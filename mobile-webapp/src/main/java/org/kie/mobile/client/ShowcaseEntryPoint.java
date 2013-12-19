/*
 * Copyright 2012 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kie.mobile.client;

import javax.inject.Inject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.animation.AnimationHelper;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jbpm.console.ng.ht.service.TaskServiceEntryPoint;

@EntryPoint
public class ShowcaseEntryPoint {

//
    @Inject
    private Caller<TaskServiceEntryPoint> taskServices;

//    @Inject
//    private Identity identity;
    @AfterInitialization
    public void startApp() {

        hideLoadingPopup();
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

            @Override
            public void onUncaughtException(Throwable e) {
                Window.alert("uncaught: " + e.getMessage());
                e.printStackTrace();

            }
        });
        new Timer() {

            @Override
            public void run() {
                start();

            }
        }.schedule(1);

    }

    private void start() {

//set viewport and other settings for mobile
        MGWT.applySettings(MGWTSettings.getAppSetting());

        //build animation helper and attach it
        final AnimationHelper animationHelper = new AnimationHelper();
        RootPanel.get().add(animationHelper);

        //build some UI
        final LayoutPanel initialScreen = new LayoutPanel();
        Button button = new Button();
        button.setText("Animate");

        button.addTapHandler(new TapHandler() {

            @Override
            public void onTap(TapEvent event) {
                //build second ui
                List<String> users = new ArrayList<String>();
                users.add("user");
                List<String> groups = new ArrayList<String>();
                long time = new Date().getTime();
                addTask(users, groups, "my task", 1, true, time, 30000);

                LayoutPanel layoutPanel = new LayoutPanel();
                Button button = new Button();
                button.setText("second");
                layoutPanel.add(button);
                button.addTapHandler(new TapHandler() {

                @Override
                public void onTap(TapEvent event) {
                    //build second ui
                    animationHelper.goTo(initialScreen, Animation.SLIDE);

                    

                }
                });
                animationHelper.goTo(layoutPanel, Animation.SLIDE);

            }
        });

        initialScreen.add(button);

        //animate
        animationHelper.goTo(initialScreen, Animation.SLIDE);
    }

    //Fade out the "Loading application" pop-up
    private void hideLoadingPopup() {
        final Element e = RootPanel.get("loading").getElement();

        new com.google.gwt.animation.client.Animation() {

            @Override
            protected void onUpdate(double progress) {
                e.getStyle().setOpacity(1.0 - progress);
            }

            @Override
            protected void onComplete() {
                e.getStyle().setVisibility(Style.Visibility.HIDDEN);
            }
        }.run(500);
    }

    public static native void redirect(String url)/*-{
     $wnd.location = url;
     }-*/;

    public void addTask(final List<String> users, List<String> groups,
            final String taskName,
            int priority,
            boolean isAssignToMe,
            long dueDate, long dueDateTime) {
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
                GWT.log("NEW TASK ID:!!! " + taskId);
            }
        }).addTask(str, null, templateVars);

    }

}
