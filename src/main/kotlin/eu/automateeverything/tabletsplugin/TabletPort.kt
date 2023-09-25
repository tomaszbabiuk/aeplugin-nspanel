/*
 * Copyright (c) 2019-2023 Tomasz Babiuk
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.automateeverything.tabletsplugin

import eu.automateeverything.domain.hardware.InputPort
import org.eclipse.californium.core.CoapClient
import org.eclipse.californium.core.CoapHandler
import org.eclipse.californium.core.CoapResponse
import java.net.InetAddress

class TabletPort(
    override val id: String,
    override var lastSeenTimestamp: Long,
    private val inetAddress: InetAddress) : InputPort<TabletConnectorPortValue>
{
    override val valueClazz = TabletConnectorPortValue::class.java

    override fun read(): TabletConnectorPortValue {
        return TabletConnectorPortValue()
    }

    fun subscribeToActions(): CoapClient {
        val uri = "coap:/${inetAddress}:5683/actions"

        val client = CoapClient(uri)
        client.observe(object : CoapHandler {
            override fun onLoad(response: CoapResponse) {
                println(response.code)
                println(response.options)
                println(String(response.payload))
            }

            override fun onError() {
                println("ERROR")
            }
        })

        return client
    }
}