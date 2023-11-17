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

import eu.automateeverything.domain.automation.*
import eu.automateeverything.tabletsplugin.interop.UIBlock

class TabletsTransformer {

    fun transform(blocks: List<Block>, context: UIContext): UIBlock? {
        return blocks
            .filter { it.type == "single" }
            .map { transformStartingPoint(it, context) }
            .firstOrNull()
    }

    fun transformStatement(block: Block, context: UIContext): UIBlock {
        var next: StatementNode? = null
        if (block.next != null) {
            next = transformStatement(block.next!!.block!!, context)
        }

        val blockFactory =
            context.factoriesCache.filterIsInstance<UIBlockFactory>().find { it.type == block.type }

        if (blockFactory != null) {
            return blockFactory.transform(block, next, context, this)
        }

        throw UnknownStatementBlockException(block.type)
    }

    private fun transformStartingPoint(
        block: Block,
        context: UIContext,
    ): UIBlock {
        var next: StatementNode? = null
        if (block.next != null) {
            next = transformStatement(block.next!!.block!!, context)
        }

        val blockFactory =
            context.factoriesCache.filterIsInstance<StartHereBlockFactory>().find {
                it.type == block.type
            }

        if (blockFactory != null) {
            return blockFactory.transform(block, next, context, this)
        }

        throw UnknownTriggerBlockException(block.type)
    }
}
