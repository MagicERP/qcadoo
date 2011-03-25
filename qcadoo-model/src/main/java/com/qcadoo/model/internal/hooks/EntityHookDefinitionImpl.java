/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.0
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */

package com.qcadoo.model.internal.hooks;

import org.springframework.context.ApplicationContext;

import com.qcadoo.model.api.DataDefinition;
import com.qcadoo.model.api.Entity;
import com.qcadoo.model.internal.api.EntityHookDefinition;

public final class EntityHookDefinitionImpl extends HookDefinitionImpl implements EntityHookDefinition {

    private boolean enabled = true;

    public EntityHookDefinitionImpl(final String className, final String methodName, final ApplicationContext applicationContext) {
        super(className, methodName, applicationContext);
    }

    @Override
    public String getName() {
        return getBean().getClass().getCanonicalName() + "." + getMethod().getName();
    }

    @Override
    public boolean call(final Entity entity) {
        return call(getDataDefinition(), entity);
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return new Class[] { DataDefinition.class, Entity.class };
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void enable() {
        enabled = true;
    }

    @Override
    public void disable() {
        enabled = false;
    }

}