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

package org.apache.isis.core.commons.ensure;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Ensure_GivenValueThatDoesNotMatchTest {

    @Test
    public void whenCallEnsureThatArgOverloadedShouldThrowIllegalArgumentExceptionUsingSuppliedMessage() {
        try {
            Ensure.ensureThatArg("foo", is(nullValue())::matches, "my message");
            fail();
        } catch (final IllegalArgumentException ex) {
            assertThat(ex.getMessage(), is("my message"));
        }
    }

    @Test
    public void whenCallEnsureThatStateOverloadedShouldThrowIllegalStateExceptionUsingSuppliedMessage() {
        try {
            Ensure.ensureThatState("foo", is(nullValue())::matches, "my message");
            fail();
        } catch (final IllegalStateException ex) {
            assertThat(ex.getMessage(), is("my message"));
        }
    }

    @Test
    public void whenCallEnsureThatContextOverloadedShouldThrowIllegalThreadStateExceptionUsingSuppliedMessage() {
        try {
            Ensure.ensureThatContext("foo", is(nullValue())::matches, "my message");
            fail();
        } catch (final IllegalThreadStateException ex) {
            assertThat(ex.getMessage(), is("my message"));
        }
    }

}
