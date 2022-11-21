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
package org.apache.isis.core.metamodel.facets.properties.property.command;

import org.apache.isis.applib.annotation.Command.ExecuteIn;
import org.apache.isis.applib.annotation.Command.Persistence;
import org.apache.isis.applib.annotation.CommandExecuteIn;
import org.apache.isis.applib.annotation.CommandPersistence;
import org.apache.isis.applib.annotation.CommandReification;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.core.commons.config.IsisConfiguration;
import org.apache.isis.core.metamodel.facetapi.FacetHolder;
import org.apache.isis.core.metamodel.facets.actions.action.command.CommandFacetFromConfiguration;
import org.apache.isis.core.metamodel.facets.actions.command.CommandFacet;
import org.apache.isis.core.metamodel.facets.actions.command.CommandFacetAbstract;

public class CommandFacetForPropertyAnnotation extends CommandFacetAbstract {

    public static CommandFacet create(
            final Property property,
            final IsisConfiguration configuration,
            final FacetHolder holder) {

        final CommandReification command = property != null ? property.command() : CommandReification.AS_CONFIGURED;
        final CommandPersistence commandPersistence = property != null ? property.commandPersistence() : CommandPersistence.PERSISTED;
        final CommandExecuteIn commandExecuteIn = property != null? property.commandExecuteIn() :  CommandExecuteIn.FOREGROUND;

        final Persistence persistence = CommandPersistence.from(commandPersistence);
        final ExecuteIn executeIn = CommandExecuteIn.from(commandExecuteIn);

        switch (command) {
            case AS_CONFIGURED:
                final CommandPropertiesConfiguration setting = CommandPropertiesConfiguration.parse(configuration);
                switch (setting) {
                case NONE:
                    return null;
                default:
                    return property != null
                            ? new CommandFacetForPropertyAnnotationAsConfigured(persistence, executeIn, Enablement.ENABLED, holder)
                            : CommandFacetFromConfiguration.create(holder);
                }
            case DISABLED:
                return null;
            case ENABLED:
                return new CommandFacetForPropertyAnnotation(persistence, executeIn, Enablement.ENABLED, holder);
        }

        return null;
    }


    CommandFacetForPropertyAnnotation(
            final Persistence persistence,
            final ExecuteIn executeIn,
            final Enablement enablement,
            final FacetHolder holder) {
        super(persistence, executeIn, enablement, holder);
    }


}
