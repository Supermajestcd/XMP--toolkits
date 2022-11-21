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
package org.apache.isis.subdomains.base.applib.with;

import org.apache.isis.testing.unittestsupport.applib.dom.bidir.Instantiator;

public class InstantiatorForComparableByReference implements Instantiator {
    public final Class<? extends WithReferenceComparable<?>> cls;
    private int i;

    public InstantiatorForComparableByReference(
            Class<? extends WithReferenceComparable<?>> cls) {
        this.cls = cls;
    }

    @Override
    public Object instantiate() {
        WithReferenceComparable<?> newInstance;
        try {
            newInstance = cls.newInstance();
            newInstance.setReference(""+(++i));
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
