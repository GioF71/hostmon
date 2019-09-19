package com.giof71.monitoring.route.hostmanagement.edit;

import org.springframework.stereotype.Component;

import com.giof71.monitoring.editing.LayoutCreatorDefinition;
import com.giof71.monitoring.route.Action;
import com.giof71.monitoring.route.HostManagement;
import com.giof71.monitoring.route.hostmanagement.HostAbsLayoutCreator;

@Component
@LayoutCreatorDefinition(requiredQualifier = HostManagement.class, action = Action.EDIT)
public class HostEditLayoutCreator extends HostAbsLayoutCreator {
}
