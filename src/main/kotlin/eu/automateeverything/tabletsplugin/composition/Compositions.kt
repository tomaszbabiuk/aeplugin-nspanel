package eu.automateeverything.tabletsplugin.composition

import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.domain.automation.StatementNode

interface UIBlockFactory : BlockFactory<UIBlock>

data class UIBlock(
    val headline: String? = null,
    val text: String? = null,
    override val next: StatementNode? = null
) : StatementNode
