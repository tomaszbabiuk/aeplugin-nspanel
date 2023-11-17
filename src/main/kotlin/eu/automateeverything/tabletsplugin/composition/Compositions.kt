package eu.automateeverything.tabletsplugin.composition

import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.domain.automation.StatementNode
import eu.automateeverything.tabletsplugin.blocks.TabletsTransformer

interface UIBlockFactory : BlockFactory<UIBlock, UIContext, TabletsTransformer>

data class UIBlock(
    val headline: String? = null,
    val text: String? = null,
    override val next: StatementNode? = null
) : StatementNode

data class UIContext(val factoriesCache: List<BlockFactory<*, *, *>>)
