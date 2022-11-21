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
package org.apache.isis.metamodel.services.swagger.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tagger {

    
    static Pattern tagSpringFramework = Pattern.compile("^org\\.springframework\\.([^\\.]+)\\.(.+)$");
    static Pattern tagPatternIsisExtensions = Pattern.compile("^org\\.apache\\.isis\\.extensions\\.([^\\.]+)\\.(.+)$");
    static Pattern tagPatternIsisAddons = Pattern.compile("^org\\.isisaddons\\.module\\.([^\\.]+)\\.(.+)$");
    static Pattern tagPatternIncodeCatalog = Pattern.compile("^org\\.incode\\.module\\.([^\\.]+)\\.(.+)$");
    static Pattern tagPatternForFqcn = Pattern.compile("^.*\\.([^\\.]+)\\.([^\\.]+)$");
    static Pattern tagPatternForTwoParts = Pattern.compile("^([^\\.]+)\\.([^\\.]+)$");
    static Pattern tagPatternForJaxbDto = Pattern.compile("^.*\\.([^\\.]+)\\.(v[0-9][^\\.]*)\\.([^\\.]+)$");

    String tagForObjectType(final String objType, final String fallback) {
        if (objType.startsWith("org.apache.isis.")) {
            return "> apache isis internals";
        }
        
        Matcher matcher;
        matcher = tagSpringFramework.matcher(objType);
        if (matcher.matches()) {
            return "> spring framework " + matcher.group(1);
        }
        matcher = tagPatternIsisExtensions.matcher(objType);
        if (matcher.matches()) {
            return "apache isis extensions " + matcher.group(1);
        }
        matcher = tagPatternIsisAddons.matcher(objType);
        if (matcher.matches()) {
            return "isisaddons.org " + matcher.group(1);
        }
        matcher = tagPatternIncodeCatalog.matcher(objType);
        if (matcher.matches()) {
            return "catalog.incode.org " + matcher.group(1);
        }
        matcher = tagPatternForJaxbDto.matcher(objType);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        matcher = tagPatternForFqcn.matcher(objType);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        matcher = tagPatternForTwoParts.matcher(objType);
        if (matcher.matches()) {
            if (objType.startsWith("isisApplib")) {
                return "> apache isis applib";
            }
            if (objType.startsWith("isissecurity")) {
                return "> apache isis extensions";
            }
            // special cases for other Isis addons, eg "isisxxxx"
            if (objType.startsWith("isis")) {
                return "isisaddons " + objType.substring(4, objType.indexOf("."));
            }
            
            return matcher.group(1);
        }
        
        return fallback != null? fallback: objType;
    }

}
