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
package org.apache.isis.testing.unittestsupport.applib.dom.pojo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.apache.isis.testing.unittestsupport.applib.dom.pojo.holders.JodaLocalDateHolder;

import lombok.val;

import org.opentest4j.AssertionFailedError;

public class PojoTester_datatypes_no_data_factory_Test {

    @Test
    public void exercise_data_not_provided() {

        // given
        val holder = new JodaLocalDateHolder();

        // when
        Assertions.assertThatThrownBy(() -> {
            PojoTester.create()
                    // .usingData(DataForJodaTime.localDates()) // not provided
                    .exercise(holder);
        }).isInstanceOf(AssertionFailedError.class)
                .hasMessageContaining("No test data is available for setSomeLocalDate");
    }



}
