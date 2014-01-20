package org.kie.mobile.client.perspectives.newtask;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author livthomas
 * @author salaboy
 */
@ApplicationScoped
public class NewTaskViewGwtImpl implements NewTaskPresenter.NewTaskView {

    private final LayoutPanel panel;

    private final MTextBox taskNameTextBox;
    private final Button addTaskButton;
    private final Button cancelButton;

    public NewTaskViewGwtImpl() {
        panel = new LayoutPanel();

        taskNameTextBox = new MTextBox();
        taskNameTextBox.setPlaceHolder("Task name");
        panel.add(taskNameTextBox);

        addTaskButton = new Button("Add");
        addTaskButton.setConfirm(true);
        panel.add(addTaskButton);

        cancelButton = new Button("Cancel");
        panel.add(cancelButton);
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

    @Override
    public Widget asWidget() {
        return panel;
    }

}
