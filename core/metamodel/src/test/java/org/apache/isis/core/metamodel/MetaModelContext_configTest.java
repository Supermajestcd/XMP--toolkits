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
package org.apache.isis.core.metamodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.isis.core.metamodel._testing.MetaModelContext_forTesting;
import org.apache.isis.core.metamodel.context.MetaModelContext;

class MetaModelContext_configTest {

    private MetaModelContext mmc;
    
    @BeforeEach
    void setUp() {
        mmc = MetaModelContext_forTesting.buildDefault();
    }

    @Test
    void shouldReturnEmptyValue() {
        assertEquals(null, environment().getProperty("test"));
    }

    @Test
    void shouldAllowOverrideForTesting() {

        mmcForTesting()
        .runWithConfigProperties(
            map->map.put("test", "Hello World!"),
            ()->{
                assertEquals("Hello World!", environment().getProperty("test"));
            });

        // expected post condition
        assertEquals(null, environment().getProperty("test"));
    }

    // -- HELPER

    private MetaModelContext_forTesting mmcForTesting() {
        return ((MetaModelContext_forTesting) mmc);
    }

    private ConfigurableEnvironment environment() {
        return mmcForTesting().getConfiguration().getEnvironment();
    }
    
}
