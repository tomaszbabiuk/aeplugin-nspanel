package eu.automateeverything.tabletsplugin.blocks

import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.tabletsplugin.interop.UINode

interface UIBlockFactory : BlockFactory<UINode, UIContext, TabletsTransformer>

data class UIContext(val factoriesCache: List<BlockFactory<*, *, *>>)
