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
package demoapp.dom.domain.properties.PropertyLayout.renderDay;

import java.time.LocalDate;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Optionality;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.ParameterLayout;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.annotation.ValueSemantics;

import lombok.RequiredArgsConstructor;

@Action(
    semantics = SemanticsOf.IDEMPOTENT
)
@ActionLayout(
    associateWith = "endDateUsingMetaAnnotationButOverridden"
    , sequence = "1")
@RequiredArgsConstructor
public class PropertyLayoutRenderDayVm_updateEndDateWithMetaAnnotationOverridden {

    private final PropertyLayoutRenderDayVm propertyLayoutRenderDayVm;

//tag::meta-annotation-overridden[]
    @MemberSupport public PropertyLayoutRenderDayVm act(
            @RenderDayMetaAnnotationStartDateInclusive          // <.>
            @Parameter(optionality = Optionality.OPTIONAL)
            @ValueSemantics(dateRenderAdjustDays = ValueSemantics.AS_DAY_BEFORE)          // <.>
            @ParameterLayout(describedAs =
                    "@RenderDayMetaAnnotationStartDateInclusive " +
                    "@ValueSemantics(dateRenderAdjustDays = ValueSemantics.AS_DAY_BEFORE)"
            )
            final LocalDate endDate) {
        propertyLayoutRenderDayVm.setEndDateUsingMetaAnnotationButOverridden(endDate);
        return propertyLayoutRenderDayVm;
    }
//end::meta-annotation-overridden[]
    @MemberSupport public LocalDate default0Act() {
        return propertyLayoutRenderDayVm.getEndDateUsingMetaAnnotationButOverridden();
    }

}
