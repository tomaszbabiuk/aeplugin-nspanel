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

@file:Suppress("FunctionName")

package eu.automateeverything.tabletsplugin

import eu.automateeverything.data.localization.Resource

object R {
    val field_initial_composition = Resource("Initial composition", "Kompozycja startowa")
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
    val configurable_dashboard_add = Resource("Add dashboard", "Dodaj panel")
    val configurable_dashboard_edit = Resource("Edit dashboard", "Edytuj dashboard")
    val configurable_dashboard_title = Resource("Dashboards", "Panele kontrolne")
    val configurable_dashboard_description =
        Resource(
            "An unique and totally custom set of user interface in a form of a dashboard to be presented on the tablets.",
            "Całkowicie unikalny i personalizowany interfejs użytkownika do prezentowania w formie tablic kontrolnych na tabletach."
        )
    val field_port_hint = Resource("Port", "Port")
    val plugin_name = Resource("Tablets", "Tablety")
    val plugin_description =
        Resource(
            "A support for wall tablets running Android OS",
            "Wsparcie dla tabletów ściennych pracujących pod kontrolą systemu Android"
        )
    val n_a = Resource("n/a", "nd.")
    val category_ui = Resource("User interface", "Interfejs użytkownika")
    val category_actions = Resource("Actions", "Akcje")
    val category_layouts = Resource("Layouts", "Układ")

    val block_start_here_message = Resource("Start here", "Zacznij tutaj")
    val block_text_message = Resource("Text: %1", "Text %1")
    val block_headline_message = Resource("Headline: %1", "Nagłówek %1")
    val block_button_message = Resource("Button:  %1 action: %2", "Przycisk: %1 akcja: %2")
    val block_single_column = Resource("Single column %1 %2", "Pojedyńcza kolumna %1 %2")
}
