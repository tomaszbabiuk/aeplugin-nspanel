package eu.automateeverything.tabletsplugin.interop

import kotlinx.serialization.Serializable

@Serializable
data class DashboardDto(
    val dashboardId: Long,
    val content: UIBlock,
    val buttonId: Int? = null,
)
