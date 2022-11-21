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
package org.apache.isis.valuetypes.markdown.applib.jaxb;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.isis.commons.internal.base._Strings;
import org.apache.isis.valuetypes.markdown.applib.value.Markdown;

public final class MarkdownJaxbAdapter extends XmlAdapter<String, Markdown> {

    /**
     * Is threadsafe, see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.Encoder.html">JDK8 javadocs</a>
     */
    private final Base64.Encoder encoder = Base64.getEncoder();
    /**
     * Is threadsafe, see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.Decoder.html">JDK8 javadocs</a>
     */
    private final Base64.Decoder decoder = Base64.getDecoder(); // is thread-safe ?

    @Override
    public Markdown unmarshal(String v) throws Exception {
        if(v==null) {
            return null;
        }
        final String html = _Strings.ofBytes(decoder.decode(v), StandardCharsets.UTF_8);
        return new Markdown(html);
    }

    @Override
    public String marshal(Markdown v) throws Exception {
        if(v==null) {
            return null;
        }
        final String html = v.asHtml();
        return encoder.encodeToString(_Strings.toBytes(html, StandardCharsets.UTF_8));
    }
}
