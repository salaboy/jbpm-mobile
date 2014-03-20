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
package org.kie.mobile.server.impl;

import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import org.uberfire.backend.repositories.Repository;
import org.uberfire.backend.repositories.RepositoryService;
import org.uberfire.backend.server.config.ConfigGroup;
import org.uberfire.backend.server.config.ConfigType;
import org.uberfire.backend.server.config.ConfigurationFactory;
import org.uberfire.backend.server.config.ConfigurationService;
import org.uberfire.commons.services.cdi.ApplicationStarted;
import org.uberfire.commons.services.cdi.Startup;
import org.uberfire.io.IOService;

@ApplicationScoped
@Startup
public class AppSetup {

    private static final String PLAYGROUND_SCHEME = "git";
    private static final String PLAYGROUND_ALIAS = "wires-playground";
    private static final String PLAYGROUND_ORIGIN = "https://github.com/Salaboy/wires-playground.git";
    private static final String PLAYGROUND_UID = "mock";
    private static final String PLAYGROUND_PWD = "mock";

    private static final String GLOBAL_SETTINGS = "settings";

    @Inject
    @Named("ioStrategy")
    private IOService ioService;

     @Inject
    private RepositoryService repositoryService;
    

    @Inject
    private ConfigurationService configurationService;

    @Inject
    private ConfigurationFactory configurationFactory;

    @Inject
    private Event<ApplicationStarted> applicationStartedEvent;

    @PostConstruct
    public void onStartup() {
//        final Repository repository = repositoryService.getRepository( PLAYGROUND_ALIAS );
//        if ( repository == null ) {
//            repositoryService.createRepository( PLAYGROUND_SCHEME, PLAYGROUND_ALIAS,
//                                                new HashMap<String, Object>() {{
//                                                    put( "origin", PLAYGROUND_ORIGIN );
//                                                    put( "username", PLAYGROUND_UID );
//                                                    put( "crypt:password", PLAYGROUND_PWD );
//                                                }} );
//        }
//
//        configurationService.addConfiguration(getGlobalConfiguration());

        // notify cluster service that bootstrap is completed to start synchronization
        applicationStartedEvent.fire(new ApplicationStarted());
    }

    private ConfigGroup getGlobalConfiguration() {
        final ConfigGroup group = configurationFactory.newConfigGroup(ConfigType.GLOBAL,
                GLOBAL_SETTINGS,
                "");

        /*
         group.addConfigItem( configurationFactory.newConfigItem( "drools.dateformat",
         "dd-MMM-yyyy" ) );
         group.addConfigItem( configurationFactory.newConfigItem( "drools.datetimeformat",
         "dd-MMM-yyyy hh:mm:ss" ) );
         group.addConfigItem( configurationFactory.newConfigItem( "drools.defaultlanguage",
         "en" ) );
         group.addConfigItem( configurationFactory.newConfigItem( "drools.defaultcountry",
         "US" ) );
         */
        group.addConfigItem(configurationFactory.newConfigItem("build.enable-incremental",
                "true"));

        /*
         group.addConfigItem( configurationFactory.newConfigItem( "rule-modeller-onlyShowDSLStatements",
         "false" ) );
         group.addConfigItem( configurationFactory.newConfigItem( "designer.url",
         "http://localhost:8080" ) );
         group.addConfigItem( configurationFactory.newConfigItem( "designer.context",
         "designer" ) );
         group.addConfigItem( configurationFactory.newConfigItem( "designer.profile",
         "jbpm" ) );
         */
        return group;
    }
}
