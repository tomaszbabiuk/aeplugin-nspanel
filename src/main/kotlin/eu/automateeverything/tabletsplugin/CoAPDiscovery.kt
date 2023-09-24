package eu.automateeverything.tabletsplugin

import eu.automateeverything.data.coap.VersionManifestDto
import eu.automateeverything.domain.langateway.LanGatewayResolver
import kotlinx.coroutines.*
import kotlinx.serialization.BinaryFormat
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.LinkFormat
import java.net.InetAddress

class CoAPDiscovery(private val binaryFormat: BinaryFormat, private val lanGatewayResolver: LanGatewayResolver, private val progressReporter: (message: String) -> Unit) {
    private val coapPort = 5683

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun discoverByForceScanning() : Map<InetAddress, VersionManifestDto> = coroutineScope{
        val result = HashMap<InetAddress,VersionManifestDto>()
        val gateways = lanGatewayResolver.resolve()
        gateways.forEach { gateway ->
            val gatewayIP = gateway.inet4Address.address

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

            val jobs = HashMap<InetAddress, Deferred<VersionManifestDto?>>()

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
                jobs[ipToCheck] = job
            }


            jobs.values.awaitAll()

            jobs
                .filter { it.value.getCompleted() != null }
                .forEach {
                    result[it.key] = it.value.getCompleted()!!
                }

        }

        return@coroutineScope result
    }

    private fun discover(address: InetAddress) : VersionManifestDto? {
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

    private fun readManifest(address: InetAddress): VersionManifestDto {
        val client = CoapClient("coap:/${address}:$coapPort/automateeverything")
        client.timeout = 5000

        try {
            val response: CoapResponse? = client.get()
            return binaryFormat.decodeFromByteArray(VersionManifestDto.serializer(), response!!.payload)
        } finally {
            client.shutdown()
        }
    }
}