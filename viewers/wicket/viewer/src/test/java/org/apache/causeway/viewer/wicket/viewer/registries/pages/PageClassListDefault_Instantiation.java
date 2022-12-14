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
package org.apache.causeway.viewer.wicket.viewer.registries.pages;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.causeway.viewer.wicket.ui.pages.PageClassList;

class PageClassListDefault_Instantiation {

    @Test
    void shouldCauseAllPagesToBeRegistered() {
        // necessary to provide an implementation that will register
        // all pages with the registry.
        final PageClassListDefault pageClassList = new PageClassListDefault();
        newPageClassRegistryDefault(pageClassList);
    }

    @Test
    void shouldFailIfNoPagesRegistered() {
        // no side effects, ie doesn't register
        final PageClassList mockPageClassList = Mockito.mock(PageClassList.class);
        assertThrows(IllegalStateException.class, ()->{
            newPageClassRegistryDefault(mockPageClassList);
        });
    }

    // -- HELPER

    static PageClassRegistryDefault newPageClassRegistryDefault(final PageClassList pageClassList) {
        PageClassRegistryDefault pageClassRegistry = new PageClassRegistryDefault(pageClassList);
        pageClassRegistry.init();
        return pageClassRegistry;
    }

}
