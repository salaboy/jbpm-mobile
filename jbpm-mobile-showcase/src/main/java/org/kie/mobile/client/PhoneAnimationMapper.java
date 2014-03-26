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
package org.kie.mobile.client;

import com.google.gwt.place.shared.Place;
import com.googlecode.mgwt.mvp.client.Animation;
import com.googlecode.mgwt.mvp.client.AnimationMapper;
import javax.enterprise.context.ApplicationScoped;
import org.kie.mobile.client.home.HomePlace;
import org.kie.mobile.ht.client.newtask.NewTaskPlace;
import org.kie.mobile.ht.client.taskdetails.TaskDetailsPlace;
import org.kie.mobile.ht.client.tasklist.TaskListPlace;
import org.kie.mobile.pr.client.definition.list.ProcessDefinitionListPlace;

/**
 *
 * @author livthomas
 */
@ApplicationScoped
public class PhoneAnimationMapper implements AnimationMapper {

    @Override
    public Animation getAnimation(Place oldPlace, Place newPlace) {
        // home page - tasks list
        if (oldPlace instanceof HomePlace && newPlace instanceof TaskListPlace) {
            return Animation.SLIDE;
        }
        if (oldPlace instanceof TaskListPlace && newPlace instanceof HomePlace) {
            return Animation.SLIDE_REVERSE;
        }

        // home page - process definitions list
        if (oldPlace instanceof HomePlace && newPlace instanceof ProcessDefinitionListPlace) {
            return Animation.SLIDE;
        }
        if (oldPlace instanceof ProcessDefinitionListPlace && newPlace instanceof HomePlace) {
            return Animation.SLIDE_REVERSE;
        }

        // tasks list - new task page
        if (oldPlace instanceof TaskListPlace && newPlace instanceof NewTaskPlace) {
            return Animation.SLIDE;
        }
        if (oldPlace instanceof NewTaskPlace && newPlace instanceof TaskListPlace) {
            return Animation.SLIDE_REVERSE;
        }

        // tasks list - task details page
        if (oldPlace instanceof TaskListPlace && newPlace instanceof TaskDetailsPlace) {
            return Animation.SLIDE;
        }
        if (oldPlace instanceof TaskDetailsPlace && newPlace instanceof TaskListPlace) {
            return Animation.SLIDE_REVERSE;
        }

        // other
        return Animation.SLIDE;
    }

}
