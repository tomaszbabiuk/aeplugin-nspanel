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
import eu.automateeverything.tabletsplugin.interop.UIBlock
import java.io.IOException
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
        PortCapabilities(false, false),
        0L
    ) {

    init {
        updateLastSeenTimeStamp(lastSeenTimestamp)
    }

    private var actionsClient: CoapClient? = null
    var activeDashboardId = 0L
        private set

    var selectedOptionId: Int? = null
        private set

    override fun read(): TabletConnectorPortValue {
        return TabletConnectorPortValue()
    }

    override fun readInternal(): TabletConnectorPortValue {
        throw IOException("This port cannot read")
    }

    fun start() {
        operationScope = CoroutineScope(Dispatchers.IO)

        actionsClient =
            aeClient.observeDashboard {
                activeDashboardId = it.dashboardId
                selectedOptionId = it.buttonId

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

    fun updateDashboard(dashboardId: Long, content: UIBlock) {
        aeClient.changeDashboard(dashboardId, content)
    }
}
