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
package org.apache.isis.applib.services.queryresultscache;

/**
 * Used to enable or disable caching by {@link QueryResultsCache}.
 *
 * <p>
 *     This mechanism was introduced to allow fixtures module to be able to
 *     disable the {@link QueryResultsCache} while fixtures are being
 *     installed.
 * </p>
 *
 * @since 1.x {@index}
 */
public interface QueryResultsCacheControl {

    /**
     * Whether the {@link QueryResultsCache} should be disabled, in other
     * words to stop using any cached results.
     *
     * @return
     */
    boolean isIgnoreCache();

}
