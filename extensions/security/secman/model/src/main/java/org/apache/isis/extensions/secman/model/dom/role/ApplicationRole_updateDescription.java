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
package org.apache.isis.extensions.secman.model.dom.role;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberSupport;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.PromptStyle;
import org.apache.isis.applib.types.DescriptionType;
import org.apache.isis.extensions.secman.api.IsisModuleExtSecmanApi;
import org.apache.isis.extensions.secman.api.role.ApplicationRole;
import org.apache.isis.extensions.secman.model.dom.role.ApplicationRole_updateDescription.ActionDomainEvent;

import lombok.RequiredArgsConstructor;

@Action(
        domainEvent = ApplicationRole_updateDescription.ActionDomainEvent.class,
        associateWith = "description")
@ActionLayout(sequence = "1", promptStyle = PromptStyle.INLINE_AS_IF_EDIT)
@RequiredArgsConstructor
public class ApplicationRole_updateDescription {

    public static class ActionDomainEvent extends IsisModuleExtSecmanApi.ActionDomainEvent<ApplicationRole_updateDescription> {}

    private final ApplicationRole target;

    @MemberSupport
    public ApplicationRole act(
            @Parameter(
                    maxLength = DescriptionType.Meta.MAX_LEN,
                    optionality = Optionality.OPTIONAL
                    )
            @ParameterLayout(
                    named="Description",
                    typicalLength=ApplicationRole.TYPICAL_LENGTH_DESCRIPTION)
            final String description) {

        target.setDescription(description);
        return target;
    }

    @MemberSupport
    public String default0Act() {
        return target.getDescription();
    }
}
