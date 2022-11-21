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
package org.apache.isis.core.metamodel.services.ixn;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.commons.collections.Can;
import org.apache.isis.core.metamodel.spec.ManagedObject;
import org.apache.isis.core.metamodel.spec.feature.ObjectAction;
import org.apache.isis.core.metamodel.spec.feature.OneToOneAssociation;
import org.apache.isis.schema.ixn.v2.ActionInvocationDto;
import org.apache.isis.schema.ixn.v2.PropertyEditDto;

public interface InteractionDtoServiceInternal {

    @Programmatic
    ActionInvocationDto asActionInvocationDto(
            ObjectAction objectAction,
            ManagedObject targetAdapter,
            Can<ManagedObject> argumentAdapters);

    @Programmatic
    PropertyEditDto asPropertyEditDto(
            OneToOneAssociation property,
            ManagedObject targetAdapter,
            ManagedObject newValueAdapterIfAny);

    @Programmatic
    ActionInvocationDto updateResult(
            ActionInvocationDto actionInvocationDto,
            ObjectAction objectAction,
            Object resultPojo);


}
