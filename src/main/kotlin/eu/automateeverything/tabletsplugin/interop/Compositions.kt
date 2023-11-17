package eu.automateeverything.tabletsplugin.interop

import eu.automateeverything.domain.automation.StatementNode
import kotlinx.serialization.Serializable

@Serializable
data class UIBlock(
    val headline: Headline? = null,
    val text: Text? = null,
    val singleColumn: SingleColumn? = null,
    @Transient override val next: StatementNode? = null
) : StatementNode

@Serializable data class Headline(val text: String)

@Serializable data class Text(val text: String)

@Serializable data class SingleColumn(val children: List<UIBlock>)
