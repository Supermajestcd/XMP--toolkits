/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.isis.client.kroviz.ui

import org.apache.isis.client.kroviz.handler.DomainTypeHandler
import org.apache.isis.client.kroviz.snapshots.simpleapp1_16_0.SO
import org.apache.isis.client.kroviz.to.DomainType
import org.apache.isis.client.kroviz.ui.core.UiManager
import org.apache.isis.client.kroviz.ui.diagram.ClassDiagram
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class ClassDiagramTest {

    @BeforeTest
    fun setup() {
        val user = "sven"
        val pw = "pass"
        val url = "http://${user}:${pw}@localhost:8080/restful/"
        UiManager.login(url, user, pw)
    }

    @Test
    fun testSimpleObject() {
        //given
        val pkg = "domainapp.modules.simple.dom.impl"
        val cls = "SimpleObject"

        val jsonStr = SO.str
        val domainType = DomainTypeHandler().parse(jsonStr) as DomainType

        //when
        val actual = ClassDiagram.with(domainType)
        //then
        assertTrue(actual.startsWith("\"@startuml"))
        assertTrue(actual.endsWith("@enduml\""))
        assertTrue(actual.contains("package $pkg {\\n"))
        assertTrue(actual.contains("class $cls\\n"))
    }

}
