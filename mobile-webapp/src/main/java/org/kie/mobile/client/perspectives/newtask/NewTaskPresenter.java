package org.kie.mobile.client.perspectives.newtask;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jbpm.console.ng.ht.service.TaskServiceEntryPoint;
import org.kie.mobile.client.perspectives.tasklist.TaskListPresenter;
import org.uberfire.security.Identity;

/**
 *
 * @author livthomas
 * @author salaboy
 */
@Dependent
public class NewTaskPresenter {

    public interface NewTaskView extends IsWidget {
	
	HasText getTaskNameTextBox();
	
	HasTapHandlers getAddTaskButton();
	
	HasTapHandlers getCancelButton();

    }
    
    @Inject
    private Caller<TaskServiceEntryPoint> taskServices;

    @Inject
    private NewTaskView view;

    @Inject
    private TaskListPresenter taskListPresenter;
    
    @Inject
    private Identity identity;

    @AfterInitialization
    public void init() {
        view.getAddTaskButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                List<String> users = new ArrayList<String>();
                users.add(identity.getName());
                GWT.log(identity.getName());
                List<String> groups = new ArrayList<String>();
                long time = new Date().getTime();
                
                addTask(users, groups, view.getTaskNameTextBox().getText(), 1, true, time, 30000);

                view.getTaskNameTextBox().setText("");
                AnimationHelper animationHelper = new AnimationHelper();
                RootPanel.get().clear();
                RootPanel.get().add(animationHelper);
                animationHelper.goTo(taskListPresenter.getView(), Animation.SLIDE_REVERSE);
            }
        });

        view.getCancelButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                view.getTaskNameTextBox().setText("");
                AnimationHelper animationHelper = new AnimationHelper();
                RootPanel.get().clear();
                RootPanel.get().add(animationHelper);
                animationHelper.goTo(taskListPresenter.getView(), Animation.SLIDE_REVERSE);
            }
        });
    }

    private void addTask(final List<String> users, List<String> groups, final String taskName, int priority,
            boolean isAssignToMe, long dueDate, long dueDateTime) {
        Map<String, Object> templateVars = new HashMap<String, Object>();
        Date due = new Date(dueDate + dueDateTime);
        templateVars.put("due", due);
        templateVars.put("now", new Date());

        String str = "(with (new Task()) { priority = " + priority
                + ", taskData = (with( new TaskData()) { createdOn = now, expirationTime = due } ), ";
        str += "peopleAssignments = (with ( new PeopleAssignments() ) { potentialOwners = ";
        str += " [";
        if (users != null && !users.isEmpty()) {

            for (String user : users) {
                str += "new User('" + user + "'), ";
            }

        }
        if (groups != null && !groups.isEmpty()) {

            for (String group : groups) {
                str += "new Group('" + group + "'), ";
            }

        }
        str += "], businessAdministrators = [ new Group('Administrators') ],}),";
        str += "names = [ new I18NText( 'en-UK', '" + taskName + "')]})";

        taskServices.call(new RemoteCallback<Long>() {
            @Override
            public void callback(Long taskId) {
                taskListPresenter.refresh();
            }
        }).addTask(str, null, templateVars);
    }

    public NewTaskView getView() {
        return view;
    }

}
