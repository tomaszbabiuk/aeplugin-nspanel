package eu.automateeverything.tabletsplugin.blocks

import eu.automateeverything.domain.automation.BlockFactory
import eu.automateeverything.tabletsplugin.interop.UINode

interface UIBlockFactory : BlockFactory<UINode, UIContext, TabletsTransformer>

class UIContext(
    val factoriesCache: List<BlockFactory<*, *, *>>,
) {
    private val routes: MutableMap<String, Long> = HashMap()

    fun registerRoute(targetDashboardId: Long, reference: String) {
        routes[reference] = targetDashboardId
    }

    fun resolveRoute(reference: String): Long? {
        return routes[reference]
    }
}
