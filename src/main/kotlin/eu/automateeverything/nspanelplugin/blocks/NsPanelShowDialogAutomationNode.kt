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

package eu.automateeverything.nspanelplugin.blocks

import eu.automateeverything.domain.automation.StatementNode
import eu.automateeverything.domain.automation.StatementNodeBase
import eu.automateeverything.nspanelplugin.NsPanelAutomationUnit
import java.util.*

class NsPanelShowDialogAutomationNode(
    override val next: StatementNode?,
    private val screenId: String,
    headline: String,
    private val nsPanelDevice: NsPanelAutomationUnit,
    private val options: LinkedHashMap<String, StatementNode>,
    ) : StatementNodeBase() {

    override fun process(now: Calendar, firstLoop: Boolean) {
        if (nsPanelDevice.activeScreenId != screenId) {
            nsPanelDevice.changeScreen(screenId, options.keys.toTypedArray())
        }

        options.values.forEach { it.process(now, firstLoop) }

        next?.process(now, firstLoop)
    }
}