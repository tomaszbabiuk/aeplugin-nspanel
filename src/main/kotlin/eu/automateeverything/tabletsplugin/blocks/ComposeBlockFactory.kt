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
import eu.automateeverything.domain.automation.blocks.CommonBlockCategories
import java.util.*

class ComposeBlockFactory() : StatementBlockFactory {

    override val category = CommonBlockCategories.ThisObject

    override val type: String = "tablet_compose"

    override fun buildBlock(): RawJson {
        return RawJson {
            """
                {
                  "type": "$type",
                  "message0": "This screen composition %1 %2",
                  "args0": [
                    {
                      "type": "input_dummy"
                    },
                    {
                      "type": "input_statement",
                      "name": "NAME",
                      "check": [
                        "tablet_headline",
                        "tablet_button",
                        "tablet_text",
                        "tablet_device_value",
                        "tablet_qrcode"
                      ]
                    }
                  ],
                  "inputsInline": true,
                  "colour": 230,
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
        throw NotImplementedError()
    }

    //    override fun dependsOn(): List<Long> {
    //        return listOf(dialog.id)
    //    }
}
