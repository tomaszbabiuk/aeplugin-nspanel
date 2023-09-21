package eu.automateeverything.tabletsplugin

import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapResponse
import java.net.InetAddress

class CoAPDiscovery {
    fun discover(address: InetAddress) {
        val multicastPort = 5683

        val client = CoapClient("coap:/${address}:$multicastPort/.well-known/core")
        client.timeout = 5000

        val response: CoapResponse = client.get()

        if (response.isSuccess) {
            // Print the response payload (CoRE Link Format)
            println("CoAP Discovery Response:")
            System.out.println(response.getResponseText())
        } else {
            System.err.println("Error: Unable to perform CoAP multicast request")
        }

        client.shutdown()
    }
}