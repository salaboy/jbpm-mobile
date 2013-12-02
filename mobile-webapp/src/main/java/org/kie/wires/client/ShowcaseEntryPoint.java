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
package org.kie.wires.client;

import javax.enterprise.event.Observes;
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
import com.googlecode.mgwt.ui.client.util.SuperDevModeUtil;
import com.googlecode.mgwt.ui.client.widget.Button;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import java.util.Date;
import org.jboss.errai.common.client.api.Caller;
import org.jboss.errai.common.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.jbpm.console.ng.ht.service.TaskServiceEntryPoint;
import org.uberfire.client.UberFirePreferences;
import org.uberfire.client.mvp.ActivityManager;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.workbench.events.ApplicationReadyEvent;

/**
 * GWT's Entry-point for Uberfire-showcase
 */
@EntryPoint
public class ShowcaseEntryPoint {

    @Inject
    private SyncBeanManager manager;

    @Inject
    private PlaceManager placeManager;

    @Inject
    private ActivityManager activityManager;

    @Inject
    Caller<TaskServiceEntryPoint> taskServices;

    @AfterInitialization
    public void startApp() {
        UberFirePreferences.setProperty("org.uberfire.client.workbench.clone.ou.mandatory.disable", true);

        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {

            @Override
            public void onUncaughtException(Throwable e) {
                Window.alert("uncaught: " + e.getMessage());
                String s = buildStackTrace(e, "RuntimeExceotion:\n");
                Window.alert(s);
                e.printStackTrace();

            }
        });

        new Timer() {

            @Override
            public void run() {
                start();

            }
        }.schedule(1);

        hideLoadingPopup();
    }

    private void start() {

        // MGWTColorScheme.setBaseColor("#56a60D");
        // MGWTColorScheme.setFontColor("#eee");
        //
        // MGWTStyle.setTheme(new MGWTColorTheme());
        //
        // MGWTStyle.setDefaultBundle((MGWTClientBundle)
        // GWT.create(MGWTStandardBundle.class));
        // MGWTStyle.getDefaultClientBundle().getMainCss().ensureInjected();
        // MGWTStyle.setTheme(new CustomTheme());
        SuperDevModeUtil.showDevMode();

        MGWTSettings.ViewPort viewPort = new MGWTSettings.ViewPort();
        viewPort.setTargetDensity(MGWTSettings.ViewPort.DENSITY.MEDIUM);
        viewPort.setUserScaleAble(false).setMinimumScale(1.0).setMinimumScale(1.0).setMaximumScale(1.0);

        MGWTSettings settings = new MGWTSettings();
        settings.setViewPort(viewPort);

        settings.setAddGlosToIcon(true);
        settings.setFullscreen(true);
        settings.setPreventScrolling(true);

        MGWT.applySettings(settings);

        // build animation helper and attach it
        final AnimationHelper animationHelper = new AnimationHelper();
        RootPanel.get().add(animationHelper);

        // build some UI
        LayoutPanel layoutPanel = new LayoutPanel();
        final LayoutPanel layoutPanel2 = new LayoutPanel();
        final MTextBox textBox = new MTextBox();
        layoutPanel2.add(textBox);
        Button button = new Button("Hello mgwt");
        layoutPanel.add(button);
        button.addTapHandler(new TapHandler() {

            public void onTap(TapEvent event) {
                taskServices.call(new RemoteCallback<Long>() {
                    @Override
                    public void callback(Long commentId) {
                        textBox.setText(String.valueOf(commentId));
                    }
                }).addComment(1, "text of the comment", "salaboy", new Date());
                animationHelper.goTo(layoutPanel2, Animation.SLIDE);
            }
        });
        // animate
        animationHelper.goTo(layoutPanel, Animation.SLIDE);
    }

    private void setupMenu(@Observes final ApplicationReadyEvent event) {

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

    private String buildStackTrace(Throwable t, String log) {
        return "disabled";
        // if (t != null) {
        // log += t.getClass().toString();
        // log += t.getMessage();
        // //
        // StackTraceElement[] stackTrace = t.getStackTrace();
        // if (stackTrace != null) {
        // StringBuffer trace = new StringBuffer();
        //
        // for (int i = 0; i < stackTrace.length; i++) {
        // trace.append(stackTrace[i].getClassName() + "." + stackTrace[i].getMethodName() + "("
        // + stackTrace[i].getFileName() + ":" + stackTrace[i].getLineNumber());
        // }
        //
        // log += trace.toString();
        // }
        // //
        // Throwable cause = t.getCause();
        // if (cause != null && cause != t) {
        //
        // log += buildStackTrace(cause, "CausedBy:\n");
        //
        // }
        // }
        // return log;
    }

}
