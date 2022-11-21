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
package org.apache.isis.security.shiro.authorization;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

import org.apache.isis.commons.internal.collections._Multimaps;
import org.apache.isis.commons.internal.collections._Multimaps.ListMultimap;

import lombok.val;

public class IsisPermission extends WildcardPermission {

    private static final long serialVersionUID = 1L;
    private static final Pattern PATTERN = Pattern.compile("([!]?)([^/]+)[/](.+)");

    private boolean isVetoed;
    private String permissionGroup;

    public IsisPermission() {
    }

    public IsisPermission(String wildcardString, boolean caseSensitive) {
        super(wildcardString, caseSensitive);
    }

    public IsisPermission(String wildcardString) {
        super(wildcardString);
    }

    @Override
    protected void setParts(String wildcardString, boolean caseSensitive) {
        Matcher matcher = PATTERN.matcher(wildcardString);
        if(matcher.matches()) {
            isVetoed = matcher.group(1).length() > 0;
            permissionGroup = matcher.group(2);
            super.setParts(matcher.group(3), caseSensitive);
        } else {
            super.setParts(wildcardString, caseSensitive);
        }
    }

    @Override
    public boolean implies(Permission p) {
        if(isVetoed) {
            addVeto(this);
            return false;
        } else {
            return !isVetoed(this.permissionGroup, p) && super.implies(p);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof IsisPermission) {
            IsisPermission ip = (IsisPermission) other;
            return permissionGroup.equals(ip.getPermissionGroup()) && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        // good enough
        return super.hashCode();
    }

    @Override
    public String toString() {
        return (isVetoed?"!":"") + (permissionGroup != null? permissionGroup + "/": "") + super.toString();
    }
    
    // -- HELPER
    
    private static final ThreadLocal<ListMultimap<String, IsisPermission>> VETOING_PERMISSIONS = 
            ThreadLocal.withInitial(_Multimaps::newListMultimap);

    public static void resetVetoedPermissions() {
        IsisPermission.VETOING_PERMISSIONS.remove();
    }

    private boolean isVetoed(String permissionGroup, Permission permission) {
        if(permissionGroup == null) {
            return false;
        }
        val vetoMultimap = IsisPermission.VETOING_PERMISSIONS.get();
        return vetoMultimap.getOrElseEmpty(permissionGroup)
            .stream()
            .anyMatch(vetoingPermission->vetoingPermission.impliesWithoutVeto(permission));
    }

    private void addVeto(IsisPermission vetoingPermission) {
        val permissionGroup = vetoingPermission.getPermissionGroup();
        val vetoMultimap = IsisPermission.VETOING_PERMISSIONS.get();
        vetoMultimap.putElement(permissionGroup, vetoingPermission);
    }
    
    private boolean impliesWithoutVeto(Permission p) {
        return super.implies(p);
    }

    private String getPermissionGroup() {
        return permissionGroup;
    }

}
