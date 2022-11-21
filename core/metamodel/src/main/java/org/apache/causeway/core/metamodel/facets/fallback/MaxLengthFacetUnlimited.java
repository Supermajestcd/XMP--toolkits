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
package org.apache.causeway.core.metamodel.facets.fallback;

import org.apache.causeway.core.metamodel.facetapi.FacetHolder;
import org.apache.causeway.core.metamodel.facetapi.HasSemanticEqualityByClass;
import org.apache.causeway.core.metamodel.facets.objectvalue.maxlen.MaxLengthFacetAbstract;
import org.apache.causeway.core.metamodel.interactions.ValidityContext;

public class MaxLengthFacetUnlimited
extends MaxLengthFacetAbstract
implements HasSemanticEqualityByClass {

    public MaxLengthFacetUnlimited(final FacetHolder holder) {
        super(Integer.MAX_VALUE, holder, Precedence.FALLBACK);
    }

    /**
     * No limit to maximum length.
     */
    @Override
    public String invalidates(final ValidityContext context) {
        return null;
    }

}
