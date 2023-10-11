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
import eu.automateeverything.tabletsplugin.DialogConfigurable
import eu.automateeverything.tabletsplugin.R
import eu.automateeverything.tabletsplugin.TabletAutomationUnit
import java.util.*

class ShowDialogBlockFactory(private val dialog: InstanceDto) : StatementBlockFactory {

    override val category = CommonBlockCategories.ThisObject

    private val typePrefix = "show_dialog_"

    override val type: String = "$typePrefix${dialog.id}"

    override fun buildBlock(): RawJson {
        return RawJson {
            """
                   { "type":  "$type",
                     "colour": ${category.color},
                     "tooltip": null,
                     "helpUrl": null,
                     "message0": "${R.block_show_dialog_message(dialog.fields[FIELD_NAME]!!).getValue(it)}",
                     "previousStatement": null,
                     "nextStatement": null }
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
        val thisAutomationUnit =
            context.automationUnitsCache[context.thisInstance.id] as TabletAutomationUnit

        val dialogInstanceIdRaw = block.type.replace(typePrefix, "")
        val dialogInstanceId = dialogInstanceIdRaw.toLong()

        val dialogInstance =
            context.allInstances[dialogInstanceId]
                ?: throw MalformedBlockException(
                    type,
                    "Show dialog block points to a ${DialogConfigurable::class.java.simpleName} with id== $dialogInstanceId but device with this id does not exists"
                )

        val title = dialogInstance.fields[DialogConfigurable.FIELD_TITLE]!!
        val headline = dialogInstance.fields[DialogConfigurable.FIELD_HEADLINE]!!
        val option1 = dialogInstance.fields[DialogConfigurable.FIELD_OPTION1]!!
        val option2 = dialogInstance.fields[DialogConfigurable.FIELD_OPTION2]
        val option3 = dialogInstance.fields[DialogConfigurable.FIELD_OPTION3]
        val option4 = dialogInstance.fields[DialogConfigurable.FIELD_OPTION4]
        val option5 = dialogInstance.fields[DialogConfigurable.FIELD_OPTION5]
        val option6 = dialogInstance.fields[DialogConfigurable.FIELD_OPTION6]
        val option7 = dialogInstance.fields[DialogConfigurable.FIELD_OPTION7]

        val options = ArrayList<String>()
        fun addOption(item: String?) {
            if (item != null && item != "") {
                options.add(item)
            }
        }

        addOption(option1)
        addOption(option2)
        addOption(option3)
        addOption(option4)
        addOption(option5)
        addOption(option6)
        addOption(option7)

        return ShowDialogAutomationNode(
            next,
            dialogInstanceId.toString(),
            title,
            headline,
            thisAutomationUnit,
            options.toTypedArray()
        )
    }
}
