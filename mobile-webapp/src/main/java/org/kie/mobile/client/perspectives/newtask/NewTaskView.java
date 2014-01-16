package org.kie.mobile.client.perspectives.newtask;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;

/**
 *
 * @author livthomas
 */
public interface NewTaskView extends IsWidget {
	
	HasText getTaskNameTextBox();
	
	HasTapHandlers getAddTaskButton();
	
	HasTapHandlers getCancelButton();

}
