/*
 * Copyright (c) 2019-2023 Tomasz Babiuk
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.automateeverything.tabletsplugin

import eu.automateeverything.data.Repository
import eu.automateeverything.data.localization.Resource
import eu.automateeverything.data.plugins.PluginCategory
import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.extensibility.PluginMetadata
import eu.automateeverything.domain.hardware.HardwareAdapter
import eu.automateeverything.domain.hardware.HardwareManager
import eu.automateeverything.domain.hardware.HardwarePlugin
import eu.automateeverything.domain.hardware.PortFinder
import eu.automateeverything.domain.langateway.LanGatewayResolver
import org.pf4j.PluginWrapper

@Suppress("unused")
class TabletsPlugin(
    wrapper: PluginWrapper,
    private val lanGatewayResolver: LanGatewayResolver,
    private val portFinder: PortFinder,
    private val eventBus: EventBus,
    private val repository: Repository
) : HardwarePlugin(wrapper), PluginMetadata {
    override fun createAdapters(): List<HardwareAdapter<*>> {
        val adapter = TabletAdapter(pluginId, lanGatewayResolver, portFinder, repository, eventBus)
        return listOf(adapter)
    }

    override fun start() {
    }

    override fun stop() {
    }

    override val name: Resource = R.plugin_name
    override val description: Resource = R.plugin_description
    override val category: PluginCategory = PluginCategory.Objects
}