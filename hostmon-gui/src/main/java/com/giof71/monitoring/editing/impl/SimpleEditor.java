package com.giof71.monitoring.editing.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.giof71.monitoring.editing.EditingLayout;
import com.giof71.monitoring.editing.EditingLayoutCreator;
import com.giof71.monitoring.editing.EditingLayoutCreatorLibrary;
import com.giof71.monitoring.editing.Editor;
import com.giof71.monitoring.editing.EditorAction;
import com.giof71.monitoring.editing.EditorDefinition;
import com.giof71.monitoring.editing.ExecuteSimpleValidation;
import com.giof71.monitoring.editing.ExecuteSimpleValidationList;
import com.giof71.monitoring.editing.SimpleValidator;
import com.giof71.monitoring.editing.SimpleValidatorLibrary;
import com.giof71.monitoring.editing.ValidationException;
import com.giof71.monitoring.route.start.StartPage;
import com.giof71.monitoring.util.AnnotationScanner;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public abstract class SimpleEditor extends VerticalLayout implements Editor {

	private static final long serialVersionUID = -2919331865460931980L;
	
	@Autowired
	private AnnotationScanner annotationScanner;
	
	@Autowired 
	private EditingLayoutCreatorLibrary editingLayoutCreatorLibrary;
	
	@Autowired
	private SimpleValidatorLibrary simpleValidatorLibrary;
	
	private TextArea errorMessage;
	
	private EditingLayout editingLayout;
	
	private Optional<EditorDefinition> getEditorDefinition() {
		return annotationScanner.scanForTypeAnnotation(getClass(), SimpleEditor.class, EditorDefinition.class);
	}
	
	private Optional<EditorAction> getEditorAction() {
		return annotationScanner.scanForTypeAnnotation(getClass(), SimpleEditor.class, EditorAction.class);
	}
	
	private List<ExecuteSimpleValidation> getSimpleValidationList() {
		List<ExecuteSimpleValidation> list = new ArrayList<>();
		Optional<ExecuteSimpleValidation> esv = annotationScanner.scanForTypeAnnotation(getClass(), SimpleEditor.class, ExecuteSimpleValidation.class);
		if (esv.isPresent()) {
			list.add(esv.get());
		}
		Optional<ExecuteSimpleValidationList> esvl = annotationScanner.scanForTypeAnnotation(getClass(), SimpleEditor.class, ExecuteSimpleValidationList.class);
		if (esvl.isPresent()) {
			ExecuteSimpleValidationList vList = esvl.get();
			for (ExecuteSimpleValidation current : vList.validationList()) {
				list.add(current);
			}
		}
		return list;
	}
	
	private ComponentEventListener<ClickEvent<Button>> wrapExecuteActionClickListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {

			private static final long serialVersionUID = -8504613097374652555L;

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				resetErrors();
				boolean ok = true;
				List<ExecuteSimpleValidation> validationList = getSimpleValidationList();
				for (ExecuteSimpleValidation current : validationList) {
					Component currentComponent = editingLayout.getComponent(current.componentName(), Component.class);
					SimpleValidator currentValidator = simpleValidatorLibrary.get(current.validator());
					if (currentValidator != null) {
						try {
							currentValidator.validate(currentComponent);
						} catch (ValidationException vExc) {
							addErrorMessage(String.format("Could not validate %s due to %s [%s]", 
								current.componentName(), 
								Exception.class.getSimpleName(),
								vExc.getClass().getSimpleName()));
							ok = false;
						}
					} 
				}
				if (ok) {
					getExecuteActionClickListener().onComponentEvent(event);
				}
			}
		};
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
	
	protected final void doCreateLayout() {
		Optional<EditorDefinition> optEditorDefinition = getEditorDefinition();
		Optional<EditorAction> optEditorAction = getEditorAction();
		if (optEditorAction.isPresent() && optEditorAction.isPresent()) {
			EditingLayoutCreator layoutCreator = editingLayoutCreatorLibrary.get(optEditorDefinition.get().requiredQualifier(), optEditorAction.get().action());
			EditingLayout editingLayout = layoutCreator.create();
			this.editingLayout = editingLayout;
			add(editingLayout.getLayoutComponent());
		} else {
			throw new RuntimeException(String.format("Cannot select a %s for %s [%s]", 
				EditingLayoutCreator.class.getSimpleName(),
				Editor.class.getSimpleName(),
				getClass().getSimpleName()));
		}
	}
	
	protected final EditingLayout getEditingLayout() {
		return editingLayout;
	}
	
	@PostConstruct
	private void postConstruct() {
		doCreateLayout();
		
		HorizontalLayout messageLayout = new HorizontalLayout();
		errorMessage = new TextArea();
		errorMessage.setWidthFull();
		errorMessage.setReadOnly(true);
		messageLayout.add(errorMessage);
		messageLayout.setWidthFull();
		add(messageLayout);
		
		HorizontalLayout bottomLayout = createBottomLayout();
		add(bottomLayout);
	}
	
	private String getUpdateButtonText() {
		String text = "Execute";
		Optional<EditorAction> optEditorAction = getEditorAction();
		if (optEditorAction.isPresent()) {
			String buttonText = optEditorAction.get().buttonText();
			if (buttonText != null && buttonText.length() > 0) {
				text = buttonText;
			} else {
				Optional<EditorDefinition> optEditorDefinition = getEditorDefinition();
				if (optEditorDefinition.isPresent()) {
					EditorDefinition editorDefinition = optEditorDefinition.get();
					String entityDisplayName = editorDefinition.entityDisplayName();
					String actionName = optEditorAction.get().action().getDisplayText();
					text = String.format("%s %s", actionName, entityDisplayName);
				}
			}
		}
		return text;
	}

	private HorizontalLayout createBottomLayout() {
		HorizontalLayout bottomButtons = new HorizontalLayout();
		Button updateButton = new Button(getUpdateButtonText(), wrapExecuteActionClickListener());
		bottomButtons.add(updateButton);
		
		Button cancelButton = new Button("Return to Host List", getCancelActionClickListener());
		bottomButtons.add(cancelButton);
		return bottomButtons;
	}
	
	protected void addErrorMessage(String message) {
		errorMessage.setReadOnly(false);
		String currentText = errorMessage.getValue();
		String newText = message;
		if (currentText != null && currentText.length() > 0) {
			newText = newText.concat("\n").concat(currentText);
		}
		errorMessage.setValue(newText);
		errorMessage.setReadOnly(true);
	}
	
	protected void resetErrors() {
		errorMessage.setReadOnly(false);
		errorMessage.setValue("");
		errorMessage.setReadOnly(true);
	}
}

