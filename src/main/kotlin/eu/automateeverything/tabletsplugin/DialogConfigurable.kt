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
import eu.automateeverything.devices.DevicesConfigurable
import eu.automateeverything.domain.configurable.*
import org.pf4j.Extension

@Extension
open class DialogConfigurable(private val repository: Repository) : NameDescriptionConfigurable() {

    override val parent: Class<out Configurable>
        get() = DevicesConfigurable::class.java

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
            <svg width="100" height="100" xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg" enable-background="new 0 0 100 100" version="1.1" xml:space="preserve">
             <g class="layer">
              <title>Layer 1</title>
              <path d="m16.62,7.83c-6.14,0 -11.12,3.55 -11.12,7.91l0,68.52c0,4.36 4.99,7.91 11.12,7.91l66.75,0c6.14,0 11.12,-3.55 11.12,-7.91l0,-68.52c0,-4.36 -4.99,-7.91 -11.12,-7.91l-66.75,0zm70.46,7.91l0,68.52c0,1.45 -1.66,2.64 -3.71,2.64l-66.75,0c-2.05,0 -3.71,-1.18 -3.71,-2.64l0,-68.52c0,-1.45 1.66,-2.64 3.71,-2.64l66.75,0c2.05,0 3.71,1.19 3.71,2.64z" id="svg_1"/>
              <path d="m79.74,73.93l-21.62,0c-2.98,0 -5.41,2.51 -5.41,5.61s2.42,5.61 5.41,5.61l21.62,0c2.98,0 5.41,-2.51 5.41,-5.61s-2.42,-5.61 -5.41,-5.61z" id="svg_2"/>
              <path d="m42.48,74.24l-21.62,0c-2.98,0 -5.41,2.51 -5.41,5.61s2.42,5.61 5.41,5.61l21.62,0c2.98,0 5.41,-2.51 5.41,-5.61s-2.42,-5.61 -5.41,-5.61z" id="svg_3" transform="matrix(1 0 0 1 0 0)"/>
              <path d="m24.09,15.71c-4.74,0 -8.59,2.37 -8.59,5.3l0,45.89c0,2.92 3.85,5.3 8.59,5.3l51.52,0c4.74,0 8.59,-2.37 8.59,-5.3l0,-45.89c0,-2.92 -3.85,-5.3 -8.59,-5.3l-51.52,0zm54.39,5.3l0,45.89c0,0.97 -1.28,1.77 -2.86,1.77l-51.52,0c-1.58,0 -2.86,-0.79 -2.86,-1.77l0,-45.89c0,-0.97 1.28,-1.77 2.86,-1.77l51.52,0c1.58,0 2.86,0.8 2.86,1.77z" id="svg_4"/>
             </g>
            </svg>
        """
                .trimIndent()

    override val hasAutomation: Boolean
        get() = false

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
