/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 1.2.0
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
package com.qcadoo.plugin.internal.api;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.qcadoo.plugin.api.Plugin;
import com.qcadoo.plugin.api.PluginDependencyResult;
import com.qcadoo.plugin.internal.dependencymanager.PluginStatusResolver;

public interface PluginDependencyManager {

    PluginDependencyResult getDependenciesToEnable(List<Plugin> plugins, PluginStatusResolver pluginStatusResolver);

    PluginDependencyResult getDependenciesToDisable(List<Plugin> plugins, PluginStatusResolver pluginStatusResolver);

    List<Plugin> sortPluginsInDependencyOrder(Collection<Plugin> plugins);

    List<Plugin> sortPluginsInDependencyOrder(final Collection<Plugin> plugins, final Map<String, Plugin> allPlugins);

    PluginDependencyResult getDependenciesToUninstall(List<Plugin> plugins, PluginStatusResolver pluginStatusResolver);

    PluginDependencyResult getDependenciesToUpdate(Plugin existingPlugin, Plugin newPlugin,
            PluginStatusResolver pluginStatusResolver);

}
