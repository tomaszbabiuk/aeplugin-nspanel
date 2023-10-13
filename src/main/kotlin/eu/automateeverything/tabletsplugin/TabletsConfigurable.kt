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

package eu.automateeverything.tabletsplugin

import eu.automateeverything.data.localization.Resource
import eu.automateeverything.domain.configurable.CategoryConfigurable
import eu.automateeverything.domain.configurable.Configurable
import org.pf4j.Extension

@Extension
class TabletsConfigurable : CategoryConfigurable() {

    override val parent: Class<out Configurable>?
        get() = null

    override val titleRes: Resource
        get() = R.configurable_tablets_title

    override val descriptionRes: Resource
        get() = R.configurable_tablets_description

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
}
