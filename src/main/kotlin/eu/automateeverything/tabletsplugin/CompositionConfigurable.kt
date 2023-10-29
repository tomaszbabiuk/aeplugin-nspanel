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
open class CompositionConfigurable(private val repository: Repository) :
    NameDescriptionConfigurable() {

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

    override val fieldDefinitions: Map<String, FieldDefinition<*>>
        get() {
            val uniqueNameValidator =
                UniqueValueAcrossItsTypeValidator(
                    CompositionConfigurable::class.java,
                    repository,
                    FIELD_NAME
                )
            val result: MutableMap<String, FieldDefinition<*>> = LinkedHashMap()

            result[FIELD_NAME] = nameField.includeValidator(uniqueNameValidator)
            result[FIELD_DESCRIPTION] = descriptionField
            result[FIELD_TITLE] = titleField
            return result
        }

    override val addNewRes = R.configurable_composition_add
    override val editRes = R.configurable_composition_edit
    override val titleRes = R.configurable_composition_title
    override val descriptionRes = R.configurable_composition_description

    override val iconRaw: String
        get() =
            """
            <svg width="100" height="100" xmlns="http://www.w3.org/2000/svg" xmlns:svg="http://www.w3.org/2000/svg">
             <g class="layer">
              <title>Layer 1</title>
              <g id="svg_1">
               <g id="svg_2">
                <path d="m94.46666,34.66667l-27.6,0l0,-29.13333c0,-0.84752 -0.68581,-1.53333 -1.53333,-1.53333l-59.8,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333l0,88.93333c0,0.84752 0.68581,1.53333 1.53333,1.53333l59.8,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333l0,-29.13333l27.6,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333l0,-27.6c0,-0.84752 -0.68581,-1.53333 -1.53333,-1.53333zm-30.66667,58.26667l-56.73333,0l0,-85.86666l56.73333,0l0,27.6l-9.2,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333l0,1.53333l-19.93333,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333l0,9.2c0,0.84752 0.68581,1.53333 1.53333,1.53333l19.93333,0l0,13.8c0,0.84752 0.68581,1.53333 1.53333,1.53333l9.2,0l0,27.6zm-10.73333,-46l-18.4,0l0,-6.13333l18.4,0l0,6.13333zm39.86667,15.33333l-27.6,0l-9.2,0l0,-13.8l0,-9.2l0,-1.53333l9.2,0l27.6,0l0,24.53333z" id="svg_3"/>
                <path d="m13.2,28.53333l16.86667,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333l0,-9.2c0,-0.84752 -0.68581,-1.53333 -1.53333,-1.53333l-16.86667,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333l0,9.2c0,0.84752 0.68581,1.53333 1.53333,1.53333zm1.53333,-9.2l13.8,0l0,6.13333l-13.8,0l0,-6.13333z" id="svg_4"/>
                <path d="m39.26667,28.53333l13.8,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333l0,-9.2c0,-0.84752 -0.68581,-1.53333 -1.53333,-1.53333l-13.8,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333l0,9.2c0,0.84752 0.68581,1.53333 1.53333,1.53333zm1.53333,-9.2l10.73333,0l0,6.13333l-10.73333,0l0,-6.13333z" id="svg_5"/>
                <path d="m25.46667,50c0.84752,0 1.53333,-0.68581 1.53333,-1.53333l0,-9.2c0,-0.84752 -0.68581,-1.53333 -1.53333,-1.53333l-12.26667,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333l0,9.2c0,0.84752 0.68581,1.53333 1.53333,1.53333l12.26667,0zm-10.73333,-9.2l9.2,0l0,6.13333l-9.2,0l0,-6.13333z" id="svg_6"/>
                <path d="m13.2,59.2c0,0.84752 0.68581,1.53333 1.53333,1.53333l19.93333,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333s-0.68581,-1.53333 -1.53333,-1.53333l-19.93333,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333z" id="svg_7"/>
                <path d="m23.93333,66.86667l-9.2,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333s0.68581,1.53333 1.53333,1.53333l9.2,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333s-0.68581,-1.53333 -1.53333,-1.53333z" id="svg_8"/>
                <path d="m43.86667,68.4c0,-0.84752 -0.68581,-1.53333 -1.53333,-1.53333l-10.73333,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333s0.68581,1.53333 1.53333,1.53333l10.73333,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333z" id="svg_9"/>
                <path d="m37.73333,79.13333l0,7.66667c0,0.84752 0.68581,1.53333 1.53333,1.53333l18.4,0c0.84752,0 1.53333,-0.68581 1.53333,-1.53333l0,-7.66667c0,-0.84752 -0.68581,-1.53333 -1.53333,-1.53333l-18.4,0c-0.84752,0 -1.53333,0.68581 -1.53333,1.53333zm3.06667,1.53333l15.33333,0l0,4.6l-15.33333,0l0,-4.6z" id="svg_10"/>
                <path d="m67.5944,57.43757c0.24558,0.15274 0.52558,0.2291 0.8056,0.2291c0.23509,0 0.46868,-0.05391 0.68581,-0.16172l12.26667,-6.13333c0.51959,-0.26054 0.84752,-0.79062 0.84752,-1.37161s-0.32793,-1.11107 -0.84752,-1.37161l-12.26667,-6.13333c-0.47618,-0.23509 -1.0407,-0.21413 -1.49141,0.06737c-0.45221,0.28002 -0.72774,0.77266 -0.72774,1.30424l0,12.26667c0,0.53158 0.27552,1.02422 0.72774,1.30424zm2.33893,-11.08973l7.30429,3.65215l-7.30429,3.65215l0,-7.30431z" id="svg_11"/>
               </g>
              </g>
             </g>
            </svg>
        """
                .trimIndent()

    override val hasAutomation: Boolean
        get() = false

    override val hasComposition: Boolean
        get() = true

    override val editableIcon: Boolean
        get() = false

    override val taggable: Boolean
        get() = true

    override val generable: Boolean
        get() = false

    companion object {
        const val FIELD_TITLE = "title"
    }
}
