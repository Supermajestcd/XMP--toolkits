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

package org.apache.isis.viewer.wicket.ui.pages.accmngt.password_reset;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.util.cookies.CookieUtils;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import org.apache.isis.applib.services.userreg.EmailNotificationService;
import org.apache.isis.applib.services.userreg.events.PasswordResetEvent;
import org.apache.isis.viewer.wicket.model.models.PageType;
import org.apache.isis.viewer.wicket.ui.components.widgets.bootstrap.FormGroup;
import org.apache.isis.viewer.wicket.ui.pages.EmailVerificationUrlService;
import org.apache.isis.viewer.wicket.ui.pages.PageNavigationService;
import org.apache.isis.viewer.wicket.ui.pages.accmngt.AccountManagementPageAbstract;
import org.apache.isis.viewer.wicket.ui.pages.accmngt.EmailAvailableValidator;
import org.apache.isis.viewer.wicket.ui.panels.PanelBase;

/**
 * A panel with a form for creation of new users
 */
public class PasswordResetEmailPanel extends PanelBase<Void> {

    private static final long serialVersionUID = 1L;

    @Inject private transient EmailNotificationService emailNotificationService;
    @Inject private transient EmailVerificationUrlService emailVerificationUrlService;
    @Inject private transient PageNavigationService pageNavigationService;
//    @Inject private transient WebAppConfigBean webAppConfigBean;

    /**
     * Constructor
     *
     * @param id
     *            the component id
     */
    public PasswordResetEmailPanel(final String id) {
        super(id);

        StatelessForm<Void> form = new StatelessForm<>("signUpForm");
        addOrReplace(form);

        final RequiredTextField<String> emailField = new RequiredTextField<>("email", Model.of(""));
        emailField.setLabel(new ResourceModel("emailLabel"));
        emailField.add(EmailAddressValidator.getInstance());
        emailField.add(EmailAvailableValidator.exists(commonContext));

        FormGroup formGroup = new FormGroup("formGroup", emailField);
        form.add(formGroup);

        formGroup.add(emailField);

        Button signUpButton = new Button("passwordResetSubmit") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit() {
                super.onSubmit();

                String email = emailField.getModelObject();

                String confirmationUrl = emailVerificationUrlService.createVerificationUrl(PageType.PASSWORD_RESET, email);

                //TODO [2033] remove ...                
                //                /**
                //                 * We have to init() the services here because the Isis runtime is not available to us
                //                 * (guice will have instantiated a new instance of the service).
                //                 *
                //                 * We do it this way just so that the programming model for the EmailService is similar to regular Isis-managed services.
                //                 */
                //                emailNotificationService.init();
                //                emailService.init();

                final PasswordResetEvent passwordResetEvent = new PasswordResetEvent(
                        email, 
                        confirmationUrl, 
                        webAppConfigBean.getApplicationName());

                boolean emailSent = emailNotificationService.send(passwordResetEvent);
                if (emailSent) {
                    Map<String, String> map = new HashMap<>();
                    map.put("email", email);
                    IModel<Map<String, String>> model = Model.ofMap(map);
                    String emailSentMessage = getString("emailSentMessage", model);

                    CookieUtils cookieUtils = new CookieUtils();
                    cookieUtils.save(AccountManagementPageAbstract.FEEDBACK_COOKIE_NAME, emailSentMessage);
                    pageNavigationService.navigateTo(PageType.SIGN_IN);
                }
            }
        };

        form.add(signUpButton);
    }



}
