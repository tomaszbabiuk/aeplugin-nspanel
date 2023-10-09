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

package eu.automateeverything.tabletsplugin.blocks

import eu.automateeverything.data.blocks.RawJson
import eu.automateeverything.data.instances.InstanceDto
import eu.automateeverything.domain.automation.*
import eu.automateeverything.domain.automation.blocks.CommonBlockCategories
import eu.automateeverything.domain.configurable.NameDescriptionConfigurable.Companion.FIELD_NAME
import eu.automateeverything.domain.configurable.StateDeviceConfigurable
import eu.automateeverything.tabletsplugin.R

class ShowDialogBlockFactory(private val dialog: InstanceDto) : StatementBlockFactory {

    override val category = CommonBlockCategories.ThisObject

    override val type: String = "show_dialog_${dialog.id}"

    override fun buildBlock(): RawJson {
        return RawJson {
            """
                   { "type":  "$type",
                     "colour": ${category.color},
                     "tooltip": null,
                     "helpUrl": null,
                     "message0": "${R.block_show_dialog_message(dialog.fields[FIELD_NAME]!!).getValue(it )}",
                     "previousStatement": null,
                     "nextStatement": null }
                """
                .trimIndent()
        }
    }

    override fun transform(
        block: Block,
        next: StatementNode?,
        context: AutomationContext,
        transformer: BlocklyTransformer,
        order: Int
    ): StatementNode {
        //        if (context.thisDevice is StateDeviceConfigurable) {
        //            val evaluator = context.automationUnitsCache[context.instance.id]
        //            if (evaluator is StateDeviceAutomationUnitBase) {
        //                return ChangeStateAutomationNode(state.id, evaluator, next)
        //            } else {
        //                throw MalformedBlockException(block.type, "should point only to a state
        // device")
        //            }
        //        }

        throw MalformedBlockException(
            block.type,
            "it's impossible to connect this block with correct ${StateDeviceConfigurable::class.java}"
        )
    }
}
