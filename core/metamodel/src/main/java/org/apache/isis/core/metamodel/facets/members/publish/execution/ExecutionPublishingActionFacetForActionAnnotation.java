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

package org.apache.isis.core.metamodel.facets.members.publish.execution;

import java.util.Optional;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Publishing;
import org.apache.isis.core.config.IsisConfiguration;
import org.apache.isis.core.config.metamodel.facets.PublishingPolicies;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.actions.semantics.ActionSemanticsFacet;

import lombok.val;

public class ExecutionPublishingActionFacetForActionAnnotation 
extends ExecutionPublishingFacetAbstract {

    public static ExecutionPublishingFacet create(
            final Optional<Action> actionsIfAny,
            final IsisConfiguration configuration,
            final FacetHolder holder) {

        val publishingPolicy = PublishingPolicies.actionExecutionPublishingPolicy(configuration);

        return actionsIfAny
                .map(Action::executionPublishing)
                .filter(publishing -> publishing != Publishing.NOT_SPECIFIED)
                .<ExecutionPublishingFacet>map(publishing -> {
                    switch (publishing) {
                    case AS_CONFIGURED:

                        switch (publishingPolicy) {
                        case NONE:
                            return null;
                        case IGNORE_QUERY_ONLY:
                        case IGNORE_SAFE:
                            if (hasSafeSemantics(holder)) {
                                return null;
                            }
                            // else fall through
                        default:
                            return new ExecutionPublishingActionFacetForActionAnnotationAsConfigured(holder);
                        }
                    case DISABLED:
                        return null;
                    case ENABLED:
                        return new ExecutionPublishingActionFacetForActionAnnotation(holder);
                    default:
                    }
                    throw new IllegalStateException("publishing '" + publishing + "' not recognised");
                })
                .orElseGet(() -> {
                    switch (publishingPolicy) {
                    case NONE:
                        return null;
                    case IGNORE_QUERY_ONLY:
                    case IGNORE_SAFE:
                        if (hasSafeSemantics(holder)) {
                            return null;
                        }
                        // else fall through
                    default:
                        return new ExecutionPublishingActionFacetFromConfiguration(holder);
                    }
                });
    }

    private static boolean hasSafeSemantics(final FacetHolder holder) {
        final ActionSemanticsFacet actionSemanticsFacet = holder.getFacet(ActionSemanticsFacet.class);
        if(actionSemanticsFacet == null) {
            throw new IllegalStateException("Require ActionSemanticsFacet in order to process");
        }

        return actionSemanticsFacet.value().isSafeInNature();
    }

    ExecutionPublishingActionFacetForActionAnnotation(
            final FacetHolder holder) {
        super(holder);
    }

}
