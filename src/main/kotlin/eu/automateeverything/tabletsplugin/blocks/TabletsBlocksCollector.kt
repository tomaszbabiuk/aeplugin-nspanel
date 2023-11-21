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

package eu.automateeverything.tabletsplugin.blocks

import eu.automateeverything.data.DataRepository
import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.domain.automation.blocks.BlockFactoriesCollector
import eu.automateeverything.domain.automation.blocks.CollectionContext
import eu.automateeverything.domain.configurable.Configurable
import eu.automateeverything.domain.configurable.DeviceConfigurable
import eu.automateeverything.domain.extensibility.ConfigurableRepository
import eu.automateeverything.tabletsplugin.DashboardConfigurable
import eu.automateeverything.tabletsplugin.TabletConfigurable
import org.pf4j.Extension

@Suppress("unused")
@Extension
class TabletsBlocksCollector(
    private val dataRepository: DataRepository,
    private val configurableRepository: ConfigurableRepository
) : BlockFactoriesCollector {

    override fun collect(
        thisDevice: Configurable,
        instanceId: Long?,
        context: CollectionContext
    ): List<BlockFactory<*, *, *>> {
        return collectUIBlocks(thisDevice) +
            collectNavigationFactories(instanceId) +
            collectDeviceFactories()
    }

    private fun collectUIBlocks(thisDevice: Configurable): List<BlockFactory<*, *, *>> {
        if (thisDevice is DashboardConfigurable) {
            return listOf(
                HeadlineBlockFactory(),
                TextBlockFactory(),
                ButtonBlockFactory(),
                StartHereBlockFactory(),
                SingleColumnBlockFactory(),
                QuarterControlBlockFactory()
            )
        }

        return listOf()
    }

    private fun collectNavigationFactories(instanceId: Long?): List<BlockFactory<*, *, *>> {
        return dataRepository
            .getAllInstances()
            .filter { it.clazz == DashboardConfigurable::class.java.name }
            .filter { it.id != instanceId }
            .map { NavigateBlockFactory(it) }
    }

    private fun collectDeviceFactories(): List<BlockFactory<*, *, *>> {
        val allConfigurables = configurableRepository.getAllConfigurables()
        val deviceConfigurables =
            allConfigurables
                .filter { DeviceConfigurable::class.java.isAssignableFrom(it::class.java) }
                .filter { it::class.java != TabletConfigurable::class.java }

        return deviceConfigurables
            .flatMap { dataRepository.getInstancesOfClazz(it::class.java.name) }
            .map { DeviceBlockFactory(it) }
    }
}
