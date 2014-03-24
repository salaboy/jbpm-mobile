/*
 * Copyright 2014 JBoss Inc
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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.kie.mobile.client.home.HomePlace;
import org.kie.mobile.client.home.HomePresenter;
import org.kie.mobile.ht.client.newtask.NewTaskPlace;
import org.kie.mobile.ht.client.newtask.NewTaskPresenter;
import org.kie.mobile.ht.client.taskdetails.TaskDetailsPlace;
import org.kie.mobile.ht.client.taskdetails.TaskDetailsPresenter;
import org.kie.mobile.ht.client.tasklist.TaskListPlace;
import org.kie.mobile.ht.client.tasklist.TaskListPresenter;
import org.kie.mobile.pr.client.definition.list.ProcessDefinitionListPlace;
import org.kie.mobile.pr.client.definition.list.ProcessDefinitionListPresenter;

/**
 *
 * @author livthomas
 */
@ApplicationScoped
public class PhoneActivityMapper implements ActivityMapper {
    
    @Inject
    private HomePresenter homePresenter;
    
    @Inject
    private NewTaskPresenter newTaskPresenter;
    
    @Inject
    private TaskDetailsPresenter taskDetailsPresenter;
    
    @Inject
    private TaskListPresenter taskListPresenter;
    
    @Inject
    private ProcessDefinitionListPresenter processDefinitionListPresenter;

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof HomePlace) {
            return homePresenter;
        }
        
        if (place instanceof NewTaskPlace) {
            return newTaskPresenter;
        }
        
        if (place instanceof TaskDetailsPlace) {
            long taskId = ((TaskDetailsPlace) place).getTaskId();
            taskDetailsPresenter.initTask(taskId);
            return taskDetailsPresenter;
        }
        
        if (place instanceof TaskListPlace) {
            return taskListPresenter;
        }
        
        if (place instanceof ProcessDefinitionListPlace) {
            return processDefinitionListPresenter;
        }
        
        throw new IllegalArgumentException("Unknown place");
    }

}
