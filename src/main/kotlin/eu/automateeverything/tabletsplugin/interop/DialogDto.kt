package eu.automateeverything.tabletsplugin.interop

import kotlinx.serialization.Serializable

@Serializable
data class DialogDto(val title: String, val headline: String, val options: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DialogDto

        if (title != other.title) return false
        if (headline != other.headline) return false
        if (!options.contentEquals(other.options)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + headline.hashCode()
        result = 31 * result + options.contentHashCode()
        return result
    }
}