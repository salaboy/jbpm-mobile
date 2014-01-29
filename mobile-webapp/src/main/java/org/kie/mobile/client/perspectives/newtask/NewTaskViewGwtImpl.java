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

package org.kie.mobile.client.perspectives.newtask;

import com.google.gwt.user.client.ui.HasText;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import javax.enterprise.context.ApplicationScoped;
import org.kie.mobile.client.perspectives.AbstractTaskView;

/**
 *
 * @author livthomas
 * @author salaboy
 */
@ApplicationScoped
public class NewTaskViewGwtImpl extends AbstractTaskView implements NewTaskPresenter.NewTaskView {

    private final MTextBox taskNameTextBox;
    private final Button addTaskButton;
    private final Button cancelButton;

    public NewTaskViewGwtImpl() {
        taskNameTextBox = new MTextBox();
        taskNameTextBox.setPlaceHolder("Task name");
        layoutPanel.add(taskNameTextBox);

        addTaskButton = new Button("Add");
        addTaskButton.setConfirm(true);
        layoutPanel.add(addTaskButton);

        cancelButton = new Button("Cancel");
        layoutPanel.add(cancelButton);
    }

    @Override
    public HasText getTaskNameTextBox() {
        return taskNameTextBox;
    }

    @Override
    public HasTapHandlers getAddTaskButton() {
        return addTaskButton;
    }

    @Override
    public HasTapHandlers getCancelButton() {
        return cancelButton;
    }

}
