package org.kie.mobile.client.perspectives.tasklist;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jbpm.console.ng.ht.model.TaskSummary;
import org.jbpm.console.ng.ht.service.TaskServiceEntryPoint;
import org.kie.mobile.client.perspectives.newtask.NewTaskPresenter;
import org.uberfire.security.Identity;

/**
 *
 * @author livthomas
 * @author salaboy
 */
@Dependent
public class TaskListPresenter {

    public interface TaskListView extends IsWidget {

        HasTapHandlers getNewTaskButton();

        HasRefresh getPullPanel();

        void setHeaderPullHandler(PullPanel.Pullhandler pullHandler);

        PullArrowWidget getPullHeader();

        void render(List<TaskSummary> tasks);

    }

    @Inject
    private Caller<TaskServiceEntryPoint> taskServices;

    @Inject
    private TaskListView view;

    @Inject
    private NewTaskPresenter newTaskPresenter;

    @Inject
    private Identity identity;

    private boolean failedHeader = false;

    @AfterInitialization
    public void init() {
        view.getNewTaskButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                AnimationHelper animationHelper = new AnimationHelper();
                RootPanel.get().clear();
                RootPanel.get().add(animationHelper);
                animationHelper.goTo(newTaskPresenter.getView(), Animation.SLIDE);
            }
        });

        view.getPullHeader().setHTML("pull down");

        PullArrowStandardHandler headerHandler = new PullArrowStandardHandler(view.getPullHeader(), view.getPullPanel());

        headerHandler.setErrorText("Error");
        headerHandler.setLoadingText("Loading");
        headerHandler.setNormalText("pull down");
        headerHandler.setPulledText("release to load");
        headerHandler.setPullActionHandler(new PullArrowStandardHandler.PullActionHandler() {
            @Override
            public void onPullAction(final AsyncCallback<Void> callback) {
                new Timer() {
                    @Override
                    public void run() {
                        refresh();
                    }
                }.schedule(1000);

            }
        });
        view.setHeaderPullHandler(headerHandler);

        refresh();
    }

    public void refresh() {
        List<String> status = new ArrayList<String>();
        status.add("Created");
        status.add("Ready");
        status.add("Reserved");
        status.add("InProgress");
        status.add("Suspended");
        status.add("Failed");
        status.add("Error");
        status.add("Exited");
        status.add("Obsolete");
        status.add("Completed");
        taskServices.call(new RemoteCallback<List<TaskSummary>>() {
            @Override
            public void callback(List<TaskSummary> taskList) {
                view.render(taskList);
            }
        }).getTasksAssignedAsPotentialOwnerByExpirationDateOptional(identity.getName(), status, null, "en-UK");
    }

    public TaskListView getView() {
        return view;
    }

}
