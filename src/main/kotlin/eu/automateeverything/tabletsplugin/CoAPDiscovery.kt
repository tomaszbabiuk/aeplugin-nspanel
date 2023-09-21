package eu.automateeverything.tabletsplugin

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import org.eclipse.californium.core.coap.LinkFormat
import java.net.InetAddress

class CoAPDiscovery {
    private val multicastPort = 5683

    fun discover(address: InetAddress) {
        val client = CoapClient("coap:/${address}:$multicastPort/.well-known/core")
        client.timeout = 5000

        try {
            val response: CoapResponse? = client.get()

            if (response != null && response?.isSuccess) {
                println("CoAP Discovery Response:")
                val links = LinkFormat.parse(response.responseText)
                links.forEach {
                    println(it.uri)
                }
            } else {
                System.err.println("Error: Unable to perform CoAP multicast request")
            }
        } catch (ignored:Exception) {
        } finally {
            client.shutdown()
        }
    }
}