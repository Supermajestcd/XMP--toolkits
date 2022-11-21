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

package org.apache.isis.metamodel.facets.actions.layout;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.layout.component.ActionLayoutData;
import org.apache.isis.metamodel.facetapi.FacetHolder;
import org.apache.isis.metamodel.facets.object.bookmarkpolicy.BookmarkPolicyFacet;
import org.apache.isis.metamodel.facets.object.bookmarkpolicy.BookmarkPolicyFacetAbstract;

public class BookmarkPolicyFacetForActionXml extends BookmarkPolicyFacetAbstract {

    public static BookmarkPolicyFacet create(final ActionLayoutData actionLayout, final FacetHolder holder) {
        if (actionLayout == null) {
            return null;
        }
        final BookmarkPolicy bookmarkPolicy = actionLayout.getBookmarking();
        return bookmarkPolicy != null && bookmarkPolicy != BookmarkPolicy.NEVER ? new BookmarkPolicyFacetForActionXml(bookmarkPolicy, holder) : null;
    }

    private BookmarkPolicyFacetForActionXml(final BookmarkPolicy bookmarkPolicy, final FacetHolder holder) {
        super(bookmarkPolicy, holder);
    }

}
