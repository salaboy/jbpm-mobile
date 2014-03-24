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
package org.kie.mobile.client.home;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.kie.mobile.client.ClientFactory;
import org.kie.mobile.ht.client.tasklist.TaskListPlace;
import org.kie.mobile.ht.client.tasklist.TaskListPresenter;
import org.kie.mobile.pr.client.definition.list.ProcessDefinitionListPlace;
import org.kie.mobile.pr.client.definition.list.ProcessDefinitionListPresenter;

/**
 *
 * @author livthomas
 */
@ApplicationScoped
public class HomePresenter extends MGWTAbstractActivity {

    public interface HomeView extends IsWidget {

        HasTapHandlers getProcessDefinitionsButton();

        HasTapHandlers getProcessInstancesButton();

        HasTapHandlers getTasksListButton();

    }

    @Inject
    private ClientFactory clientFactory;
    
    @Inject
    private TaskListPresenter taskListPresenter;

    @Inject
    private ProcessDefinitionListPresenter processDefinitionListPresenter;

    @Inject
    private HomeView view;

    public HomeView getView() {
        return view;
    }

    @AfterInitialization
    public void init() {
        view.getProcessDefinitionsButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                clientFactory.getPlaceController().goTo(new ProcessDefinitionListPlace());
            }
        });
        
        view.getTasksListButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                clientFactory.getPlaceController().goTo(new TaskListPlace());
            }
        });

        taskListPresenter.getView().getBackButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                clientFactory.getPlaceController().goTo(new HomePlace());
            }
        });
        
        processDefinitionListPresenter.getView().getBackButton().addTapHandler(new TapHandler() {
            @Override
            public void onTap(TapEvent event) {
                clientFactory.getPlaceController().goTo(new HomePlace());
            }
        });
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(view);
    }

}
