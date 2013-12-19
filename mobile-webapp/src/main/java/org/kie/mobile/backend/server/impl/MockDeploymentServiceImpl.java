/*
 * Copyright 2013 JBoss by Red Hat.
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

package org.kie.mobile.backend.server.impl;

import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import org.jbpm.kie.services.api.Kjar;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.internal.deployment.DeployedUnit;
import org.kie.internal.deployment.DeploymentService;
import org.kie.internal.deployment.DeploymentUnit;

/**
 *
 * @author salaboy
 */
@ApplicationScoped
public class MockDeploymentServiceImpl implements DeploymentService{

    @Override
    public void deploy(DeploymentUnit unit) {
        
    }

    @Override
    public void undeploy(DeploymentUnit unit) {
        
    }

    @Override
    public RuntimeManager getRuntimeManager(String deploymentUnitId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DeployedUnit getDeployedUnit(String deploymentUnitId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<DeployedUnit> getDeployedUnits() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
