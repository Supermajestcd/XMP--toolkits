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

package org.apache.isis.viewer.wicket.ui.components.scalars.jdkdates;


import java.sql.Timestamp;

import org.apache.isis.viewer.wicket.model.models.ScalarModel;
import org.apache.isis.viewer.wicket.ui.components.scalars.ScalarPanelTextFieldDatePickerAbstract;

/**
 * Panel for rendering scalars of type {@link java.sql.Timestamp}.
 */
public class JavaSqlTimestampPanel extends ScalarPanelTextFieldDatePickerAbstract<Timestamp> {

    private static final long serialVersionUID = 1L;

    public JavaSqlTimestampPanel(final String id, final ScalarModel scalarModel) {
        super(id, scalarModel, java.sql.Timestamp.class);
        init(new DateConverterForJavaSqlTimestamp(getWicketViewerSettings(), getAdjustBy()));
    }

    @Override
    protected Integer getLengthAdjustHint() {
        return +3;
    }

    @Override
    protected String getScalarPanelType() {
        return "javaSqlTimestampPanel";
    }

}
