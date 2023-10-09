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
import eu.automateeverything.domain.configurable.Configurable
import eu.automateeverything.tabletsplugin.DialogConfigurable
import org.pf4j.Extension

@Suppress("unused")
@Extension
class TabletsBlocksCollector(private val repository: Repository) : BlockFactoriesCollector {

    override fun collect(thisDevice: Configurable?): List<BlockFactory<*>> {
        return collectShowDialogFactories() + listOf(DialogOptionsBlockFactory())
    }

    private fun collectShowDialogFactories(): List<ShowDialogBlockFactory> {
        return repository
            .getAllInstances()
            .filter { it.clazz == DialogConfigurable::class.java.name }
            .map { ShowDialogBlockFactory(it) }
    }
}
