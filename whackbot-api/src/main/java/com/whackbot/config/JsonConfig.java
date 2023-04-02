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

package com.whackbot.config;

import java.io.File;
import java.io.IOException;

/**
 * WhackBot; com.whackbot.config:JsonConfig
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
public class JsonConfig<T> {

    private final File dataFile;

    private Class<T> configurationClazz;

    private T configuration;

    /**
     * JsonConfig which can hold plain Java Objects which are GSON serializable ( needs to have Full Args Constructor AND Getter and Setter for each field )
     *
     * @param clazz    GSON serializable class
     * @param dataFile the configuration file
     */
    public JsonConfig(Class<T> clazz, File dataFile) {
        this.dataFile = dataFile;
        this.configurationClazz = clazz;
    }

    public void setDefault(Class clazz, Object defaultConfig) {
        this.configurationClazz = clazz;
        this.configuration = (T) defaultConfig;
    }

    public void load() throws IOException {
        if (dataFile.exists()) {
            configuration = ConfigLoader.loadConfig(configurationClazz, dataFile);
        }
    }

    public void load(boolean overrideDefault) throws IOException {
        if (!overrideDefault && configuration != null) {
            return;
        }
        load();
    }

    public void save() throws IOException {
        ConfigLoader.saveConfig(configuration, dataFile);
    }

    public void save(boolean overwrite) throws IOException {
        if (!overwrite && dataFile.exists()) {
            return;
        }
        save();
    }

    public T get() {
        return configuration;
    }

}