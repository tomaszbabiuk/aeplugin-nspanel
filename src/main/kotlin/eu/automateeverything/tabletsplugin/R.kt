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

import eu.automateeverything.data.localization.Resource

object R {
    val state_active = Resource("Active", "Aktywny")

    val state_inactive = Resource("Inactive", "Nieaktywny")

    val configurable_tablet_add = Resource("Add tablet", "Dodaj tablet")

    val configurable_tablet_edit = Resource("Edit tablet", "Edytuj tablet")

    val configurable_tablet_title = Resource("Tablets", "Tablety")

    val configurable_tablet_description =
        Resource("A custom control over Android tablet.", "Kontroler dla tabletów z Androidem.")

    val field_port_hint = Resource("Port", "Port")

    val state_unknown = Resource("Unknown", "Nieznany")

    val plugin_name = Resource("Tablets", "Tablety")

    val plugin_description =
        Resource(
            "A support for wall tablets running Android OS",
            "Wsparcie dla tabletów ściennych pracujących pod kontrolą systemu Android"
        )

    val n_a = Resource("n/a", "nd.")

    val category_tablets = Resource("Tablets", "Tablety")

    val block_tablets_option_message = Resource("%1 Option: %2 %3", "%1 Opcja: %2 %3")

    val block_tablets_show_dialog_message =
        Resource(
            "Show dialog %1 Title %2 %3 Headline %4 %5 Options %6",
            "Pokaż dialog %1 Tytuł %2 %3 Treść %4 %5 Opcje %6"
        )
}
