package eu.automateeverything.tabletsplugin.interop

import eu.automateeverything.domain.automation.StatementNode
import kotlinx.serialization.Serializable

@Serializable
data class UINode(val item: DashboardItem, override val next: StatementNode? = null) :
    StatementNode

@Serializable
data class DashboardItem(
    val headline: Headline? = null,
    val text: Text? = null,
    val singleColumn: SingleColumn? = null,
    val quarterControl: QuarterControl? = null,
    val navigationButton: NavigationButton? = null
)

@Serializable data class Headline(val text: String)

@Serializable data class Text(val text: String)

@Serializable data class NavigationButton(val label: String, val ref: String)

@Serializable data class SingleColumn(val children: List<DashboardItem>)

@Serializable
data class QuarterControl(
    val device1Id: Long?,
    val device2Id: Long?,
    val device3Id: Long?,
    val device4Id: Long?
)
