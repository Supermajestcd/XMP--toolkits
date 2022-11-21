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

package org.apache.isis.metamodel.facets;

import java.util.Map;

import org.apache.isis.applib.annotation.Where;
import org.apache.isis.metamodel.facetapi.Facet;
import org.apache.isis.metamodel.facetapi.FacetAbstract;
import org.apache.isis.metamodel.facetapi.FacetHolder;

public abstract class WhereValueFacetAbstract extends FacetAbstract implements WhereValueFacet {
    private final Where where;

    public WhereValueFacetAbstract(
            final Class<? extends Facet> facetType,
            final FacetHolder holder,
            final Where where) {
        super(facetType, holder, Derivation.NOT_DERIVED);
        this.where = where;
    }

    @Override
    public Where where() {
        return where;
    }

    @Override
    protected String toStringValues() {
        return super.toStringValues() + "; where =" + where.getFriendlyName();
    }

    @Override
    public void appendAttributesTo(final Map<String, Object> attributeMap) {
        super.appendAttributesTo(attributeMap);
        attributeMap.put("where", where);
    }

}
