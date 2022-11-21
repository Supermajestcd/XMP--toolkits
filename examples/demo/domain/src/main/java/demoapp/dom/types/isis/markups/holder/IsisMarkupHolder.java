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
package demoapp.dom.types.isis.markups.holder;

import org.apache.isis.applib.annotation.LogicalTypeName;
import org.apache.isis.applib.value.Markup;

@LogicalTypeName("demo.IsisMarkupHolder")
//tag::class[]
public interface IsisMarkupHolder {

    Markup getReadOnlyProperty();
    void setReadOnlyProperty(Markup c);

    Markup getReadWriteProperty();
    void setReadWriteProperty(Markup c);

    Markup getReadOnlyOptionalProperty();
    void setReadOnlyOptionalProperty(Markup c);

    Markup getReadWriteOptionalProperty();
    void setReadWriteOptionalProperty(Markup c);

}
//end::class[]
