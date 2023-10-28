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
    val field_title_hint = Resource("Title", "Tytuł")
    val field_headline_hint = Resource("Headline", "Treść")
    val field_option1_hint = Resource("Option 1", "Opcja 1")
    val field_option2_hint = Resource("Option 2", "Opcja 2")
    val field_option3_hint = Resource("Option 3", "Opcja 3")
    val field_option4_hint = Resource("Option 4", "Opcja 4")
    val field_option5_hint = Resource("Option 5", "Opcja 5")
    val field_option6_hint = Resource("Option 6", "Opcja 6")
    val field_option7_hint = Resource("Option 7", "Opcja 7")
    val state_active = Resource("Active", "Aktywny")
    val state_inactive = Resource("Inactive", "Nieaktywny")
    val configurable_tablet_add = Resource("Add tablet", "Dodaj tablet")
    val configurable_tablet_edit = Resource("Edit tablet", "Edytuj tablet")
    val configurable_tablets_title = Resource("Tablets", "Tablety")
    val configurable_tablets_description =
        Resource(
            "Objects related to tablets and content on those tablets.",
            "Obiekty związane z tabletami i treścią na tych tabletach."
        )
    val configurable_tablet_title = Resource("Tablets (Android)", "Tablety (Android)")
    val configurable_tablet_description =
        Resource("A custom control over Android tablet.", "Kontroler dla tabletów z Androidem.")
    val configurable_dialog_add = Resource("Add dialog", "Dodaj dialog")
    val configurable_dialog_edit = Resource("Edit dialog", "Edytuj dialog")
    val configurable_dialog_title = Resource("Dialogs", "Dialogi")
    val configurable_dialog_description =
        Resource(
            "A dialog than can be displayed on Android tablet.",
            "Dialog wyświetlany na tabletach z androidem"
        )
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

    fun block_show_dialog_message(dialogName: String) =
        Resource("Show \\\"$dialogName\\\"", "Pokaż \\\"$dialogName\\\"")

    fun block_dialog_option_selected(dialogName: String) =
        Resource(
            "\\\"$dialogName\\\" %1 - option %2 selected",
            "\\\"$dialogName\\\" %1 - opcja %2 została wybrana"
        )

    val block_screen_composition_message =
        Resource("Show screen composition on %1 %2", "Pokaż kompozycję ekranu na %1 %2")
}
