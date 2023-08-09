/*
 * Copyright (c) 2019-2022 Tomasz Babiuk
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

package eu.automateeverything.nspanelplugin

import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.DiscoveryMode
import eu.automateeverything.domain.hardware.HardwareAdapterBase
import eu.automateeverything.domain.hardware.Port
import eu.automateeverything.domain.hardware.PortIdBuilder
import kotlinx.coroutines.*

class NsPanelAdapter(
    owningPluginId: String,
    eventBus: EventBus) : HardwareAdapterBase<Port<*>>(owningPluginId, "0", eventBus) {
    var operationScope: CoroutineScope? = null
    private val idBuilder = PortIdBuilder(owningPluginId)


    override suspend fun internalDiscovery(mode: DiscoveryMode) = coroutineScope {

    }

    override fun executePendingChanges() {
    }

    override fun stop() {
    }

    override fun start() {
    }
}

