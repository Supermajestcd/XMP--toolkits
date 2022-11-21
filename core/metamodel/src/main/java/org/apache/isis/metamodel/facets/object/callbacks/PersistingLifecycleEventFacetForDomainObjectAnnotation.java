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

package org.apache.isis.metamodel.facets.object.callbacks;

import org.apache.isis.applib.events.lifecycle.ObjectPersistingEvent;
import org.apache.isis.commons.internal.base._Casts;
import org.apache.isis.metamodel.facetapi.FacetHolder;
import org.apache.isis.metamodel.facets.SingleClassValueFacetAbstract;

public class PersistingLifecycleEventFacetForDomainObjectAnnotation extends SingleClassValueFacetAbstract implements LoadedLifecycleEventFacet {

    static Class<PersistingLifecycleEventFacet> type() {
        return PersistingLifecycleEventFacet.class;
    }

    public PersistingLifecycleEventFacetForDomainObjectAnnotation(
            final FacetHolder holder,
            final Class<? extends ObjectPersistingEvent<?>> value) {
        super(type(), holder, value);
    }

    @Override
    public Class<? extends ObjectPersistingEvent<?>> getEventType() {
        return _Casts.uncheckedCast(value());
    }


}
