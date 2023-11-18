package eu.automateeverything.tabletsplugin.interop

import kotlinx.serialization.Serializable

@Serializable
data class DashboardDto(
    val title: String,
    val id: Long,
    val content: DashboardItem,
    val buttonRef: String? = null,
)
