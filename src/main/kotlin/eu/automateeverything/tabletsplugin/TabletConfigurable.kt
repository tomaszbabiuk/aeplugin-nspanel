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
import eu.automateeverything.data.automation.State
import eu.automateeverything.data.fields.InstanceReference
import eu.automateeverything.data.fields.InstanceReferenceType
import eu.automateeverything.data.fields.PortReference
import eu.automateeverything.data.fields.PortReferenceType
import eu.automateeverything.data.instances.InstanceDto
import eu.automateeverything.domain.automation.AutomationUnit
import eu.automateeverything.domain.automation.BlocklyParser
import eu.automateeverything.domain.automation.blocks.CollectionContext
import eu.automateeverything.domain.configurable.*
import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.PortFinder
import eu.automateeverything.tabletsplugin.blocks.TabletsBlocksCollector
import eu.automateeverything.tabletsplugin.blocks.TabletsTransformer
import eu.automateeverything.tabletsplugin.composition.UIContext
import org.pf4j.Extension

@Extension
class TabletConfigurable(
    private val portFinder: PortFinder,
    private val eventBus: EventBus,
    private val repository: Repository
) : StateDeviceConfigurable() {

    override val parent: Class<out Configurable>
        get() = TabletsConfigurable::class.java

    private val portField =
        PortReferenceField(
            FIELD_PORT,
            R.field_port_hint,
            PortReference(TabletConnectorPortValue::class.java, PortReferenceType.Any),
            RequiredStringValidator()
        )

    private val initialCompositionIdField =
        InstanceReferenceField(
            FIELD_INITIAL_COMPOSITION,
            R.field_initial_composition,
            InstanceReference(DashboardConfigurable::class.java, InstanceReferenceType.Single),
            RequiredStringValidator()
        )

    override fun buildAutomationUnit(instance: InstanceDto): AutomationUnit<State> {
        val initialCompositionId = extractFieldValue(instance, initialCompositionIdField)
        val initialCompositionInstance = repository.getInstance(initialCompositionId.toLong())
        val initialCompositionXml = BlocklyParser().parse(initialCompositionInstance.composition!!)
        val transformer = TabletsTransformer()

        val factoriesCache =
            TabletsBlocksCollector(repository)
                .collect(
                    DashboardConfigurable(repository),
                    initialCompositionInstance.id,
                    CollectionContext.Automation
                )
        val context = UIContext(factoriesCache)
        val x = transformer.transform(initialCompositionXml.blocks!!, context)
        println(x)

        val portId = extractFieldValue(instance, portField)
        val port = portFinder.searchForAnyPort(TabletConnectorPortValue::class.java, portId)
        val name = extractFieldValue(instance, nameField)

        return TabletAutomationUnit(eventBus, instance, name, states, port as TabletPort)
    }

    override val states: Map<String, State>
        get() {
            val states: MutableMap<String, State> = HashMap()
            states[STATE_UNKNOWN] =
                State.buildReadOnlyState(
                    STATE_UNKNOWN,
                    R.state_unknown,
                )
            states[STATE_ACTIVE] =
                State.buildReadOnlyState(
                    STATE_ACTIVE,
                    R.state_active,
                )
            states[STATE_INACTIVE] =
                State.buildReadOnlyState(
                    STATE_INACTIVE,
                    R.state_inactive,
                )
            return states
        }

    override val fieldDefinitions: Map<String, FieldDefinition<*>>
        get() {
            val result: MutableMap<String, FieldDefinition<*>> =
                LinkedHashMap(super.fieldDefinitions)
            result[FIELD_PORT] = portField
            result[FIELD_INITIAL_COMPOSITION] = initialCompositionIdField
            return result
        }

    override val addNewRes = R.configurable_tablet_add
    override val editRes = R.configurable_tablet_edit
    override val titleRes = R.configurable_tablet_title
    override val descriptionRes = R.configurable_tablet_description

    override val iconRaw: String
        get() =
            """
            <svg width="100" height="100" xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
             <title>most_search2BF</title>
             <g class="layer">
              <title>Layer 1</title>
              <g id="svg_1">
               <path d="m36.4,56.84999l2.94,0l0,2.87a3.36,3.28 0 0 0 6.72,0l0,-2.87l5.88,0l0,2.87a3.36,3.28 0 0 0 6.72,0l0,-2.87l2.94,0l0,-18.04l-25.2,0l0,18.04z" id="svg_2"/>
               <path d="m62.1166,22.22139a1.6506,1.6113 0 0 0 -0.84,-0.2214a1.68,1.64 0 0 0 -1.4532,0.82l-2.52,4.2763a12.6,12.3 0 0 0 -16.59,0l-2.5368,-4.2763a1.68,1.64 0 0 0 -1.4532,-0.82a1.6506,1.6113 0 0 0 -0.84,0.2214a1.68,1.64 0 0 0 -0.6174,2.2386l3.1206,5.2726a12.4782,12.1811 0 0 0 -1.9866,6.6174l25.2,0a12.4782,12.1811 0 0 0 -1.9866,-6.6174l3.1206,-5.2726a1.68,1.64 0 0 0 -0.6174,-2.2386zm-17.9466,10.8486a1.26,1.23 0 1 1 1.26,-1.23a1.26,1.23 0 0 1 -1.26,1.23zm9.66,0a1.26,1.23 0 1 1 1.26,-1.23a1.26,1.23 0 0 1 -1.26,1.23z" id="svg_3"/>
               <path d="m67.48,38.80999a2.52,2.46 0 0 0 -2.52,2.46l0,8.2a2.52,2.46 0 0 0 5.04,0l0,-8.2a2.52,2.46 0 0 0 -2.52,-2.46z" id="svg_4"/>
               <path d="m30.52,38.80999a2.52,2.46 0 0 0 -2.52,2.46l0,8.2a2.52,2.46 0 0 0 5.04,0l0,-8.2a2.52,2.46 0 0 0 -2.52,-2.46z" id="svg_5"/>
              </g>
              <g class="layer" id="svg_6">
               <title>Layer 1</title>
               <path d="m16.62,7.83c-6.14,0 -11.12,3.55 -11.12,7.91l0,68.52c0,4.36 4.99,7.91 11.12,7.91l66.75,0c6.14,0 11.12,-3.55 11.12,-7.91l0,-68.52c0,-4.36 -4.99,-7.91 -11.12,-7.91l-66.75,0zm70.46,7.91l0,68.52c0,1.45 -1.66,2.64 -3.71,2.64l-66.75,0c-2.05,0 -3.71,-1.18 -3.71,-2.64l0,-68.52c0,-1.45 1.66,-2.64 3.71,-2.64l66.75,0c2.05,0 3.71,1.19 3.71,2.64z" id="svg_1"/>
               <path d="m79.74,73.93l-21.62,0c-2.98,0 -5.41,2.51 -5.41,5.61s2.42,5.61 5.41,5.61l21.62,0c2.98,0 5.41,-2.51 5.41,-5.61s-2.42,-5.61 -5.41,-5.61z" id="svg_2"/>
               <path d="m42.48,74.24l-21.62,0c-2.98,0 -5.41,2.51 -5.41,5.61s2.42,5.61 5.41,5.61l21.62,0c2.98,0 5.41,-2.51 5.41,-5.61s-2.42,-5.61 -5.41,-5.61z" id="svg_3" transform="matrix(1 0 0 1 0 0)"/>
               <path d="m24.09,15.71c-4.74,0 -8.59,2.37 -8.59,5.3l0,45.89c0,2.92 3.85,5.3 8.59,5.3l51.52,0c4.74,0 8.59,-2.37 8.59,-5.3l0,-45.89c0,-2.92 -3.85,-5.3 -8.59,-5.3l-51.52,0zm54.39,5.3l0,45.89c0,0.97 -1.28,1.77 -2.86,1.77l-51.52,0c-1.58,0 -2.86,-0.79 -2.86,-1.77l0,-45.89c0,-0.97 1.28,-1.77 2.86,-1.77l51.52,0c1.58,0 2.86,0.8 2.86,1.77z" id="svg_4"/>
              </g>
             </g>
            </svg>
        """
                .trimIndent()

    companion object {
        const val FIELD_PORT = "portId"
        const val FIELD_INITIAL_COMPOSITION = "compositionId"
        const val STATE_ACTIVE = "active"
        const val STATE_INACTIVE = "inactive"
    }
}
