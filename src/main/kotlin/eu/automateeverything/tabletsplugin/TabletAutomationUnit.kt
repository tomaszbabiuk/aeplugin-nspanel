package eu.automateeverything.tabletsplugin

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

import eu.automateeverything.data.automation.State
import eu.automateeverything.data.configurables.ControlType
import eu.automateeverything.data.instances.InstanceDto
import eu.automateeverything.domain.automation.StateDeviceAutomationUnitBase
import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import java.util.*

class TabletAutomationUnit(
    eventBus: EventBus,
    instance: InstanceDto,
    name: String,
    initialCompositionTitle: String,
    initialCompositionId: Long,
    initialCompositionContent: DashboardItem?,
    states: Map<String, State>,
    private val port: TabletPort,
) : StateDeviceAutomationUnitBase(eventBus, instance, name, ControlType.States, states, false) {
    override val usedPortsIds: Array<String>
        get() = arrayOf(port.portId)

    init {
        if (initialCompositionContent != null) {
            port.updateDashboard(
                initialCompositionTitle,
                initialCompositionId,
                initialCompositionContent
            )
        }
    }

    override val recalculateOnTimeChange: Boolean
        get() = false

    override val recalculateOnPortUpdate: Boolean
        get() = false

    override fun calculateInternal(now: Calendar) {}

    override fun applyNewState(state: String) {}
}
