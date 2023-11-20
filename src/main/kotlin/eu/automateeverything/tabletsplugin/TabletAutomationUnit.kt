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

import eu.automateeverything.data.Repository
import eu.automateeverything.data.automation.State
import eu.automateeverything.data.configurables.ControlType
import eu.automateeverything.data.instances.InstanceDto
import eu.automateeverything.domain.automation.BlocklyParser
import eu.automateeverything.domain.automation.PortNotFoundException
import eu.automateeverything.domain.automation.StateDeviceAutomationUnitBase
import eu.automateeverything.domain.automation.blocks.CollectionContext
import eu.automateeverything.domain.configurable.NameDescriptionConfigurable
import eu.automateeverything.domain.configurable.StateDeviceConfigurable.Companion.STATE_ERROR
import eu.automateeverything.domain.configurable.StateDeviceConfigurable.Companion.STATE_OPERATIONAL
import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.PortFinder
import eu.automateeverything.tabletsplugin.blocks.TabletsBlocksCollector
import eu.automateeverything.tabletsplugin.blocks.TabletsTransformer
import eu.automateeverything.tabletsplugin.blocks.UIContext
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import java.util.*

class TabletAutomationUnit(
    eventBus: EventBus,
    instance: InstanceDto,
    name: String,
    private val portId: String,
    initialCompositionId: Long,
    states: Map<String, State>,
    private val portFinder: PortFinder,
    private val repository: Repository,
) : StateDeviceAutomationUnitBase(eventBus, instance, name, ControlType.States, states, false) {
    override val usedPortsIds: Array<String>
        get() = arrayOf(portId)

    private var uiContext: UIContext
    private var cachedPort: TabletPort? = null

    init {
        val now = Calendar.getInstance().timeInMillis
        val (context, dashboardTitle, dashboard) = changeDashboard(initialCompositionId)
        uiContext = context
        val port = resolvePort()
        if (port != null && port.assumeConnected(now)) {
            changeState(STATE_OPERATIONAL)
            port.updateDashboard(dashboardTitle, initialCompositionId, dashboard)
        } else {
            changeState(STATE_ERROR)
        }
    }

    private fun resolvePort(): TabletPort? {
        if (cachedPort != null) {
            return cachedPort
        }

        return try {
            val port =
                portFinder.searchForAnyPort(TabletConnectorPortValue::class.java, portId)
                    as TabletPort
            cachedPort = port
            port
        } catch (ex: PortNotFoundException) {
            cachedPort = null
            null
        }
    }

    private fun changeDashboard(dashboardId: Long): Triple<UIContext, String, DashboardItem> {
        val factoriesCache =
            TabletsBlocksCollector(repository)
                .collect(
                    DashboardConfigurable(repository),
                    dashboardId,
                    CollectionContext.Automation
                )
        uiContext = UIContext(factoriesCache)
        val (dashboardTitle, dashboardItem) = resolveComposition(dashboardId, uiContext)

        return Triple(uiContext, dashboardTitle, dashboardItem!!)
    }

    override val recalculateOnTimeChange: Boolean
        get() = false

    override val recalculateOnPortUpdate: Boolean
        get() = true

    override fun calculateInternal(now: Calendar) {
        val port = resolvePort()
        if (port != null && port.assumeConnected(now.timeInMillis)) {
            changeState(STATE_OPERATIONAL)
            val newState = port.read()
            val buttonRef = newState.value.lastPressedButtonRef
            if (buttonRef != null) {
                val newDashboardId = uiContext.resolveRoute(buttonRef)
                if (newDashboardId != null) {
                    changeDashboard(newDashboardId)
                }
            }
        } else {
            changeState(STATE_ERROR)
        }
    }

    override fun applyNewState(state: String) {}

    private fun resolveComposition(
        initialCompositionId: Long,
        uiContext: UIContext
    ): Pair<String, DashboardItem?> {
        val initialCompositionInstance = repository.getInstance(initialCompositionId)
        val initialCompositionTitle =
            initialCompositionInstance.fields[NameDescriptionConfigurable.FIELD_NAME]!!
        val initialCompositionXml = BlocklyParser().parse(initialCompositionInstance.composition!!)
        val transformer = TabletsTransformer()

        return Pair(
            initialCompositionTitle,
            transformer.transform(initialCompositionXml.blocks!!, uiContext)?.item
        )
    }
}
