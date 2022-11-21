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
package org.apache.isis.client.kroviz.ui.kv

import io.kvision.core.CssSize
import io.kvision.core.UNIT
import io.kvision.panel.SimplePanel
import io.kvision.panel.TabPanel
import io.kvision.panel.VPanel
import io.kvision.utils.perc

class RoTabPanel : TabPanel() {

    init {
        width = 100.perc
        marginTop = CssSize(40, UNIT.px)
    }

    override fun removeTab(index: Int): TabPanel {
        val tab = getTabs().get(index)
        RoView.removeTab(tab as SimplePanel)
        return super.removeTab(index)
    }

    fun findTab(title: String): Int? {
        getTabs().forEachIndexed { index, component ->
            if ((component is VPanel) && (component.title == title)) {
                return index
            }
        }
        return null
    }

}
