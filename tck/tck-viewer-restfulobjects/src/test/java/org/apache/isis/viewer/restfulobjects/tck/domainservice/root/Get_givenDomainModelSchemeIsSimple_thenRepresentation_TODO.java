/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.isis.viewer.restfulobjects.tck.domainservice.root;

import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.apache.isis.core.webserver.WebServer;
import org.apache.isis.viewer.restfulobjects.applib.*;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulClient;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse;
import org.apache.isis.viewer.restfulobjects.applib.client.RestfulResponse.HttpStatusCode;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.DomainServiceResource;
import org.apache.isis.viewer.restfulobjects.applib.domainobjects.ListRepresentation;
import org.apache.isis.viewer.restfulobjects.tck.IsisWebServerRule;

import static org.apache.isis.viewer.restfulobjects.tck.RestfulMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class Get_givenDomainModelSchemeIsSimple_thenRepresentation_TODO {

    @Rule
    public IsisWebServerRule webServerRule = new IsisWebServerRule();

    private RestfulClient client;
    private DomainServiceResource resource;

    @Before
    public void setUp() throws Exception {
        final WebServer webServer = webServerRule.getWebServer();
        client = new RestfulClient(webServer.getBase());

        resource = client.getDomainServiceResource();
    }

    @Ignore
    @Test
    public void representation() throws Exception {

    }

}
