/*
 * Copyright (c) 2023 - present | LuciferMorningstarDev <contact@lucifer-morningstar.xyz>
 * Copyright (c) 2023 - present | whackdevelopment.com <contact@whackdevelopment.com>
 * Copyright (c) 2023 - present | whackdevelopment.com team and contributors
 * Copyright (c) 2023 - present | whackbot.com <contact@whackbot.com>
 * Copyright (c) 2023 - present | whackbot.com team and contributors
 *
 * ██╗    ██╗██╗  ██╗ █████╗  ██████╗██╗  ██╗██████╗  ██████╗ ████████╗
 * ██║    ██║██║  ██║██╔══██╗██╔════╝██║ ██╔╝██╔══██╗██╔═══██╗╚══██╔══╝
 * ██║ █╗ ██║███████║███████║██║     █████╔╝ ██████╔╝██║   ██║   ██║
 * ██║███╗██║██╔══██║██╔══██║██║     ██╔═██╗ ██╔══██╗██║   ██║   ██║
 * ╚███╔███╔╝██║  ██║██║  ██║╚██████╗██║  ██╗██████╔╝╚██████╔╝   ██║
 *  ╚══╝╚══╝ ╚═╝  ╚═╝╚═╝  ╚═╝ ╚═════╝╚═╝  ╚═╝╚═════╝  ╚═════╝    ╚═╝
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.whackbot.localization;

import com.whackbot.resource.ResourceReader;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * WhackBot; com.whackbot.localization:LocalizedMessage
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
public enum LocalizedMessage {
    LANGUAGE_NAME("name"),
    LANGUAGE_KEY("key"),

    COMMANDS_SLASH_PING_SUCCESS("commands.slash.ping.success");

    private final static String FOLDER = "/localization/";
    private final static String FILENAME = "messages.properties";
    private static Properties fallback;
    private static Map<String, Properties> languages = new HashMap<>();
    @Getter
    private final String key;

    LocalizedMessage(String key) {
        this.key = key;
    }

    public String getLocalizedMessage(Locale locale, Object... obj) {
        String lang = locale.getKey();
        if (fallback == null) {
            fallback = new Properties();
            try {
                fallback.load(ResourceReader.stream(FOLDER + "en/" + FILENAME));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!languages.containsKey(locale.getKey())) {
            Properties prop = new Properties();
            try {
                prop.load(ResourceReader.stream(FOLDER + locale.getKey() + "/" + FILENAME));
                languages.put(locale.getKey(), prop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String form = String.valueOf(languages.getOrDefault(locale.getKey(), fallback).get(this.getKey()));
        return String.format(form, obj);
    }

    public String getLocalizedMessage(Locale locale) {
        return getLocalizedMessage(locale, "undefined");
    }

}
