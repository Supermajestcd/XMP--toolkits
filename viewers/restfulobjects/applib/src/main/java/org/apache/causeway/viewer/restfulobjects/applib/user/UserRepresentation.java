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
package org.apache.causeway.viewer.restfulobjects.applib.user;

import com.fasterxml.jackson.databind.JsonNode;

import org.apache.causeway.viewer.restfulobjects.applib.JsonRepresentation;
import org.apache.causeway.viewer.restfulobjects.applib.JsonRepresentation.HasLinkToUp;
import org.apache.causeway.viewer.restfulobjects.applib.LinkRepresentation;
import org.apache.causeway.viewer.restfulobjects.applib.Rel;
import org.apache.causeway.viewer.restfulobjects.applib.domainobjects.DomainRepresentation;

/**
 * @since 1.x {@index}
 */
public class UserRepresentation extends DomainRepresentation implements HasLinkToUp {

    public UserRepresentation(final JsonNode jsonNode) {
        super(jsonNode);
    }

    @Override
    public LinkRepresentation getUp() {
        return getLinkWithRel(Rel.UP);
    }

    public String getUserName() {
        return getString("userName");
    }

    public String getFriendlyName() {
        return getString("friendlyName");
    }

    public String getEmail() {
        return getString("email");
    }

    public JsonRepresentation getRoles() {
        return getRepresentation("roles").ensureArray();
    }

}
