package eu.automateeverything.tabletsplugin.interop

import kotlinx.serialization.Serializable

@Serializable
data class QuoteDto(val quote: String, val author: String)