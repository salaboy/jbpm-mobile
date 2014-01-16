package org.kie.mobile.client.perspectives.tasklist;

import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import java.util.List;
import org.jbpm.console.ng.ht.model.TaskSummary;

/**
 *
 * @author livthomas
 */
public interface TaskListView extends IsWidget {
	
	HasTapHandlers getNewTaskButton();
    
    HasRefresh getPullPanel();
    
    void setHeaderPullHandler(PullPanel.Pullhandler pullHandler);
    
    PullArrowWidget getPullHeader();
    
    void render(List<TaskSummary> tasks);

}
