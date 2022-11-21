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
package org.apache.causeway.applib.services.error;

/**
 * Optional SPI service providing the ability to record errors occurring in the application into an external incident
 * recording system (such as JIRA) and to provide a user-friendly (jargon-free) error message to the end-user, along
 * with incident reference.
 *
 * <p>
 * The `Ticket` object returned by this service contains a number of elements:
 *
 * <ul>
 *     <li>
 *          a user-friendly (jargon-free) error message to be returned and rendered to the end-user
 *     </li>
 *     <li>
 *          an optional incident reference (eg a JIRA issue `XXX-1234`).
 *     </li>
 *          a URL for an external image.  For example, this could be to a
 *          comic strip or (as a bit of fun) a picture of a random kitten.
 *     </li>
 *     <li>
 * </ul>
 * </p>
 *
 * <p>
 *  The information this object can then be sed by the configured viewer,
 *  rendered whenever an error occurs.  At the time of writing this is
 *  supported by the Wicket viewer.
 * </p>
 *
 * @since 2.0 {@index}
 */
public interface ErrorReportingService {

    Ticket reportError(final ErrorDetails errorDetails);

}
