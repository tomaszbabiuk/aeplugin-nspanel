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

class TabletPort(
    override val id: String,
    override var lastSeenTimestamp: Long,
    private val aeClient: AETabletClient) : InputPort<TabletConnectorPortValue>{
    private var actionsClient: CoapClient? = null
    override val valueClazz = TabletConnectorPortValue::class.java
    private var sceneId = "init"
    private var optionId: String? = null

    override fun read(): TabletConnectorPortValue {
        return TabletConnectorPortValue()
    }

    fun start() {
        actionsClient = aeClient.observeActions {
            sceneId = it.sceneId
            optionId = it.optionId
        }
    }

    fun stop() {
        actionsClient?.shutdown()
    }
}