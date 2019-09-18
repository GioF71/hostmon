package com.giof71.monitoring.route.start;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.bean.MonitoredHostView;
import com.giof71.monitoring.conversion.ConverterLibrary;
import com.giof71.monitoring.conversion.TypedConverter;
import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.edit.EditMonitoredHost;
import com.giof71.monitoring.service.HostService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "start")
@Component
@UIScope
public class StartPage extends VerticalLayout {

	private static final long serialVersionUID = 9092663229633895398L;
	
	@Autowired
	private HostService hostService;
		
	@Autowired 
	private ConverterLibrary converterLibrary;
	
	private TypedConverter<MonitoredHost, MonitoredHostView> monitoredHostConverter;
	
	private Label lblHostCountValue;
	
	private Button btnDelete;
	private Grid<MonitoredHostView> grid = new Grid<>(MonitoredHostView.class);
	
	@PostConstruct
	private void postConstruct() {
		monitoredHostConverter = converterLibrary.getConverter(MonitoredHost.class, MonitoredHostView.class);
				
		HorizontalLayout hostContainer = new HorizontalLayout();
		hostContainer.setDefaultVerticalComponentAlignment(Alignment.CENTER);
		
		Label lblHostCountDescription = new Label("Host Count");
		hostContainer.add(lblHostCountDescription);
		
		lblHostCountValue = new Label();
		hostContainer.add(lblHostCountValue);
		
		hostContainer.add(createRandomHostButton());
		
		btnDelete = createDeleteSelectedHostButton();
		hostContainer.add(btnDelete);
		
		add(hostContainer);	
		
		grid.setColumns("id", "friendlyName", "address", "creationTimestamp", "updateTimestamp");
		
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.getSelectionModel().addSelectionListener(new SelectionListener<Grid<MonitoredHostView>, MonitoredHostView>() {
			
			private static final long serialVersionUID = 1268342967335989414L;

			@Override
			public void selectionChange(SelectionEvent<Grid<MonitoredHostView>, MonitoredHostView> event) {
				btnDelete.setEnabled(event.getAllSelectedItems().size() > 0);
			}
		});
		
		grid.addComponentColumn(createdEditLinkProvider());
		grid.addComponentColumn(createdButtonProvider());
		
		grid.setSizeFull();
		
		add(grid);
		
		setSizeFull();
		refresh();
	}
	
	private ValueProvider<MonitoredHostView, RouterLink> createdEditLinkProvider() {
		return new ValueProvider<MonitoredHostView, RouterLink>() {
			
			private static final long serialVersionUID = -5669582444002840211L;

			@Override
			public RouterLink apply(MonitoredHostView source) {
				RouterLink link = new RouterLink("Edit", EditMonitoredHost.class, source.getId());
				return link;
			}
		};
	}
	
	private ValueProvider<MonitoredHostView, Button> createdButtonProvider() {
		return new ValueProvider<MonitoredHostView, Button>() {
			
			private static final long serialVersionUID = -5669582444002840211L;

			@Override
			public Button apply(MonitoredHostView source) {
				Button button = new Button("Edit");
				button.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {
					
					private static final long serialVersionUID = -7203535747497790891L;

					@Override
					public void onComponentEvent(ClickEvent<Button> event) {
						// TODO Auto-generated method stub
						UI.getCurrent().navigate(EditMonitoredHost.class, source.getId());
					}
				});
				return button;
			}
		};
	}

	private Button createRandomHostButton() {
		Button btn = new Button("Add Random host");
		btn.addClickListener(createAddRandomHostClickListener());
		return btn;
	}
	
	private Button createDeleteSelectedHostButton() {
		Button btn = new Button("Delete");
		btn.setEnabled(false);
		btn.addClickListener(createDeleteSelectedHostClickListener());
		return btn;
	}
	
	private ComponentEventListener<ClickEvent<Button>> createDeleteSelectedHostClickListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {
			
			private static final long serialVersionUID = -1703701379087029570L;
			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				Set<MonitoredHostView> selected = grid.getSelectionModel().getSelectedItems();
				for (MonitoredHostView currentSelection : Optional.ofNullable(selected).orElse(Collections.emptySet())) {
					hostService.remove(currentSelection.getId());
				}
				refresh();
			}
		};
	}
	
	private ComponentEventListener<ClickEvent<Button>> createAddRandomHostClickListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {
			
			private static final long serialVersionUID = -1703701379087029570L;
			
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				String friendlyName = UUID.randomUUID().toString();
				String address = UUID.randomUUID().toString();
				hostService.addHost(friendlyName, address);
				refresh();
			}
		};
	}
	
	private List<MonitoredHostView> convert(List<MonitoredHost> sourceList) {
		List<MonitoredHostView> list = new ArrayList<>();
		for (MonitoredHost current : Optional.ofNullable(sourceList).orElse(Collections.emptyList())) {
			list.add(convert(current));
		}
		return list;
	}
	
	private MonitoredHostView convert(MonitoredHost source) {
		return monitoredHostConverter.convert(source); 
	}
	
	public void refresh() {
		List<MonitoredHostView> hostList = convert(hostService.findAll());
		grid.setItems(hostList);
		String hostCountText = Long.valueOf(hostList.size()).toString();
		lblHostCountValue.setText(hostCountText);
	}
}

