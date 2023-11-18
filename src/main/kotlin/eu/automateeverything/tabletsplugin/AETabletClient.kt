package eu.automateeverything.tabletsplugin

import eu.automateeverything.tabletsplugin.interop.DashboardDto
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import eu.automateeverything.tabletsplugin.interop.VersionManifestDto
import java.io.IOException
import java.net.InetAddress
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.serialization.BinaryFormat
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.LinkFormat
import org.eclipse.californium.core.coap.MediaTypeRegistry.APPLICATION_CBOR

class AETabletClient(
    private val address: InetAddress,
    private val port: Int,
    private val binaryFormat: BinaryFormat,
) {
    private val observeResponseInProgress = AtomicBoolean(false)

    private val putQueue = ArrayList<Triple<String, ByteArray, Boolean>>()

    private fun get(endpoint: String, ignoreIOErrors: Boolean = false): CoapResponse? {
        val uri = "coap:/$address:$port$endpoint"
        val client = CoapClient(uri)
        client.timeout = 5000

        try {
            return client.get()
        } catch (ex: Exception) {
            if (!ignoreIOErrors) {
                throw IOException(ex)
            }
        } finally {
            client.shutdown()
        }

        return null
    }

    private fun put(
        endpoint: String,
        payload: ByteArray,
        ignoreIOErrors: Boolean = false
    ): CoapResponse? {
        if (observeResponseInProgress.get()) {
            putQueue.add(Triple(endpoint, payload, ignoreIOErrors))
        } else {

            val uri = "coap:/$address:$port$endpoint"
            val client = CoapClient(uri)
            client.timeout = 5000

            try {
                return client.put(payload, APPLICATION_CBOR)
            } catch (ex: Exception) {
                if (!ignoreIOErrors) {
                    throw IOException(ex)
                }
            } finally {
                client.shutdown()
            }
        }

        return null
    }

    private fun <T : Any> observe(
        endpoint: String,
        responseHandler: (CoapResponse) -> T
    ): CoapClient {
        val uri = "coap:/$address:$port$endpoint"
        val client = CoapClient(uri)
        client.timeout = 5000

        try {
            client.observe(
                object : CoapHandler {
                    override fun onLoad(response: CoapResponse) {
                        observeResponseInProgress.set(true)
                        responseHandler(response)
                        observeResponseInProgress.set(false)
                    }

                    override fun onError() {
                        error("Observe error")
                    }
                }
            )
        } catch (ex: Exception) {
            error(ex)
        }

        return client
    }

    fun releasePutQueue() {
        while (putQueue.size > 0) {
            val firstToPut = putQueue.removeFirst()
            put(firstToPut.first, firstToPut.second)
        }
    }

    fun observeDashboard(handler: (DashboardDto) -> Unit): CoapClient {
        return observe("/dashboard") {
            val activeSceneDto =
                binaryFormat.decodeFromByteArray(DashboardDto.serializer(), it.payload)
            handler(activeSceneDto)
        }
    }

    fun isAETablet(): Boolean {
        val rawResponse = get("/.well-known/core", true)
        if (rawResponse != null && rawResponse.isSuccess) {
            val links = LinkFormat.parse(rawResponse.responseText)
            val aeLink = links.filter { it.uri == "/automateeverything" }
            return aeLink.isNotEmpty()
        }

        return false
    }

    fun readAEManifest(): VersionManifestDto? {
        val rawResponse = get("/automateeverything")
        if (rawResponse != null && rawResponse.isSuccess) {
            return binaryFormat.decodeFromByteArray(
                VersionManifestDto.serializer(),
                rawResponse.payload
            )
        }

        return null
    }

    fun changeDashboard(title: String, dashboardId: Long, content: DashboardItem) {
        val dashboardDto = DashboardDto(title, dashboardId, content)
        val payload = binaryFormat.encodeToByteArray(DashboardDto.serializer(), dashboardDto)
        put("/dashboard", payload)
    }
}
