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

import eu.automateeverything.data.hardware.PortValue
import eu.automateeverything.data.localization.Resource
import java.math.BigDecimal

data class TabletUIState(val dashboardId: Long, val lastPressedButtonRef: String?)

class TabletConnectorPortValue(val value: TabletUIState) : PortValue {
    override fun toFormattedString(): Resource {
        return R.n_a
    }

    override fun asDecimal(): BigDecimal {
        return BigDecimal(value.dashboardId)
    }
}
