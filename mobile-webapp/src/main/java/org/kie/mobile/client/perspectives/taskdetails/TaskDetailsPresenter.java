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
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.widget.MDateBox.DateParser;
import com.googlecode.mgwt.ui.client.widget.MListBox;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jbpm.console.ng.ht.model.TaskSummary;
import org.kie.mobile.client.perspectives.AbstractTaskPresenter;

/**
 *
 * @author livthomas
 */
@Dependent
public class TaskDetailsPresenter extends AbstractTaskPresenter {

    public interface TaskDetailsView extends IsWidget {

        void refreshTask(TaskSummary task);

        HasText getDescriptionTextArea();

        HasText getDueOnDateBox();

        MListBox getPriorityListBox();

        HasTapHandlers getUpdateButton();

        HasText getDelegateTextBox();

        HasTapHandlers getDelegateButton();

        HasTapHandlers getCancelButton();

        void displayNotification(String title, String text);

    }

    @Inject
    private TaskDetailsView view;

    private TaskSummary task;

    public TaskDetailsView getView(TaskSummary task) {
        this.task = task;
        view.refreshTask(task);
        return view;
    }

    private void updateTask(String description, Date dueDate, int priority) {
        List<String> descriptions = new ArrayList<String>();
        descriptions.add(description);

        List<String> names = new ArrayList<String>();
        names.add(task.getName());

        taskServices.call(new RemoteCallback<Void>() {
            @Override
            public void callback(Void response) {
                view.displayNotification("Success", "Task details has been updated for the task with id = " + task.getId());
            }
        }).updateSimpleTaskDetails(task.getId(), names, priority, descriptions, dueDate);
    }

    private void delegateTask(String entity) {
        taskServices.call(new RemoteCallback<Void>() {
            @Override
            public void callback(Void nothing) {
                view.displayNotification("Success", "Task was succesfully delegated");
                // refresh potential owners and disable delegate button
            }
        }).delegate(task.getId(), identity.getName(), entity);
    }

    @AfterInitialization
    public void setUpHandlers() {
        view.getCancelButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                AnimationHelper animationHelper = new AnimationHelper();
                RootPanel.get().clear();
                RootPanel.get().add(animationHelper);
                animationHelper.goTo(clientFactory.getTaskListPresenter().getView(), Animation.SLIDE_REVERSE);
            }
        });

        view.getUpdateButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                try {
                    updateTask(view.getDescriptionTextArea().getText(), new DateParser().parse(view.getDueOnDateBox()
                            .getText()), view.getPriorityListBox().getSelectedIndex());
                } catch (ParseException ex) {
                    view.displayNotification("Wrong date format", "Enter the date in the correct format!");
                }
            }
        });
        
        view.getDelegateButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                delegateTask(view.getDelegateTextBox().getText());
            }
        });
    }

}
