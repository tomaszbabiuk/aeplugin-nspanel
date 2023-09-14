package eu.automateeverything.nspanelplugin

import eu.automateeverything.data.automation.State
import eu.automateeverything.data.configurables.ControlType
import eu.automateeverything.data.instances.InstanceDto
import eu.automateeverything.domain.automation.StateDeviceAutomationUnitBase
import eu.automateeverything.domain.events.EventBus
import java.util.*

class NsPanelAutomationUnit(
    eventBus: EventBus,
    instance: InstanceDto,
    name: String,
    states: Map<String, State>,
) : StateDeviceAutomationUnitBase(eventBus, instance, name, ControlType.States, states, false) {

    var activeScreenId: String? = null

    val selectedOptionId: Int? = null
    override val usedPortsIds: Array<String>
        get() = arrayOf(/*port.id*/)
    override val recalculateOnTimeChange: Boolean
        get() = false
    override val recalculateOnPortUpdate: Boolean
        get() = false

    override fun calculateInternal(now: Calendar) {
    }

    override fun applyNewState(state: String) {
    }

    fun changeScreen(screenId: String, options: Array<String>) {
        activeScreenId = screenId
        //TODO
        //port.sendCommand(ChangeScreenCommand())
    }
}