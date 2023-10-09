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
import eu.automateeverything.tabletsplugin.R

open class DialogOptionsBlockFactory : EvaluatorBlockFactory {

    enum class OptionId(val label: String, val seconds: Int) {
        S00("1", 0),
        S01("2", 1),
        S02("3", 2),
        S03("4", 3),
        S04("5", 4),
        S05("6", 5),
        S06("7", 6),
    }

    override val category = TabletsBlockCategories.Tablets
    override val type = "tablet_option_value"

    override fun buildBlock(): RawJson {
        return RawJson { language ->
            """
                {
                  "type": "$type",
                  "message0": "${R.block_dialog_options_message.getValue(language)}",
                  "args0": [
                    {
                      "type": "input_dummy",
                      "align": "CENTRE"
                    },
                    {
                       "type": "field_dropdown",
                       "name": "OPTION",
                       "options": ${ OptionId.values().joinToString(prefix = "[", postfix = "]") { "[\"${it.label}\", \"${it.seconds}\"]" }}
                     }
                  ],
                  "inputsInline": true,
                  "output": "dialog_option",
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
    ): EvaluatorNode {

        throw NotImplementedError()
    }
}
