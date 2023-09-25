package eu.automateeverything.tabletsplugin

import eu.automateeverything.data.coap.VersionManifestDto
import kotlinx.serialization.BinaryFormat
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.LinkFormat
import java.net.InetAddress

class AETabletClient(private val address: InetAddress, private val port: Int, private val binaryFormat: BinaryFormat) {
    private inline fun <T : Any> get(endpoint: String, responseHandler: (CoapResponse) -> T) : T {
        val uri = "coap:/$address:$port$endpoint"
        val client = CoapClient(uri)
        client.timeout = 5000

        try {
            val response: CoapResponse = client.get()
            if (response.isSuccess) {
                return responseHandler(response)
            } else {
                error("No success")
            }
        } catch (ex: Exception) {
            error(ex)
        } finally {
            client.shutdown()
        }
    }

    private fun <T : Any> observe(endpoint: String, responseHandler: (CoapResponse) -> T) : CoapClient  {
        val uri = "coap:/$address:$port$endpoint"
        val client = CoapClient(uri)
        client.timeout = 5000

        try {
            client.observe(object : CoapHandler {
                override fun onLoad(response: CoapResponse) {
                    responseHandler(response)
                }

                override fun onError() {
                    error("Observe error")
                }
            })
        } catch (ex: Exception) {
            error(ex)
        }

        return client
    }

    fun subscribeToActions(): CoapClient {
        return observe("/actions") {
            //TODO
            //deserialization
        }
    }

    fun isAETablet(): Boolean {
        get("/.well-known/core") { response ->
            val links = LinkFormat.parse(response.responseText)
            val aeLink = links.filter { it.uri == "/automateeverything" }
            return aeLink.isNotEmpty()
        }
    }

    fun readAEManifest() : VersionManifestDto {
        get("/automateeverything") {
            return binaryFormat.decodeFromByteArray(VersionManifestDto.serializer(), it.payload)
        }
    }
}