package com.giof71.monitoring.route.hostmanagement.add;

import org.springframework.stereotype.Component;

import com.giof71.monitoring.editing.Action;
import com.giof71.monitoring.editing.LayoutCreatorDefinition;
import com.giof71.monitoring.route.HostManagement;
import com.giof71.monitoring.route.hostmanagement.HostAbsLayoutCreator;

@Component
@LayoutCreatorDefinition(requiredQualifier = HostManagement.class, action = Action.ADD)
public class HostAddLayoutCreator extends HostAbsLayoutCreator {
}
