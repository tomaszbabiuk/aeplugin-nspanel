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

import eu.automateeverything.data.coap.VersionManifestDto
import eu.automateeverything.domain.langateway.LanGatewayResolver
import kotlinx.coroutines.*
import kotlinx.serialization.BinaryFormat
import java.net.InetAddress

class CoAPDiscovery(
    private val binaryFormat: BinaryFormat,
    private val lanGatewayResolver: LanGatewayResolver,
    private val progressReporter: (message: String) -> Unit
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun discoverByForceScanning(): Map<InetAddress, VersionManifestDto> = coroutineScope {
        val result = HashMap<InetAddress, VersionManifestDto>()
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

    private fun discover(address: InetAddress): VersionManifestDto? {
        val client = AETabletClient(address, 5683, binaryFormat)
        if (client.isAETablet()) {
            progressReporter("Potential AE tablet, checking manifest of $address")
            val manifest = client.readAEManifest()
            progressReporter("AE tablet found: ${manifest.uniqueId}, v${manifest.versionMajor}.${manifest.versionMinor}")

            return manifest
        }

        return null
    }
}