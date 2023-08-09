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

package eu.automateeverything.nspanelplugin.blocks

import eu.automateeverything.data.blocks.RawJson
import eu.automateeverything.domain.automation.*
import eu.automateeverything.nspanelplugin.NsPanelAutomationUnit
import eu.automateeverything.nspanelplugin.NsPanelConfigurable
import eu.automateeverything.nspanelplugin.R

class NsPanelOptionBlockFactory : StatementBlockFactory {

    companion object {
        const val TYPE = "nspanel_option"
    }
    override val category = NsPanelBlockCategories.NSPanel

    override val type: String = TYPE

    override fun buildBlock(): RawJson {
        return RawJson {
            """
                {
                  "type": "$type",
                  "message0": "${R.block_nspanel_option_message.getValue(it)}",
                  "args0": [
                    {
                      "type": "input_dummy"
                    },
                    {
                      "type": "field_input",
                      "name": "LABEL",
                      "text": ""
                    },
                    {
                      "type": "input_statement",
                      "name": "INSIDE"
                    }
                  ],
                  "previousStatement": "$type",
                  "nextStatement": "$type",
                  "colour": ${category.color},
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
        if (context.thisDevice is NsPanelConfigurable) {
            val evaluator = context.automationUnitsCache[context.instance.id] as NsPanelAutomationUnit

            var insideNode: StatementNode? = null

            if (block.statements != null) {
                val insideStatement = block.statements!!.find { it.name == "INSIDE" }
                if (insideStatement?.block != null) {
                    insideNode = transformer.transformStatement(insideStatement.block!!, context)
                }
            }

            return NsPanelOptionAutomationNode(next, insideNode, order, evaluator)
        }

        throw MalformedBlockException(block.type, "it's impossible to connect this block with correct ${NsPanelConfigurable::class.java}")
    }
}