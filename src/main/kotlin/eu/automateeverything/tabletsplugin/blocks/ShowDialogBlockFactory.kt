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
import eu.automateeverything.domain.automation.*
import eu.automateeverything.tabletsplugin.TabletAutomationUnit
import eu.automateeverything.tabletsplugin.TabletConfigurable
import eu.automateeverything.tabletsplugin.R
import java.util.LinkedHashMap

class ShowDialogBlockFactory : StatementBlockFactory {

    override val category = TabletsBlockCategories.Tablets

    override val type: String = "tablet_show_dialog"

    override fun buildBlock(): RawJson {
        return RawJson { language ->
            """
                {
                  "type": "$type",
                  "message0": "${R.block_tablets_show_dialog_message.getValue(language)}",
                  "args0": [
                    {
                      "type": "input_dummy",
                      "align": "CENTRE"
                    },
                    {
                      "type": "field_input",
                      "name": "HEADLINE",
                      "text": "Hello World!"
                    },
                    {
                      "type": "input_dummy"
                    },
                    {
                      "type": "input_statement",
                      "name": "OPTIONS",
                      "check": "${OptionBlockFactory.TYPE}"
                    }
                  ],
                  "previousStatement": null,
                  "colour": ${category.color},
                  "tooltip": "",
                  "helpUrl": ""
                }
            """.trimIndent()
        }
    }

    override fun transform(
        block: Block,
        next: StatementNode?,
        context: AutomationContext,
        transformer: BlocklyTransformer,
        order: Int
    ): StatementNode {
        if (context.thisDevice is TabletConfigurable) {
            val evaluator = context.automationUnitsCache[context.instance.id] as TabletAutomationUnit

            var optionNodes = LinkedHashMap<String, StatementNode>()
            var headlineField = block.fields?.firstOrNull { it.name == "HEADLINE"}
                ?: throw MalformedBlockException(OptionBlockFactory.TYPE, "There should be a HEADLINE field inside.")
            val headline = headlineField.value!!

            if (block.statements != null) {
                val iOptionStatement = block.statements!!.find { it.name == "OPTIONS" }
                var iBlock = iOptionStatement?.block
                if (iBlock != null) {
                    var order = 0
                    while (iBlock != null) {
                        val labelField = iBlock.fields?.firstOrNull { it.name == "LABEL" }
                            ?: throw MalformedBlockException(OptionBlockFactory.TYPE, "There should be a LABEL field inside.")
                        val label = labelField.value!!
                        val optionNode = transformer.transformStatement(iBlock, context, order)
                        optionNodes[label] = optionNode
                        iBlock = iBlock.next?.block
                        order++
                    }
                }
            }

            //TODO: Create a random screen id here
            return ShowDialogAutomationNode(next, "screenId", headline, evaluator, optionNodes)
        }

        throw MalformedBlockException(
            block.type,
            "it's impossible to connect this block with correct ${TabletConfigurable::class.java}"
        )
    }
}