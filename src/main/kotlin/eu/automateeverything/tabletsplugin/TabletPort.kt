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

import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.Port
import eu.automateeverything.domain.hardware.PortCapabilities
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import java.util.*
import kotlinx.coroutines.*
import org.eclipse.californium.core.CoapClient

class TabletPort(
    factoryId: String,
    adapterId: String,
    portId: String,
    eventBus: EventBus,
    lastSeenTimestamp: Long,
    private val aeClient: AETabletClient,
    private var operationScope: CoroutineScope? = null
) :
    Port<TabletConnectorPortValue>(
        factoryId,
        adapterId,
        portId,
        eventBus,
        TabletConnectorPortValue::class.java,
        PortCapabilities(canRead = true, canWrite = false),
        2 * 60 * 1000
    ) {

    init {
        updateLastSeenTimeStamp(lastSeenTimestamp)
    }

    private var lastValue = TabletConnectorPortValue(TabletUIState(0, null))

    private var actionsClient: CoapClient? = null

    override fun readInternal(): TabletConnectorPortValue {
        return lastValue
    }

    fun start() {
        operationScope = CoroutineScope(Dispatchers.IO)

        actionsClient =
            aeClient.observeDashboard {
                lastValue = TabletConnectorPortValue(TabletUIState(it.id, it.buttonRef))
                notifyValueUpdate()
                updateLastSeenTimeStamp(Calendar.getInstance().timeInMillis)
            }

        operationScope?.launch {
            while (isActive) {
                delay(500)
                try {
                    aeClient.releasePutQueue()
                } catch (ex: Exception) {
                    println(ex)
                }
            }
        }
    }

    fun stop() {
        actionsClient?.shutdown()
        operationScope?.cancel()
    }

    fun updateDashboard(title: String, dashboardId: Long, content: DashboardItem) {
        aeClient.changeDashboard(title, dashboardId, content)
    }
}
