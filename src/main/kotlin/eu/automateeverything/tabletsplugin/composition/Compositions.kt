package eu.automateeverything.tabletsplugin.composition

import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.domain.automation.StatementNode
import eu.automateeverything.tabletsplugin.blocks.TabletsTransformer

interface UIBlockFactory : BlockFactory<UIBlock, UIContext, TabletsTransformer>

data class UIBlock(
    val headline: Headline? = null,
    val text: Text? = null,
    val singleColumn: SingleColumn? = null,
    override val next: StatementNode? = null
) : StatementNode

data class Headline(val text: String)

data class Text(val text: String)

data class SingleColumn(val children: List<UIBlock>)

data class UIContext(val factoriesCache: List<BlockFactory<*, *, *>>)
