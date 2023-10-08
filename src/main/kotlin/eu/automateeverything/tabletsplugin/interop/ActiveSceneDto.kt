package eu.automateeverything.tabletsplugin.interop

import kotlinx.serialization.Serializable

@Serializable
data class ActiveSceneDto(
    val sceneId: String,
    val optionId: Int? = null,
    val dialog: DialogDto? = null,
    val quote: QuoteDto? = null
)

