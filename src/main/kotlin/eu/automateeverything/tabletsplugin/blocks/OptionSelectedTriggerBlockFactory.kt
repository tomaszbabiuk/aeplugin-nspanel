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
import eu.automateeverything.tabletsplugin.R

class OptionSelectedTriggerBlockFactory(
    private val dialog: InstanceDto,
) : TriggerBlockFactory {

    override val category = CommonBlockCategories.Triggers

    private val typePrefix = "trigger_dialog_option_selected_"
    override val type: String = "$typePrefix${dialog.id}"

    override fun buildBlock(): RawJson {

        return RawJson { language ->
            """
               {
               "type": "$type",
               "message0": "${R.block_dialog_option_selected(dialog.fields[FIELD_NAME]!!).getValue(language)}",
               "args0": [
                {
                  "type": "input_dummy",
                  "align": "CENTRE"
                },
                 {
                   "type": "field_dropdown",
                   "name": "OPTION_ID",
                   "options": [
                     ["1","0"],
                     ["2","1"],
                     ["3","2"],
                     ["4","3"],
                     ["5","4"],
                     ["6","5"],
                     ["7","6"]
                   ]
                 }
               ],
               "nextStatement": "${Boolean::class.java.simpleName}",
               "colour": ${category.color},
               "tooltip": null,
               "helpUrl": null
            }
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
        val dialogInstanceIdRaw = block.type.replace(typePrefix, "")
        val dialogInstanceId = dialogInstanceIdRaw.toLong()

        if (block.fields == null) {
            throw MalformedBlockException(block.type, "should have <field> defined")
        }

        val optionField =
            block.fields!!.find { it.name == "OPTION_ID" }
                ?: throw MalformedBlockException(
                    block.type,
                    "should have <field name=\"OPTION_ID\"> defined"
                )

        val optionId = optionField.value!!.toInt()

        return DialogOptionAutomationNode(context, dialogInstanceId.toString(), optionId, next)
    }

    override fun dependsOn(): List<Long> {
        return listOf(dialog.id)
    }
}
