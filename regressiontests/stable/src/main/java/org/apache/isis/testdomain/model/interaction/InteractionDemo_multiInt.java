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
package org.apache.isis.testdomain.model.interaction;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.MemberSupport;

import lombok.RequiredArgsConstructor;

@Action
@RequiredArgsConstructor
public class InteractionDemo_multiInt {

    @SuppressWarnings("unused")
    private final InteractionDemo holder;

    @MemberSupport
    public int act(int a, int b, int c) {
        return a * (b + c);
    }

    // -- PARAM A

    @MemberSupport
    public int defaultA(Parameters.TriInt p) {
        return 2;
    }

    @MemberSupport
    public int[] choicesA(Parameters.TriInt p) {
        return new int[] {1, 2, 3, 4};
    }

    // -- PARAM B

    @MemberSupport
    public int defaultB(Parameters.TriInt p) {
        return 3;
    }

    @MemberSupport
    public int[] choicesB(Parameters.TriInt p) {
        return new int[] {1, 2, 3, 4};
    }

    // -- PARAM C

    @MemberSupport
    public int defaultC(Parameters.TriInt p) {
        return 4;
    }

    @MemberSupport
    public int[] choicesC(Parameters.TriInt p) {
        return new int[] {1, 2, 3, 4};
    }
}
