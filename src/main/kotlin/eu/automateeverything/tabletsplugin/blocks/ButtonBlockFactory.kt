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
import eu.automateeverything.tabletsplugin.R
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import eu.automateeverything.tabletsplugin.interop.NavigationButton
import eu.automateeverything.tabletsplugin.interop.Text
import eu.automateeverything.tabletsplugin.interop.UINode
import java.util.*

class ButtonBlockFactory : UIBlockFactory {

    override val category = TabletsBlockCategories.UI

    override val type: String = "tablet_button"

    override fun buildBlock(): RawJson {
        return RawJson { language ->
            """
                {
                  "type": "$type",
                  "message0": "${R.block_button_message.getValue(language)}",
                  "args0": [
                    {
                      "type": "field_input",
                      "name": "LABEL",
                      "text": ""
                    },
                    {
                      "type": "input_value",
                      "name": "ACTION",
                      "check": [
                        "tablet_navigate",
                        "tablet_change_state"
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
        context: UIContext,
        transformer: TabletsTransformer,
    ): UINode {
        fun continueWithNavigationButton(caption: String, dashboardId: Long): UINode {
            val reference = UUID.randomUUID().toString()
            val buttonElement = NavigationButton(caption, reference)
            context.registerRoute(dashboardId, reference)
            return UINode(DashboardItem(navigationButton = buttonElement))
        }

        fun continueWithEmptyButton(caption: String): UINode {
            return UINode(DashboardItem(text = Text(caption)))
        }

        val captionField =
            block.fields!!.find { it.name == "LABEL" }
                ?: throw MalformedBlockException(block.type, "Should have CAPTION field defined")
        val caption = captionField.value ?: ""

        val actionValue =
            block.values!!.find { it.name == "ACTION" }
                ?: throw MalformedBlockException(block.type, "Should have ACTION value defined")

        if (actionValue.block != null) {
            val actionTypeRaw = actionValue.block!!.type
            if (actionTypeRaw.startsWith(NavigateBlockFactory.TYPE_PREFIX)) {
                val dashboardIdRaw =
                    actionValue.block!!.type.replace(NavigateBlockFactory.TYPE_PREFIX, "")
                val dashboardId = dashboardIdRaw.toLong()
                return continueWithNavigationButton(caption, dashboardId)
            }
        }

        return continueWithEmptyButton(caption)
    }
}
