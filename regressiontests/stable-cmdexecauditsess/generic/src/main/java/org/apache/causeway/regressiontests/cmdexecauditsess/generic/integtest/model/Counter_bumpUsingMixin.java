/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.causeway.regressiontests.cmdexecauditsess.generic.integtest.model;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.Publishing;
import org.apache.causeway.applib.services.wrapper.Mixin;

import lombok.RequiredArgsConstructor;

@Action(
        commandPublishing = Publishing.ENABLED,
        executionPublishing = Publishing.ENABLED
)
@RequiredArgsConstructor
public class Counter_bumpUsingMixin implements Mixin<Counter> {

    private final Counter counter;

    public Counter act() {
        return counter.doBump();
    }
}
