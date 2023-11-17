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
import eu.automateeverything.tabletsplugin.composition.UIBlock
import eu.automateeverything.tabletsplugin.composition.UIBlockFactory
import eu.automateeverything.tabletsplugin.composition.UIContext

class StartHereBlockFactory : UIBlockFactory {

    override val category = CommonBlockCategories.ThisObject

    override val type: String = "single"

    override fun buildBlock(): RawJson {
        return RawJson { language ->
            """
                {
                  "type": "$type",
                  "message0": "${R.block_start_here_message.getValue(language)}",
                  "nextStatement": [
                    "tablet_single_column",
                    "tablet_double_column",
                    "tablet_quarter_grid"
                  ],
                  "nextStatement": "single",                
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

        // transformer.transformStatement(next!!, context)
        return UIBlock()
    }
}
