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

import eu.automateeverything.domain.automation.Block
import eu.automateeverything.domain.automation.StatementNode
import eu.automateeverything.tabletsplugin.composition.UIContext

class TabletsTransformer {

    fun transform(blocks: List<Block>, context: UIContext, order: Int = 0): List<StatementNode> {
        val masterNodes = ArrayList<StatementNode>()

        blocks.forEach {
            //            val masterNode = transformTrigger(it, context, order)
            //            masterNodes.add(masterNode)
            println(it)
        }

        return masterNodes
    }
}
