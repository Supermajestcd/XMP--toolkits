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

package org.apache.isis.metamodel.specloader;

import org.junit.jupiter.api.Test;

import org.apache.isis.metamodel.facetapi.Facet;
import org.apache.isis.metamodel.facets.actcoll.typeof.TypeOfFacet;
import org.apache.isis.metamodel.facets.collections.modify.CollectionFacet;
import org.apache.isis.metamodel.spec.ObjectSpecification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SpecificationLoaderTest_array extends SpecificationLoaderTestAbstract {

    @Override
    protected ObjectSpecification loadSpecification(final SpecificationLoader reflector) {
        return reflector.loadSpecification(ReflectorTestPojo[].class);
    }

    @Test
    public void testType() throws Exception {
        assertTrue(specification.isParentedOrFreeCollection());
    }

    @Test
    public void testName() throws Exception {
        assertEquals(ReflectorTestPojo[].class.getName(), specification.getFullIdentifier());
    }

    @Test
    @Override
    public void testCollectionFacet() throws Exception {
        final Facet facet = specification.getFacet(CollectionFacet.class);
        assertNotNull(facet);
    }

    @Test
    @Override
    public void testTypeOfFacet() throws Exception {
        final TypeOfFacet facet = specification.getFacet(TypeOfFacet.class);
        assertNotNull(facet);
        assertEquals(ReflectorTestPojo.class, facet.value());
    }

    //TODO [2033] performance testing the spec loader
    //    @Test @RepeatedTest(1000)
    //    public void testTypeOfFacet1000(TestInfo testInfo) throws Exception {
    //        final TypeOfFacet facet =
    //        _Timing.callVerbose("*** Loop " + testInfo.getDisplayName(), ()->specification.getFacet(TypeOfFacet.class));
    //        assertNull(facet);
    //    }


}
