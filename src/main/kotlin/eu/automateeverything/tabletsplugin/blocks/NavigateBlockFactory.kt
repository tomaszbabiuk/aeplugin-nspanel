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
import eu.automateeverything.data.instances.InstanceDto
import eu.automateeverything.domain.automation.*
import eu.automateeverything.domain.configurable.NameDescriptionConfigurable.Companion.FIELD_NAME
import eu.automateeverything.tabletsplugin.composition.UIBlock
import eu.automateeverything.tabletsplugin.composition.UIBlockFactory
import eu.automateeverything.tabletsplugin.composition.UIContext

class NavigateBlockFactory(private val dashboard: InstanceDto) : UIBlockFactory {

    override val category = TabletsBlockCategories.Actions

    private val typePrefix = "tablet_navigate_"

    override val type: String = "$typePrefix${dashboard.id}"

    override fun buildBlock(): RawJson {
        return RawJson {
            """
                {
                  "type": "$type",
                  "message0": "${dashboard.fields[FIELD_NAME]}",
                  "output": "tablet_navigate",
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
        order: Int
    ): UIBlock {
        throw NotImplementedError()
    }

    override fun dependsOn(): List<Long> {
        return listOf(dashboard.id)
    }

    override fun belongs(type: String): Boolean {
        return type.startsWith(typePrefix)
    }
}
