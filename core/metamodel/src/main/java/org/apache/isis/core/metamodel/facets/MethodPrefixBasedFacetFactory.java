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

package org.apache.isis.core.metamodel.facets;

import java.util.List;

import org.apache.isis.core.metamodel.facetapi.MetaModelValidatorRefiner;
import org.apache.isis.core.metamodel.specloader.facetprocessor.FacetProcessor;

/**
 * Indicates that the {@link FacetFactory} works by recognizing methods with a
 * certain prefix (or prefixes).
 * 
 * <p>
 * Used by {@link FacetProcessor#recognizes(java.lang.reflect.Method)}.
 */
public interface MethodPrefixBasedFacetFactory extends FacetFactory, MetaModelValidatorRefiner {

    /**
     * All prefixes recognized by this {@link FacetFactory}.
     */
    public List<String> getPrefixes();
}
