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
package org.apache.isis.core.metamodel.facets.properties.propertylayout;

import java.util.Optional;

import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.RenderDay;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.objectvalue.daterenderedadjust.DateRenderAdjustFacetAbstract;

@Deprecated(forRemoval = true, since = "2.0.0-M7")
public class RenderedAdjustedFacetForPropertyLayoutAnnotation
extends DateRenderAdjustFacetAbstract {

    public static Optional<RenderedAdjustedFacetForPropertyLayoutAnnotation> create(
            final Optional<PropertyLayout> propertyLayoutIfAny,
            final FacetHolder holder) {

        return propertyLayoutIfAny
        .map(PropertyLayout::renderDay)
        .filter(renderDay -> renderDay != RenderDay.NOT_SPECIFIED)
        .map(renderDay -> {
            switch (renderDay) {
            case AS_DAY:
                return null;
            case AS_DAY_BEFORE:
                return new RenderedAdjustedFacetForPropertyLayoutAnnotation(holder);
            default:
            }
            throw new IllegalStateException("renderDay '" + renderDay + "' not recognised");
        });
    }

    public static final int ADJUST_BY = -1;

    private RenderedAdjustedFacetForPropertyLayoutAnnotation(final FacetHolder holder) {
        super(ADJUST_BY, holder);
    }

}
