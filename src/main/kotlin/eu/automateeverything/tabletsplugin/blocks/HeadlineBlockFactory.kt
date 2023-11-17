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
import eu.automateeverything.tabletsplugin.composition.UIBlock
import eu.automateeverything.tabletsplugin.composition.UIBlockFactory
import eu.automateeverything.tabletsplugin.composition.UIContext

class HeadlineBlockFactory : UIBlockFactory {

    override val category = TabletsBlockCategories.UI

    override val type: String = "tablet_headline"

    override fun buildBlock(): RawJson {
        return RawJson { language ->
            """
            {
              "type": "$type",
              "implicitAlign0": "RIGHT",
              "message0": "${R.block_headline_message.getValue(language)}",
              "args0": [
                {
                  "type": "field_input",
                  "name": "TEXT",
                  "text": ""
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
    ): UIBlock {
        return UIBlock()
    }
}
