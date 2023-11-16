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

import eu.automateeverything.data.Repository
import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.domain.automation.blocks.BlockFactoriesCollector
import eu.automateeverything.domain.automation.blocks.CollectionContext
import eu.automateeverything.domain.configurable.Configurable
import eu.automateeverything.tabletsplugin.CompositionConfigurable
import org.pf4j.Extension

@Suppress("unused")
@Extension
class TabletsBlocksCollector(private val repository: Repository) : BlockFactoriesCollector {

    override fun collect(
        thisDevice: Configurable,
        instanceId: Long?,
        context: CollectionContext
    ): List<BlockFactory<*, *>> {
        return collectUIBlocks(thisDevice) + collectNavigationFactories(instanceId)
    }

    private fun collectUIBlocks(thisDevice: Configurable): List<BlockFactory<*, *>> {
        if (thisDevice is CompositionConfigurable) {
            return listOf(
                HeadlineBlockFactory(),
                TextBlockFactory(),
                ButtonBlockFactory(),
                StartHereBlockFactory(),
                SingleColumnBlockFactory()
            )
        }

        return listOf()
    }

    private fun collectNavigationFactories(instanceId: Long?): List<BlockFactory<*, *>> {
        return repository
            .getAllInstances()
            .filter { it.clazz == CompositionConfigurable::class.java.name }
            .filter { it.id != instanceId }
            .map { NavigateBlockFactory(it) }
    }
}
