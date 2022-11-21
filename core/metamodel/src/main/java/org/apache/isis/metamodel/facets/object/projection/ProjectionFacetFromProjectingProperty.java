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

package org.apache.isis.metamodel.facets.object.projection;

import org.apache.isis.applib.annotation.Projecting;
import org.apache.isis.metamodel.facetapi.Facet;
import org.apache.isis.metamodel.facetapi.FacetHolder;
import org.apache.isis.metamodel.facets.properties.projection.ProjectingFacet;
import org.apache.isis.metamodel.spec.ManagedObject;
import org.apache.isis.metamodel.spec.ObjectSpecification;
import org.apache.isis.metamodel.spec.feature.Contributed;
import org.apache.isis.metamodel.spec.feature.OneToOneAssociation;

import lombok.val;

public class ProjectionFacetFromProjectingProperty extends ProjectionFacetAbstract {

    public static Class<? extends Facet> type() {
        return ProjectionFacet.class;
    }

    private final OneToOneAssociation projectingProperty;

    private ProjectionFacetFromProjectingProperty(
            final OneToOneAssociation projectingProperty,
            final FacetHolder holder) {
        
        super(holder);
        this.projectingProperty = projectingProperty;
    }

    public static ProjectionFacet create(final ObjectSpecification objectSpecification) {
        return objectSpecification.streamProperties(Contributed.EXCLUDED)
        .filter(propertySpec -> {
            val projectingFacet = propertySpec.getFacet(ProjectingFacet.class);
            return projectingFacet != null && !projectingFacet.isNoop()
                    && projectingFacet.value() == Projecting.PROJECTED;
        })
        .findFirst()
        .map(propertySpec -> new ProjectionFacetFromProjectingProperty(propertySpec, objectSpecification))
        .orElse(null);
    }

    @Override
    public ManagedObject projected(final ManagedObject owningAdapter) {
        return projectingProperty.get(owningAdapter);
    }
}
