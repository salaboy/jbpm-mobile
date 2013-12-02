/*
 * Copyright 2012 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.wires.client.canvas;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;

import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.widget.FormListEntry;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.MTextBox;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import javax.inject.Inject;
import org.jboss.errai.ioc.client.container.SyncBeanManager;
import org.uberfire.client.annotations.WorkbenchPartTitle;
import org.uberfire.client.annotations.WorkbenchPartView;
import org.uberfire.client.annotations.WorkbenchScreen;

@Dependent
@WorkbenchScreen(identifier = "WiresCanvasScreen")
public class CanvasScreen
        extends Composite
        implements RequiresResize {

    interface ViewBinder
            extends
            UiBinder<Widget, CanvasScreen> {

    }

    
   
    @UiField
    protected ScrollPanel scrollPanel;

    private static ViewBinder uiBinder = GWT.create(ViewBinder.class);

    @Inject
    private SyncBeanManager iocManager;

   

    @PostConstruct
    public void init() {
        initWidget(uiBinder.createAndBindUi(this));


        

        scrollPanel = new ScrollPanel();

        FlowPanel container = new FlowPanel();

        HTML header = new HTML("Contact Data");
        header.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle().getListCss().listHeader());

        container.add(header);

        WidgetList widgetList = new WidgetList();
        widgetList.setRound(true);

        // lets put in some widgets
        widgetList.add(new FormListEntry("Firstname", new MTextBox()));
        widgetList.add(new FormListEntry("Lastname", new MTextBox()));
        widgetList.add(new FormListEntry("Job Title", new MTextBox()));

        container.add(widgetList);

        scrollPanel.setScrollingEnabledX(false);
        scrollPanel.setWidget(container);
		// workaround for android formfields jumping around when using
        // -webkit-transform
        scrollPanel.setUsePos(MGWT.getOsDetection().isAndroid());
        
    }

    @WorkbenchPartTitle
    @Override
    public String getTitle() {
        return "Canvas";
    }

    @WorkbenchPartView
    public IsWidget getView() {
        return this;
    }

    @Override
    public void onResize() {
        int height = getParent().getOffsetHeight();
        int width = getParent().getOffsetWidth();
        setPixelSize(width, height);
    }

}
