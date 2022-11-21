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
package org.apache.isis.commons.collections;

import java.util.EnumSet;
import java.util.Iterator;

/**
 * Immutable variant of {@link EnumSet}
 * 
 * @since 2.0
 */
public final class ImmutableEnumSet<E extends Enum<E>> 
implements Iterable<E>, java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
    private final EnumSet<E> delegate;

    private ImmutableEnumSet(EnumSet<E> delegate) {
        this.delegate = delegate;
    }

    public static <E extends Enum<E>> ImmutableEnumSet<E> from(EnumSet<E> delegate) {
        return new ImmutableEnumSet<>(delegate);
    }
    
    public static <E extends Enum<E>> ImmutableEnumSet<E> noneOf(Class<E> enumType) {
        return from(EnumSet.noneOf(enumType));
    }

    public static <E extends Enum<E>> ImmutableEnumSet<E> of(E e1) {
        return from(EnumSet.of(e1));
    }
    
    public static <E extends Enum<E>> ImmutableEnumSet<E> of(E e1, E e2) {
        return from(EnumSet.of(e1, e2));
    }
    
    public static <E extends Enum<E>> ImmutableEnumSet<E> of(E e1, E e2, E e3) {
        return from(EnumSet.of(e1, e2, e3));
    }
    
    public static <E extends Enum<E>> ImmutableEnumSet<E> of(E e1, E e2, E e3, E e4) {
        return from(EnumSet.of(e1, e2, e3, e4));
    }

    public static <E extends Enum<E>> ImmutableEnumSet<E> complementOf(ImmutableEnumSet<E> other) {
        return from(EnumSet.complementOf(other.delegate));
    }
    
    public static <E extends Enum<E>> ImmutableEnumSet<E> allOf(Class<E> enumType) {
        return from(EnumSet.allOf(enumType));
    }
    
    public boolean contains(E element) {
        return delegate.contains(element);
    }
    
    public EnumSet<E> toEnumSet() {
        return delegate.clone();
    }
    
    @Override
    public Iterator<E> iterator() {
        return delegate.iterator();
    }




}
