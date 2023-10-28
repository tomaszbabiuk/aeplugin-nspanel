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

package eu.automateeverything.tabletsplugin

import eu.automateeverything.data.Repository
import eu.automateeverything.domain.configurable.*
import org.pf4j.Extension

@Extension
open class DialogConfigurable(private val repository: Repository) : NameDescriptionConfigurable() {

    override val parent: Class<out Configurable>
        get() = TabletsConfigurable::class.java

    private val titleField =
        StringField(
            FIELD_TITLE,
            R.field_title_hint,
            50,
            "",
            RequiredStringValidator(),
            MaxStringLengthValidator(50)
        )

    private val headlineField =
        StringField(
            FIELD_HEADLINE,
            R.field_headline_hint,
            200,
            "",
            RequiredStringValidator(),
            MaxStringLengthValidator(200)
        )

    private val option1Field =
        StringField(
            FIELD_OPTION1,
            R.field_option1_hint,
            50,
            "",
            RequiredStringValidator(),
            MaxStringLengthValidator(50)
        )

    private val option2Field =
        StringField(FIELD_OPTION2, R.field_option2_hint, 50, "", MaxStringLengthValidator(50))

    private val option3Field =
        StringField(FIELD_OPTION3, R.field_option3_hint, 50, "", MaxStringLengthValidator(50))

    private val option4Field =
        StringField(FIELD_OPTION4, R.field_option4_hint, 50, "", MaxStringLengthValidator(50))

    private val option5Field =
        StringField(FIELD_OPTION5, R.field_option5_hint, 50, "", MaxStringLengthValidator(50))

    private val option6Field =
        StringField(FIELD_OPTION6, R.field_option6_hint, 50, "", MaxStringLengthValidator(50))

    private val option7Field =
        StringField(FIELD_OPTION7, R.field_option7_hint, 50, "", MaxStringLengthValidator(50))

    override val fieldDefinitions: Map<String, FieldDefinition<*>>
        get() {
            val uniqueNameValidator =
                UniqueValueAcrossItsTypeValidator(
                    DialogConfigurable::class.java,
                    repository,
                    FIELD_NAME
                )
            val result: MutableMap<String, FieldDefinition<*>> = LinkedHashMap()

            result[FIELD_NAME] = nameField.includeValidator(uniqueNameValidator)
            result[FIELD_DESCRIPTION] = descriptionField
            result[FIELD_TITLE] = titleField
            result[FIELD_HEADLINE] = headlineField
            result[FIELD_OPTION1] = option1Field
            result[FIELD_OPTION2] = option2Field
            result[FIELD_OPTION3] = option3Field
            result[FIELD_OPTION4] = option4Field
            result[FIELD_OPTION5] = option5Field
            result[FIELD_OPTION6] = option6Field
            result[FIELD_OPTION7] = option7Field
            return result
        }

    override val addNewRes = R.configurable_dialog_add
    override val editRes = R.configurable_dialog_edit
    override val titleRes = R.configurable_dialog_title
    override val descriptionRes = R.configurable_dialog_description

    override val iconRaw: String
        get() =
            """
            <svg width="100" height="100" xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
             <title>dialog_1</title>
             <g class="layer">
              <title>Layer 1</title>
              <path d="m89.7695,86.35039l0,0.5367l-0.16101,0.26835a4.91081,4.91081 0 0 1 -0.48303,0.91239a3.48855,3.48855 0 0 1 -0.26835,0.40253a3.94475,3.94475 0 0 1 -2.97869,1.36859l0,0a6.09155,6.09155 0 0 1 -2.20047,-0.21468l-25.62744,-7.11128a5.07182,5.07182 0 0 1 -1.26125,-0.64404l-2.79084,0l0,-0.29519a33.4096,33.4096 0 0 1 -3.99842,0.29519c-22.00471,0 -39.85,-16.10101 -39.85,-35.87842s17.84529,-35.85158 39.85,-35.85158s39.85,16.10101 39.85,35.85158l0,39.87684a3.03236,3.03236 0 0 1 -0.08051,0.48303zm-7.8895,-32.36303l0,-8.05051c0,-15.4033 -14.27623,-27.88158 -31.88,-27.88158s-31.88,12.53195 -31.88,27.93525s14.27623,27.90842 31.88,27.90842l8.05051,0l0,0a6.22572,6.22572 0 0 1 2.22731,0.18785l21.68269,6.03788l-0.08051,-26.13731zm-47.82,0a3.97158,3.97158 0 0 1 3.97158,-3.99842l23.93684,0a3.99842,3.99842 0 0 1 0,8.05051l-23.93684,0a3.97158,3.97158 0 0 1 -3.97158,-3.97158l0,-0.08051zm27.90842,-12.04892l-23.93684,0a3.99842,3.99842 0 0 1 0,-8.05051l23.93684,0a3.99842,3.99842 0 0 1 0,8.05051z" fill="black" id="svg_1"/>
             </g>
            </svg>
        """
                .trimIndent()

    override val hasAutomation: Boolean
        get() = true

    override val editableIcon: Boolean
        get() = false

    override val taggable: Boolean
        get() = true

    override val generable: Boolean
        get() = false

    companion object {
        const val FIELD_TITLE = "title"
        const val FIELD_HEADLINE = "headline"
        const val FIELD_OPTION1 = "option1"
        const val FIELD_OPTION2 = "option2"
        const val FIELD_OPTION3 = "option3"
        const val FIELD_OPTION4 = "option4"
        const val FIELD_OPTION5 = "option5"
        const val FIELD_OPTION6 = "option6"
        const val FIELD_OPTION7 = "option7"
    }
}
