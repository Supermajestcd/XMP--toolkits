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
package org.apache.isis.tooling.metaprog.demoshowcases.value;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.springframework.util.ClassUtils;

import org.apache.isis.commons.collections.Can;
import org.apache.isis.commons.internal.base._Files;
import org.apache.isis.commons.internal.base._Refs;
import org.apache.isis.commons.internal.base._Strings;
import org.apache.isis.commons.internal.base._Text;
import org.apache.isis.commons.internal.exceptions._Exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;
import lombok.val;

@RequiredArgsConstructor
public class ValueTypeGenTemplate {

    @Value @Builder
    public static class Config {
        final File outputRootDir;
        final String showcaseName;
        final String showcaseValueType;
        final String showcaseValueSemantics;
        @Builder.Default
        final String jdoTypeSupportNotice =
            "JDO supports `#{showcase-type}` out-of-the-box, so no special annotations are required.";
        @Builder.Default
        final String jpaTypeSupportNotice =
            "JPA supports `#{showcase-type}` out-of-the-box, so no special annotations are required.";
        @Builder.Default
        final String jaxbTypeSupportNotice =
            "JAXB supports `#{showcase-type}` out-of-the-box, so no special annotations are required.";
        final String javaPackage;
        @Builder.Default
        final String fileNamePlaceholderForShowcaseName = "$Template";
        @Builder.Default
        final String generatedFileNotice = "This file was GENERATED by the showcase generator (tooling). Do NOT edit!";
        @Singular
        final Map<String, String> templateVariables = new HashMap<>();
        @Builder.Default
        final Can<Template> templates = Template.REGULAR_SET;
        @Builder.Default
        final TemplateVariant templateVariant = TemplateVariant.DEFAULT;
    }

    @RequiredArgsConstructor
    enum Generator {
        DOC(".adoc"){
            @Override String formatAsComment(final String text) {
                return " // " + text; }
            @Override String formatAsTemplateVar(final String key) {
                return "#{" + key + "}"; } // ADOC pass through var syntax
        },
        JAVA(".java"){
            @Override String formatAsComment(final String text) {
                return "/* " + text + " */"; }
            @Override String formatAsTemplateVar(final String key) {
                return "/*${" + key + "}*/"; }
        },
        LAYOUT(".layout.xml"){
            @Override String formatAsComment(final String text) {
                return "<!-- " + text + " -->"; }
            @Override String formatAsTemplateVar(final String key) {
                return "<!--${" + key + "}-->"; }
        };
        boolean isDoc() { return this == DOC; }
        boolean isJava() { return this == JAVA; }
        boolean isLayout() { return this == LAYOUT; }
        final String fileSuffix;
        abstract String formatAsComment(String text);
        abstract String formatAsTemplateVar(String key);
    }

    @RequiredArgsConstructor
    enum TemplateVariant {
        DEFAULT(""),
        PRIMITIVE("~primitive"),
        ;
        @Getter private final String suffix;
    }

    @RequiredArgsConstructor
    enum Template {
        HOLDER("holder/%sHolder", Generator.JAVA),
        HOLDER2("holder/%sHolder2", Generator.JAVA),
        HOLDER_ACTION_RETURNING("holder/%sHolder_actionReturning", Generator.JAVA),
        HOLDER_ACTION_RETURNING_ARRAY("holder/%sHolder_actionReturningArray", Generator.JAVA),
        HOLDER_ACTION_RETURNING_COLLECTION("holder/%sHolder_actionReturningCollection", Generator.JAVA),
        HOLDER_MIXIN_PROPERTY("holder/%sHolder_mixinProperty", Generator.JAVA),
        HOLDER_UPDATE_RO_PROPERTY("holder/%sHolder_updateReadOnlyProperty", Generator.JAVA),
        HOLDER_UPDATE_ROO_PROPERTY("holder/%sHolder_updateReadOnlyOptionalProperty", Generator.JAVA),
        HOLDER_UPDATE_RO_PROPERTY_WITH_CHOICES("holder/%sHolder_updateReadOnlyPropertyWithChoices", Generator.JAVA),
        HOLDER_UPDATE_ROO_PROPERTY_WITH_CHOICES("holder/%sHolder_updateReadOnlyOptionalPropertyWithChoices", Generator.JAVA),
        COLLECTION("%ss", Generator.JAVA),
        JDO("jdo/%sJdo", Generator.JAVA),
        JDO_ENTITIES("jdo/%sJdoEntities", Generator.JAVA),
        JPA("jpa/%sJpa", Generator.JAVA),
        JPA_ENTITIES("jpa/%sJpaEntities", Generator.JAVA),
        ENTITY("persistence/%sEntity", Generator.JAVA),
        SEEDING("persistence/%sSeeding", Generator.JAVA),
        SAMPLES("samples/%sSamples", Generator.JAVA),
        VIEWMODEL("vm/%sVm", Generator.JAVA),

        COMMON_DOC("%ss-common", Generator.DOC),
        DESCRIPTION("%ss-description", Generator.DOC),
        JDO_DESCRIPTION("jdo/%sJdo-description", Generator.DOC),
        JPA_DESCRIPTION("jpa/%sJpa-description", Generator.DOC),
        VIEWMODEL_DESCRIPTION("vm/%sVm-description", Generator.DOC),

        COLLECTION_LAYOUT("%ss", Generator.LAYOUT),
        ENTITY_LAYOUT("persistence/%sEntity", Generator.LAYOUT),
        VIEWMODEL_LAYOUT("vm/%sVm", Generator.LAYOUT)
        ;

        public static Can<Template> REGULAR_SET = Can.ofArray(Template.values())
            .remove(HOLDER_ACTION_RETURNING_ARRAY);

        public static Can<Template> PRIMITIVE_SET = Can.ofArray(Template.values())
                .remove(HOLDER_ACTION_RETURNING_COLLECTION)
                .remove(HOLDER_UPDATE_ROO_PROPERTY)
                .remove(HOLDER_UPDATE_ROO_PROPERTY_WITH_CHOICES)
                .remove(Template.SAMPLES);

        private final String pathTemplate;
        private final Generator generator;
        private final File outputFile(final Config config) {
            return new File(config.getOutputRootDir(),
                    String.format(pathTemplate, config.getShowcaseName())
                    + generator.fileSuffix)
                    .getAbsoluteFile();
        }
        private final File templateFile(final Config config) {
            return _Files.existingFile(templateFile(config, config.templateVariant)) // existence is optional
                    .orElseGet(()->{
                        // existence is mandatory
                        val defaultTemplateFile = templateFile(config, TemplateVariant.DEFAULT);
                        return _Files.existingFile(defaultTemplateFile)
                                .orElseThrow(()->_Exceptions.noSuchElement("template %s not found", defaultTemplateFile));
                    });
        }
        private final File templateFile(final Config config, final TemplateVariant templateVariant) {
            val templateFile = new File("src/main/resources",
                    String.format(pathTemplate, config.fileNamePlaceholderForShowcaseName)
                    + templateVariant.suffix
                    + generator.fileSuffix)
                    .getAbsoluteFile();
            return templateFile;
        }
        private final String javaPackage(final Config config) {
            return Optional.ofNullable(new File(String.format(pathTemplate, "X")).getParent())
                    .map(path->path.replace('/', '.'))
                    .map(suffix->config.javaPackage + "." + suffix)
                    .orElse(config.javaPackage);
        }
    }

    @RequiredArgsConstructor
    static class TemplateVars extends HashMap<String, String> {
        private static final long serialVersionUID = 1L;
        private final Generator generator;
        @Override
        public String put(final String key, final String value) {
            return super.put(generator.formatAsTemplateVar(key), value);
        }
        @Override
        public void putAll(final Map<? extends String, ? extends String> other) {
            other.forEach((key, value)->put(key, value));
        }
        public void putRaw(final String key, final String value) {
            super.put(key, value);
        }
    }

    final Config config;

    public void generate(final Consumer<File> onSourceGenerated) {

        for(var template: config.getTemplates()) {

            val templateFile = template.templateFile(config);

            val genTarget = template.outputFile(config);

            val templateVars = new TemplateVars(template.generator);
            templateVars.putAll(config.templateVariables);
            templateVars.put("java-package", template.javaPackage(config));
            templateVars.put("showcase-name", config.showcaseName);
            templateVars.put("showcase-type", config.showcaseValueType);
            templateVars.put("showcase-type-boxed",
                    Optional.ofNullable(ClassUtils.resolvePrimitiveClassName(config.showcaseValueType))
                    .map(ClassUtils::resolvePrimitiveIfNecessary)
                    .map(Class::getName)
                    .orElse(config.showcaseValueType));

            templateVars.put("showcase-type-getter-prefix",
                    Optional.ofNullable(ClassUtils.resolvePrimitiveClassName(config.showcaseValueType))
                    .map(cls->boolean.class.equals(cls) ? "is" : "get")
                    .orElse("get"));

            templateVars.put("showcase-java-package", config.javaPackage);
            templateVars.put("showcase-value-semantics-provider", config.showcaseValueSemantics);
            templateVars.put("generated-file-notice", template.generator.formatAsComment(config.generatedFileNotice));

            templateVars.put("jdo-type-support-notice", config.jdoTypeSupportNotice);
            templateVars.put("jpa-type-support-notice", config.jpaTypeSupportNotice);
            templateVars.put("jaxb-type-support-notice", config.jaxbTypeSupportNotice);


            // allow for ADOC IDE tools, to properly resolve include statements,
            // that is referenced (template) files should exist
            if(template.generator.isDoc()) {
                templateVars.putRaw("$Template", config.showcaseName);
                // purge any TemplateVariant occurrences in ADOC templates
                Stream.of(TemplateVariant.values())
                .map(TemplateVariant::getSuffix)
                .filter(_Strings::isNotEmpty)
                .forEach(variantSuffix->templateVars.putRaw(variantSuffix, ""));
            }

            generateFromTemplate(templateVars, templateFile, genTarget);
            onSourceGenerated.accept(genTarget);
        }

    }

    private void generateFromTemplate(
            final Map<String, String> templateVars, final File template, final File genTarget) {
        val templateLines = _Text.readLinesFromFile(template, StandardCharsets.UTF_8);

        _Files.makeDir(genTarget.getParentFile());

        _Text.writeLinesToFile(templateLines
                .map(line->templateProcessor(templateVars, line)),
                genTarget, StandardCharsets.UTF_8);
    }

    private String templateProcessor(final Map<String, String> templateVars, final String line) {
        val lineRef = _Refs.stringRef(line);
        templateVars.forEach((key, value)->{
            lineRef.update(s->s.replace(key, value));
        });
        return lineRef.getValue();
    }

}
