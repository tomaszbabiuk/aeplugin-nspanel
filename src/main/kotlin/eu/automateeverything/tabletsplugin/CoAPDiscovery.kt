package eu.automateeverything.tabletsplugin

import eu.automateeverything.domain.coap.AutomateEverythingVersionManifest
import eu.automateeverything.domain.langateway.LanGatewayResolver
import kotlinx.coroutines.*
import kotlinx.serialization.BinaryFormat
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.LinkFormat
import java.net.InetAddress

class CoAPDiscovery(private val binaryFormat: BinaryFormat, private val lanGatewayResolver: LanGatewayResolver, private val progressReporter: (message: String) -> Unit) {
    private val coapPort = 5683

    suspend fun discoverByForceScanning() : List<AutomateEverythingVersionManifest> = coroutineScope{
        val result = ArrayList<AutomateEverythingVersionManifest>()
        val gateway = lanGatewayResolver.resolve()
        gateway.forEach {
            val gatewayIP = it.inet4Address.address

            val lookupAddressBegin = InetAddress.getByAddress(
                byteArrayOf(
                    gatewayIP[0], gatewayIP[1], gatewayIP[2],
                    0.toByte()
                )
            )
            val lookupAddressEnd = InetAddress.getByAddress(
                byteArrayOf(
                    gatewayIP[0], gatewayIP[1], gatewayIP[2],
                    255.toByte()
                )
            )

            progressReporter("Looking for CoRE devices in LAN, the IP address range is $lookupAddressBegin - $lookupAddressEnd")

            val jobs = ArrayList<Deferred<AutomateEverythingVersionManifest?>>()

            for (i in 0..255) {
                val ipToCheck = InetAddress.getByAddress(
                    byteArrayOf(
                        gatewayIP[0],
                        gatewayIP[1],
                        gatewayIP[2],
                        i.toByte()
                    )
                )

                val job = async(start = CoroutineStart.LAZY) {
                    discover(ipToCheck)
                }
                jobs.add(job)
            }

            val discovered = jobs.awaitAll()
                .filterNotNull()
                .toList()

            result.addAll(discovered)
        }

        return@coroutineScope result
    }

    private fun discover(address: InetAddress) : AutomateEverythingVersionManifest? {
        val client = CoapClient("coap:/${address}:$coapPort/.well-known/core")
        client.timeout = 5000

        try {
            val response: CoapResponse? = client.get()

            if (response != null && response.isSuccess) {
                val links = LinkFormat.parse(response.responseText)
                val aeLink = links.filter { it.uri == "/automateeverything"}
                if (aeLink.isNotEmpty()) {
                    return readManifest(address)
                }
            } else {
                System.err.println("Error: Unable to perform CoAP multicast request")
            }
        } catch (ignored:Exception) {
        } finally {
            client.shutdown()
        }

        return null
    }

    private fun readManifest(address: InetAddress): AutomateEverythingVersionManifest {
        val client = CoapClient("coap:/${address}:$coapPort/automateeverything")
        client.timeout = 5000

        try {
            val response: CoapResponse? = client.get()
            return binaryFormat.decodeFromByteArray(AutomateEverythingVersionManifest.serializer(), response!!.payload)
        } finally {
            client.shutdown()
        }
    }
}