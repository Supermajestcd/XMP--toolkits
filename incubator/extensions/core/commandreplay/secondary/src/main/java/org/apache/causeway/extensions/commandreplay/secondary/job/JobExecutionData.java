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
package org.apache.causeway.extensions.commandreplay.secondary.job;

import org.quartz.JobExecutionContext;

import lombok.RequiredArgsConstructor;

/**
 * Requires that the job is annotated with the {@link org.quartz.PersistJobDataAfterExecution} annotation.
 *
 * @since 2.0 {@index}
 */
@RequiredArgsConstructor
class JobExecutionData {

    private final JobExecutionContext context;

    /**
     * Lookup property from the job detail.
     */
    public String getString(final String key, final String defaultValue) {
        try {
            String v = context.getJobDetail().getJobDataMap().getString(key);
            return v != null ? v : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Save key into the job detail obtained from context.
     */
    public void setString(final String key, final String value) {
        context.getJobDetail().getJobDataMap().put(key, value);
    }

}
