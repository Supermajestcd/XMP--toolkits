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
package demoapp.dom.domain.collections.Collection.domainEvent.subscribers;

import jakarta.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.Redirect;
import org.apache.causeway.applib.annotation.SemanticsOf;

import demoapp.dom.domain.collections.Collection.domainEvent.CollectionDomainEventVm;
import lombok.RequiredArgsConstructor;


//tag::class[]
@Action(
    semantics = SemanticsOf.IDEMPOTENT
    , choicesFrom = "controlChildren"
)
@ActionLayout(
    promptStyle = PromptStyle.INLINE_AS_IF_EDIT
    , redirectPolicy = Redirect.EVEN_IF_SAME                        // <.>
    , sequence = "1")
@RequiredArgsConstructor
public class CollectionDomainEventVm_controlChildrenVisibility {

    private final CollectionDomainEventVm collectionDomainEventVm;

    @MemberSupport public CollectionDomainEventVm act(final CollectionDomainEventControlStrategy controlStrategy) {
        eventControlService.controlStrategy = controlStrategy;
        return collectionDomainEventVm;
    }
    @MemberSupport public CollectionDomainEventControlStrategy default0Act() {
        return eventControlService.controlStrategy;
    }

    @Inject
    CollectionDomainEventControlService eventControlService;
}
//end::class[]
