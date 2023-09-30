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


import eu.automateeverything.data.Repository
import eu.automateeverything.data.coap.VersionManifestDto
import eu.automateeverything.domain.events.EventBus
import eu.automateeverything.domain.hardware.*
import eu.automateeverything.domain.langateway.LanGatewayResolver
import kotlinx.coroutines.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import org.eclipse.californium.core.CoapClient
import java.net.InetAddress
import java.util.Calendar

@OptIn(ExperimentalSerializationApi::class)
class TabletAdapter(
    owningPluginId: String,
    private val lanGatewayResolver: LanGatewayResolver,
    private val portFinder: PortFinder,
    private val repository: Repository,
    eventBus: EventBus) : HardwareAdapterBase<TabletPort>(owningPluginId, "0", eventBus) {

    private var actionClients: List<CoapClient>? = null
    private val binaryFormat = Cbor

    private val coapDiscovery = CoAPDiscovery(binaryFormat, lanGatewayResolver) {
        logDiscovery(it)
    }

    private val idBuilder = PortIdBuilder(owningPluginId)

    override suspend fun internalDiscovery(mode: DiscoveryMode) = coroutineScope {
        val forced = coapDiscovery.discoverByForceScanning()
        val now = Calendar.getInstance()
        val ports = forced.map {
            buildTabletPort(it.key, it.value, now.timeInMillis)
        }

        logDiscovery("Ports found: ${ports.size}")

        addPotentialNewPorts(ports)
    }

    private fun buildTabletPort(address: InetAddress, manifest: VersionManifestDto, now: Long): TabletPort {
        val portId = idBuilder.buildPortId("TAB", manifest.uniqueId, "DATA")
        val aeClient = AETabletClient(address, COAP_PORT, binaryFormat)
        val port = TabletPort(portId, now, aeClient)
        port.start()
        return port
    }

    override fun executePendingChanges() {
    }

    override fun stop() {
        ports.values.forEach { it.stop() }
    }

    override fun start() {

    }

    companion object {
        const val COAP_PORT = 5683
    }
}

