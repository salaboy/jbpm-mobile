/*
 * Copyright 2014 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.mobile.client.perspectives.taskdetails;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.MDateBox;
import com.googlecode.mgwt.ui.client.widget.MDateBox.DateRenderer;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import com.googlecode.mgwt.ui.client.widget.MTextArea;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.RoundPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabBarButton;
import com.googlecode.mgwt.ui.client.widget.tabbar.TabPanel;
import javax.enterprise.context.ApplicationScoped;
import org.jbpm.console.ng.ht.model.TaskSummary;
import org.kie.mobile.client.perspectives.AbstractTaskView;

/**
 *
 * @author livthomas
 */
@ApplicationScoped
public class TaskDetailsViewGwtImpl extends AbstractTaskView implements TaskDetailsPresenter.TaskDetailsView {

    private final Button cancelButton;
    
    private final MTextArea descriptionTextArea = new MTextArea();
    
    private final MTextBox statusTextBox = new MTextBox();
    
    private final MDateBox dueOnDateBox = new MDateBox();
    
    private final MListBox priorityListBox = new MListBox();
    
    private final MTextBox userTextBox = new MTextBox();
    
    private final MTextBox processInstanceIdTextBox = new MTextBox();
    
    private final MTextBox processDefinitionIdTextBox = new MTextBox();
    
    private final Button processInstanceDetailsButton = new Button("Process Instance Details");
    
    private final Button updateButton = new Button("Update");
    
    private final Label potentialOwnersLabel = new Label();
    
    private final MTextBox delegateTextBox = new MTextBox();
    
    private final Button delegateButton = new Button("Delegate");

    public TaskDetailsViewGwtImpl() {
        TabPanel tabPanel = new TabPanel();
        tabPanel.setDisplayTabBarOnTop(true);
        layoutPanel.add(tabPanel);

        cancelButton = new Button("Cancel");
        layoutPanel.add(cancelButton);
        
        // Work tab
        
        RoundPanel workPanel = new RoundPanel();
        
        TabBarButton workTabButton = new TabBarButton(null);
        workTabButton.setText("Work");
        tabPanel.add(workTabButton, workPanel);

        // Details tab
        
        RoundPanel detailsPanel = new RoundPanel();
        
        for (String priority : priorities) {
            priorityListBox.addItem(priority);
        }
        statusTextBox.setReadOnly(true);
        userTextBox.setReadOnly(true);
        
        WidgetList detailsForm = new WidgetList();
		detailsForm.setRound(true);
		detailsForm.add(new FormListEntry("Description", descriptionTextArea));
		detailsForm.add(new FormListEntry("Status", statusTextBox));
		detailsForm.add(new FormListEntry("Due On", dueOnDateBox));
		detailsForm.add(new FormListEntry("Priority", priorityListBox));
		detailsForm.add(new FormListEntry("User", userTextBox));
        detailsPanel.add(detailsForm);
        
        processInstanceIdTextBox.setReadOnly(true);
        processDefinitionIdTextBox.setReadOnly(true);
        
        WidgetList processContextForm = new WidgetList();
		processContextForm.setRound(true);
		processContextForm.add(new FormListEntry("Process Instance Id", processInstanceIdTextBox));
		processContextForm.add(new FormListEntry("Process Definition Id", processDefinitionIdTextBox));
		processContextForm.add(new FormListEntry("Process Instance Details", processInstanceDetailsButton));
        detailsPanel.add(processContextForm);
        
        detailsPanel.add(updateButton);
        
        TabBarButton detailsTabButton = new TabBarButton(null);
        detailsTabButton.setText("Details");
        tabPanel.add(detailsTabButton, detailsPanel);
        
        // Assignments tab
        
        RoundPanel assignmentsPanel = new RoundPanel();
        
        WidgetList assignmentsForm = new WidgetList();
        assignmentsForm.setRound(true);
        assignmentsForm.add(new FormListEntry("Potential Owners", potentialOwnersLabel));
        assignmentsForm.add(new FormListEntry("User or Group", delegateTextBox));
        assignmentsPanel.add(assignmentsForm);
        
        assignmentsPanel.add(delegateButton);
        
        TabBarButton assignmentsTabButton = new TabBarButton(null);
        assignmentsTabButton.setText("Assignments");
        tabPanel.add(assignmentsTabButton, assignmentsPanel);
        
        // Comments tab
        
        RoundPanel commentsPanel = new RoundPanel();
        
        TabBarButton commentsTabButton = new TabBarButton(null);
        commentsTabButton.setText("Comments");
        tabPanel.add(commentsTabButton, commentsPanel);
        
        tabPanel.setSelectedChild(1);
    }

    @Override
    public HasTapHandlers getCancelButton() {
        return cancelButton;
    }

    @Override
    public void refreshTask(TaskSummary task) {
        descriptionTextArea.setText(task.getDescription());
        statusTextBox.setText(task.getStatus());
        dueOnDateBox.setText(new DateRenderer().render(task.getExpirationTime()));
        priorityListBox.setSelectedIndex(task.getPriority());
        userTextBox.setText(task.getActualOwner());
        
        String instanceId = (task.getProcessInstanceId() == -1) ? "None" : Long.toString(task.getProcessInstanceId());
        String definitionId = (task.getProcessId() == null) ? "None" : task.getProcessId();
        processInstanceIdTextBox.setText(instanceId);
        processDefinitionIdTextBox.setText(definitionId);
        
        potentialOwnersLabel.setText(task.getPotentialOwners().toString());
    }

    @Override
    public HasTapHandlers getUpdateButton() {
        return updateButton;
    }

    @Override
    public HasTapHandlers getDelegateButton() {
        return delegateButton;
    }

    @Override
    public HasText getDescriptionTextArea() {
        return descriptionTextArea;
    }

    @Override
    public HasText getDueOnDateBox() {
        return dueOnDateBox;
    }

    @Override
    public MListBox getPriorityListBox() {
        return priorityListBox;
    }

    @Override
    public void displayNotification(String title, String text) {
        Dialogs.alert(title, text, null);
    }

    @Override
    public HasText getDelegateTextBox() {
        return delegateTextBox;
    }

}