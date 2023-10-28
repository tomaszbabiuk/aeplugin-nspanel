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

import eu.automateeverything.data.blocks.RawJson
import eu.automateeverything.domain.automation.*
import eu.automateeverything.domain.automation.blocks.CommonBlockCategories
import eu.automateeverything.tabletsplugin.R

class ScreenCompositionBlockFactory : TriggerBlockFactory {

    override val category = CommonBlockCategories.ThisObject

    private val typePrefix = "tablet_screen_composition"
    override val type: String = typePrefix

    override fun buildBlock(): RawJson {

        return RawJson { language ->
            """
                {
                  "type": "$type",
                  "message0": "${R.block_screen_composition_message.getValue(language)}",
                  "args0": [
                    {
                      "type": "input_value",
                      "name": "PRESENTER",
                      "check": "tablet_presenter",
                      "align": "CENTRE"
                    },
                    {
                      "type": "input_statement",
                      "name": "BLOCKS",
                      "check": [
                        "tablet_headline",
                        "tablet_button",
                        "tablet_text",
                        "tablet_device_value",
                        "tablet_qrcode"
                      ]
                    }
                  ],
                  "previousStatement": [
                        "tablet_headline",
                        "tablet_button",
                        "tablet_text",
                        "tablet_device_value",
                        "tablet_qrcode"
                      ],
                  "nextStatement": [
                        "tablet_headline",
                        "tablet_button",
                        "tablet_text",
                        "tablet_device_value",
                        "tablet_qrcode"
                      ],
                  "colour": ${category.color},
                  "tooltip": "",
                  "helpUrl": ""
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
}
