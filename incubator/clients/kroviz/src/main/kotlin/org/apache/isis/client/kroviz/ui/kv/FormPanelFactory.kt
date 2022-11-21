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

import org.apache.isis.client.kroviz.to.ValueType
import org.apache.isis.client.kroviz.ui.FormItem
import org.apache.isis.client.kroviz.utils.DateHelper
import org.apache.isis.client.kroviz.utils.UUID
import io.kvision.core.Component
import io.kvision.core.Overflow
import io.kvision.core.StringPair
import io.kvision.core.onEvent
import io.kvision.form.FormPanel
import io.kvision.form.check.CheckBox
import io.kvision.form.formPanel
import io.kvision.form.range.Range
import io.kvision.form.select.SimpleSelect
import io.kvision.form.spinner.Spinner
import io.kvision.form.text.Password
import io.kvision.form.text.Text
import io.kvision.form.text.TextArea
import io.kvision.form.time.DateTime
import io.kvision.form.time.dateTime
import io.kvision.html.Div
import io.kvision.html.Iframe
import io.kvision.html.Image
import io.kvision.html.image
import io.kvision.panel.VPanel
import io.kvision.panel.vPanel
import io.kvision.require
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.px

class FormPanelFactory(items: List<FormItem>) : VPanel() {

    var panel: FormPanel<String>?

    init {
        panel = formPanel {
            height = auto
            margin = 10.px
            for (fi: FormItem in items) {
                when (fi.type) {
                    ValueType.TEXT -> add(createText(fi))
                    ValueType.PASSWORD -> add(createPassword(fi))
                    ValueType.TEXT_AREA -> add(createTextArea(fi))
                    ValueType.SIMPLE_SELECT -> add(createSelect(fi))
                    ValueType.HTML -> add(createHtml(fi))
                    ValueType.NUMERIC -> add(createNumeric(fi))
                    ValueType.DATE -> add(createDate(fi))
                    ValueType.TIME -> add(createTime(fi))
                    ValueType.BOOLEAN -> add(createBoolean(fi))
                    ValueType.IMAGE -> add(createImage(fi))
                    ValueType.SLIDER -> add(createSlider(fi))
                    ValueType.IFRAME -> add(createIFrame(fi))
                    ValueType.SVG_INLINE -> add(createSvgInline(fi))
                    ValueType.SVG_MAPPED -> add(createSvgMap(fi))
                }
            }
        }
    }

    private fun createBoolean(fi: FormItem): Component {
        if (fi.content == "true") {
            return CheckBox(label = fi.label, value = true)
        }
        if (fi.content == "false") {
            return CheckBox(label = fi.label, value = false)
        }
        return createText(fi)
    }

    private fun createTime(fi: FormItem): DateTime {
        val date = DateHelper.toDate(fi.content)
        return dateTime(format = "YYYY-MM-DD HH:mm", label = fi.label, value = date)
    }

    private fun createDate(fi: FormItem): DateTime {
        val date = DateHelper.toDate(fi.content)
        return dateTime(
                format = "YYYY-MM-DD",
                label = fi.label,
                value = date
        )
    }

    private fun createNumeric(fi: FormItem): Spinner {
        return Spinner(label = fi.label, value = fi.content as Long)
    }

    private fun createHtml(fi: FormItem): Component {
        return Div(rich = true, content = fi.content.toString())
    }

    private fun createText(fi: FormItem): Text {
        val item = Text(label = fi.label, value = fi.content.toString())
        item.readonly = fi.member?.isReadOnly()
        item.onEvent {
            change = {
                fi.changed(item.value)
                it.stopPropagation()
            }
        }
        return item
    }

    private fun createPassword(fi: FormItem): Password {
        return Password(label = fi.label, value = fi.content as String)
    }

    private fun createTextArea(fi: FormItem): TextArea {
        val rows = fi.size
        val item: TextArea = if (rows != null) {
            val rowCnt = maxOf(3, rows)
            TextArea(label = fi.label, value = fi.content as String, rows = rowCnt)
        } else {
            TextArea(label = fi.label, value = fi.content as String)
        }
        item.readonly = fi.readOnly
        item.onEvent {
            change = {
                fi.changed(item.value)
                it.stopPropagation()
            }
        }
        item.height = 100.perc
        return item
    }

    private fun createSelect(fi: FormItem): SimpleSelect {
        @Suppress("UNCHECKED_CAST")
        val list = fi.content as List<StringPair>
        var preSelectedValue: String? = null
        if (list.isNotEmpty()) {
            preSelectedValue = list.first().first
        }
        return SimpleSelect(label = fi.label, options = list, value = preSelectedValue)
    }

    private fun createImage(fi: FormItem): VPanel {
        val panel = VPanel {
            val fc = fi.content
            when {
                fc is Image -> fc
                fc is String -> {
                    // interpret as (file) URL and load locally
                    console.log("[FPF.createImage]")
                    console.log(fc)
                    // TODO passing url as string does not work:
                    // require resolves string to url and `compiles` it into the binary
                    // working with remote resources allows to me more dynamic
                    image(
                            require("img/kroviz-logo.svg"))
                }
                else -> {
                }
            }
        }
        panel.height = auto
        panel.width = 100.perc
        return panel
    }

    private fun createSvgInline(fi: FormItem): VPanel {
        val panel = VPanel {
            when (val fcb = fi.callBack) {
                is UUID -> {
                    // add InnerPanel to be replaced by callback with svg
                    vPanel {
                        id = fcb.value
                    }
                }
                else -> {
                }
            }
        }
        panel.height = auto
        panel.width = 100.perc
        return panel
    }

    private fun createSvgMap(fi: FormItem): MapPanel {
        val panel = MapPanel()
        panel.height = 100.perc
        panel.width = 100.perc
        fi.callBack = panel
        return panel
    }

    private fun createSlider(fi: FormItem): Range {
        //IMPROVE this needs to be amended for other ranges
        val item = Range(label = fi.label, min = 0, max = 1.0, step = 0.1, value = fi.content as Float)
        item.onEvent {
            change = {
                fi.changed(item.value!!.toString())
                it.stopPropagation()
            }
        }
        return item
    }

    private fun createIFrame(fi: FormItem): VPanel {
        val item = VPanel {
            val url = fi.content as String
            val iframe = Iframe(url)
            iframe.height = 100.perc
            iframe.width = 100.perc
            iframe.overflow = Overflow.INHERIT
            add(iframe)
        }
        item.height = 100.perc
        item.width = 100.perc
        item.overflow = Overflow.INHERIT
        return item
    }

}
