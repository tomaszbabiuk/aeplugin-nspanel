package eu.automateeverything.tabletsplugin.blocks

import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.tabletsplugin.interop.UIBlock

interface UIBlockFactory : BlockFactory<UIBlock, UIContext, TabletsTransformer>

data class UIContext(val factoriesCache: List<BlockFactory<*, *, *>>)
