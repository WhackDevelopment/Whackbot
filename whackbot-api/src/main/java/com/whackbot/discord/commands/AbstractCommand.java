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

package com.whackbot.discord.commands;

import java.util.Arrays;

/**
 * WhackBot; com.whackbot.discord.commands:AbstractCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 06.04.2023
 */
public interface AbstractCommand<T> extends Comparable<AbstractCommand<T>> {

    /**
     * Execute this command.
     *
     * @param object T is the object that is passed to this command
     * @param args   The arguments from the command
     * @since 1.1.3
     */
    void execute(T object, String... args);

    /**
     * Get the {@link CommandDescription} that annotates this {@link AbstractCommand}
     *
     * @return The {@link CommandDescription}
     * @since 1.0-SNAPSHOT
     */
    default CommandDescription getDescription() {
        return getClass().getAnnotation(CommandDescription.class);
    }

    /**
     * Return the {@link CommandAttribute}s that are contained within the {@link CommandDescription} annotator
     *
     * @return The {@link CommandAttribute}s
     * @since 1.0-SNAPSHOT
     */
    default CommandAttribute[] getAttributes() {
        return getDescription().attributes();
    }

    /**
     * Returns if the {@link CommandDescription} contains a {@link CommandAttribute} with the given key.
     *
     * @param key The key to check the {@link CommandAttribute} against.
     * @return If the relevant {@link CommandAttribute} exists
     * @since 1.0-SNAPSHOT
     */
    default boolean hasAttribute(String key) {
        return Arrays.stream(getAttributes()).anyMatch(ca -> ca.key().equals(key));
    }

    /**
     * Returns the {@link String} that the given {@link CommandAttribute} contains.
     *
     * @param key The key to get the {@link CommandAttribute} of.
     * @return The relevant {@link String}
     * @since 1.0-SNAPSHOT
     */
    default String getAttribute(String key) {
        return Arrays.stream(getAttributes()).filter(ca -> ca.key().equals(key)).findFirst().map(CommandAttribute::value).orElse(null);
    }

    @Override
    default int compareTo(AbstractCommand<T> that) {
        return this.getDescription().name().compareTo(that.getDescription().name());
    }

}
