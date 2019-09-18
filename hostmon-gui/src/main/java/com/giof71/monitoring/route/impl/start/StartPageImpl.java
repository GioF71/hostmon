package com.giof71.monitoring.route.impl.start;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.giof71.monitoring.conversion.ConverterLibrary;
import com.giof71.monitoring.conversion.TypedConverter;
import com.giof71.monitoring.model.MonitoredHost;
import com.giof71.monitoring.route.StartPage;
import com.giof71.monitoring.route.bean.MonitoredHostView;
import com.giof71.monitoring.route.impl.EditMonitoredHostImpl;
import com.giof71.monitoring.service.HostService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
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
public class StartPageImpl extends VerticalLayout implements StartPage {

	private static final long serialVersionUID = 9092663229633895398L;
	
//	@Autowired
//	private EditMonitoredHost editMonitoredHost;
	
//	private RouterLink editMonitoredHostLink;
	
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
				RouterLink link = new RouterLink("Edit", EditMonitoredHostImpl.class, source.getId());
				return link;
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
	
	@Override
	public void refresh() {
		List<MonitoredHostView> hostList = convert(hostService.findAll());
		grid.setItems(hostList);
		String hostCountText = Long.valueOf(hostList.size()).toString();
		lblHostCountValue.setText(hostCountText);
	}
}

