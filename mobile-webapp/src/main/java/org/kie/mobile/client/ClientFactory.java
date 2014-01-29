package org.kie.mobile.client;

import javax.inject.Inject;
import javax.inject.Singleton;
import org.kie.mobile.client.perspectives.newtask.NewTaskPresenter;
import org.kie.mobile.client.perspectives.tasklist.TaskListPresenter;

/**
 *
 * @author livthomas
 */
@Singleton
public class ClientFactory {

    @Inject
    private NewTaskPresenter newTaskPresenter;

    @Inject
    private TaskListPresenter taskListPresenter;

    public NewTaskPresenter getNewTaskPresenter() {
        return newTaskPresenter;
    }

    public TaskListPresenter getTaskListPresenter() {
        return taskListPresenter;
    }
    
}
