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

package org.apache.isis.core.metamodel.facets.value.temporal.offsetdatetime;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.value.temporal.TemporalAdjust;
import org.apache.isis.core.metamodel.facets.value.temporal.TemporalValueFacet;
import org.apache.isis.core.metamodel.facets.value.temporal.TemporalValueSemanticsProviderAbstract;

import lombok.val;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class OffsetDateTimeValueSemanticsProvider 
extends TemporalValueSemanticsProviderAbstract<OffsetDateTime> {

    public static final int MAX_LENGTH = 36;
    public static final int TYPICAL_LENGTH = 22;
    
    public OffsetDateTimeValueSemanticsProvider(final FacetHolder holder) {
        super(TemporalValueFacet.class,
                TemporalCharacteristic.DATE_TIME, OffsetCharacteristic.OFFSET,
                holder, OffsetDateTime.class, TYPICAL_LENGTH, MAX_LENGTH,
                OffsetDateTime::from,
                TemporalAdjust::adjustOffsetDateTime);
        
        val basicDateTimeNoMillis = "yyyyMMdd'T'HHmmssZ";
        val basicDateTime = "yyyyMMdd'T'HHmmss.SSSZ";
        
        super.addNamedFormat("long", "LL");
        super.addNamedFormat("medium", "MM");
        super.addNamedFormat("short", "SS");
        super.addNamedFormat("iso", basicDateTimeNoMillis);
        super.addNamedFormat("iso_encoding", basicDateTime);
        
        super.updateParsers();

        setEncodingFormatter(DateTimeFormatter.ofPattern(basicDateTime, Locale.getDefault()));

        val configuredNameOrPattern = getConfiguration().getValueTypes().getJavaTime().getOffsetDateTime().getFormat();

        val formatter = lookupNamedFormatter(configuredNameOrPattern).orElse(null);

        DateTimeFormatter result = null;
        if(formatter!=null) {
            result = formatter;
        } else {
            try {
                result = DateTimeFormatter.ofPattern(configuredNameOrPattern, Locale.getDefault());
            } catch (Exception e) {
                log.warn(e);
            }
            if (result == null) {
                result = lookupNamedFormatterElseFail("medium");
            }
        }

        setTitleFormatter(result);
        
        
    }

}
