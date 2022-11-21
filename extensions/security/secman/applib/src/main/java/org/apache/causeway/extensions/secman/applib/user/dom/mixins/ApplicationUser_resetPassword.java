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
package org.apache.causeway.extensions.secman.applib.user.dom.mixins;

import java.util.Objects;

import javax.inject.Inject;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.value.Password;
import org.apache.causeway.extensions.secman.applib.CausewayModuleExtSecmanApplib;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUser;
import org.apache.causeway.extensions.secman.applib.user.dom.ApplicationUserRepository;
import org.apache.causeway.extensions.secman.applib.user.dom.mixins.ApplicationUser_resetPassword.DomainEvent;

import lombok.RequiredArgsConstructor;

/**
 *
 * @since 2.0 {@index}
 */
@Action(
        domainEvent = DomainEvent.class,
        semantics = SemanticsOf.IDEMPOTENT
)
@ActionLayout(
        associateWith = "hasPassword",
        sequence = "20"
)
@RequiredArgsConstructor
public class ApplicationUser_resetPassword {

    public static class DomainEvent
            extends CausewayModuleExtSecmanApplib.ActionDomainEvent<ApplicationUser_resetPassword> {}

    @Inject private ApplicationUserRepository applicationUserRepository;

    private final ApplicationUser target;

    @MemberSupport public ApplicationUser act(
            final Password newPassword,
            final Password repeatPassword) {

        applicationUserRepository.updatePassword(target, newPassword.getPassword());
        return target;
    }

    @MemberSupport public boolean hideAct() {
        return !applicationUserRepository.isPasswordFeatureEnabled(target);
    }

    @MemberSupport public String validateAct(
            final Password newPassword,
            final Password repeatPassword) {

        if(!applicationUserRepository.isPasswordFeatureEnabled(target)) {
            return "Password feature is not available for this User";
        }

        if (!Objects.equals(newPassword, repeatPassword)) {
            return "Passwords do not match";
        }

        return null;
    }


}
