/*
 * Copyright (c) 2019-2022 Tomasz Babiuk
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


import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.DiscoveryMode
import eu.automateeverything.domain.hardware.HardwareAdapterBase
import eu.automateeverything.domain.hardware.Port
import eu.automateeverything.domain.hardware.PortIdBuilder
import eu.automateeverything.domain.langateway.LanGatewayResolver
import kotlinx.coroutines.*
import java.net.InetAddress


class TabletAdapter(
    owningPluginId: String,
    private val lanGatewayResolver: LanGatewayResolver,
    eventBus: EventBus) : HardwareAdapterBase<Port<*>>(owningPluginId, "0", eventBus) {
    private val coapDiscovery = CoAPDiscovery()

    private val idBuilder = PortIdBuilder(owningPluginId)

    override suspend fun internalDiscovery(mode: DiscoveryMode) = coroutineScope {
        discoverWithCoRE()
        discoverOnLAN()
    }

    private suspend fun discoverOnLAN() = withContext(Dispatchers.IO) {
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

            eventBus.broadcastDiscoveryEvent(
                owningPluginId,
                "Looking for CoRE devices in LAN, the IP address range is $lookupAddressBegin - $lookupAddressEnd"
            )


            val jobs = ArrayList<Deferred<Unit>>()

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
                    coapDiscovery.discover(ipToCheck)
                }
                jobs.add(job)
            }

            val result = jobs.awaitAll()
                .filterNotNull()
                .toList()
        }
    }

    private fun discoverWithCoRE() {
        val multicastAddresses = lanGatewayResolver.resolve().flatMap { it.broadcastAddresses }
        try {
            multicastAddresses.forEach {
                coapDiscovery.discover(it)
            }
        } catch (ex: Exception) {
            eventBus.broadcastDiscoveryEvent(owningPluginId, "CoAP discovery failed: ${ex.message}")
        }
    }

    override fun executePendingChanges() {
    }

    override fun stop() {
    }

    override fun start() {
    }
}

