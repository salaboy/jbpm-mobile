package org.kie.mobile.client.perspectives.tasklist;

import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.CellList;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowHeader;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import com.googlecode.mgwt.ui.client.widget.celllist.BasicCell;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import org.jbpm.console.ng.ht.model.TaskSummary;

/**
 *
 * @author livthomas
 * @author salaboy
 */
@ApplicationScoped
public class TaskListViewGwtImpl implements TaskListPresenter.TaskListView {

    private final LayoutPanel layoutPanel;

    private final Button newTaskButton;

    private PullPanel pullPanel;
    private PullArrowHeader pullArrowHeader;
    private final CellList<TaskSummary> taskList;

    public TaskListViewGwtImpl() {
        layoutPanel = new LayoutPanel();

        newTaskButton = new Button("New task");
        layoutPanel.add(newTaskButton);

        pullPanel = new PullPanel();
        pullArrowHeader = new PullArrowHeader();
        pullPanel.setHeader(pullArrowHeader);
        layoutPanel.add(pullPanel);

        taskList = new CellList<TaskSummary>(new BasicCell<TaskSummary>() {
            @Override
            public String getDisplayString(TaskSummary model) {
                return model.getId() + " : " + model.getName();
            }
        });
        pullPanel.add(taskList);
    }

    @Override
    public HasTapHandlers getNewTaskButton() {
        return newTaskButton;
    }

    @Override
    public HasRefresh getPullPanel() {
        return pullPanel;
    }

    @Override
    public void setHeaderPullHandler(PullPanel.Pullhandler pullHandler) {
        pullPanel.setHeaderPullhandler(pullHandler);
    }

    @Override
    public PullArrowWidget getPullHeader() {
        return pullArrowHeader;
    }

    @Override
    public Widget asWidget() {
        return layoutPanel;
    }

    @Override
    public void render(List<TaskSummary> tasks) {
        taskList.render(tasks);
        pullPanel.refresh();
    }

}
