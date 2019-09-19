package com.giof71.monitoring.editing.impl;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.giof71.monitoring.editing.EditingLayout;
import com.giof71.monitoring.editing.EditingLayoutCreator;
import com.giof71.monitoring.editing.EditingLayoutCreatorLibrary;
import com.giof71.monitoring.editing.Editor;
import com.giof71.monitoring.editing.EditorAction;
import com.giof71.monitoring.editing.EditorDefinition;
import com.giof71.monitoring.route.start.StartPage;
import com.giof71.monitoring.util.AnnotationScanner;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class SimpleEditor extends VerticalLayout implements Editor {

	private static final long serialVersionUID = -2919331865460931980L;
	
	@Autowired
	private AnnotationScanner annotationScanner;
	
	@Autowired 
	private EditingLayoutCreatorLibrary editingLayoutCreatorLibrary;
	
	private Label errorMessage;
	
	private EditingLayout editingLayout;
	
	private Optional<EditorDefinition> getEditorDefinition() {
		return annotationScanner.scanForTypeAnnotation(getClass(), SimpleEditor.class, EditorDefinition.class);
	}
	
	private Optional<EditorAction> getEditorAction() {
		return annotationScanner.scanForTypeAnnotation(getClass(), SimpleEditor.class, EditorAction.class);
	}
	
	protected abstract ComponentEventListener<ClickEvent<Button>> getExecuteActionClickListener();
	
	protected ComponentEventListener<ClickEvent<Button>> getCancelActionClickListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {
			
			private static final long serialVersionUID = -8504613097374652555L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				UI.getCurrent().navigate(StartPage.class);
			}
		};
	}
	
	protected void doCreateLayout() {
		Optional<EditorDefinition> optEditorDefinition = getEditorDefinition();
		Optional<EditorAction> optEditorAction = getEditorAction();
		if (optEditorAction.isPresent() && optEditorAction.isPresent()) {
			EditingLayoutCreator layoutCreator = editingLayoutCreatorLibrary.get(optEditorDefinition.get().requiredQualifier(), optEditorAction.get().action());
			System.out.println(String.format("Selected %s = %s", EditingLayoutCreator.class.getSimpleName(), layoutCreator.getClass().getSimpleName()));
			EditingLayout editingLayout = layoutCreator.create();
			this.editingLayout = editingLayout;
			add(editingLayout.getLayoutComponent());
		}
	}
	
	protected final EditingLayout getEditingLayout() {
		return editingLayout;
	}
	
	@PostConstruct
	private void postConstruct() {
		doCreateLayout();
		
		HorizontalLayout messageLayout = new HorizontalLayout();
		errorMessage = new Label();
		messageLayout.add(errorMessage);
		messageLayout.setWidthFull();
		add(messageLayout);
		
		HorizontalLayout bottomLayout = createBottomLayout();
		add(bottomLayout);
	}

	private HorizontalLayout createBottomLayout() {
		HorizontalLayout bottomButtons = new HorizontalLayout();
		Button updateButton = new Button("Execute", getExecuteActionClickListener());
		bottomButtons.add(updateButton);
		
		Button cancelButton = new Button("Return to Host List", getCancelActionClickListener());
		bottomButtons.add(cancelButton);
		return bottomButtons;
	}
	
	protected void setErrorMessage(String message) {
		errorMessage.setText(message);
	}
	
	protected void resetErrorMessage() {
		errorMessage.setText("");
	}
}

