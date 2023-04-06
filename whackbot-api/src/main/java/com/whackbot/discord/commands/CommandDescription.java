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

import java.lang.annotation.*;

/**
 * WhackBot; com.whackbot.discord.commands:CommandDescription
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 06.04.2023
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CommandDescription {

    /**
     * This represents the name of the {@link AbstractCommand} which is annotated with this {@link CommandDescription}
     *
     * @return The name of the command, should never be <code>null</code>.
     * @since 1.0-SNAPSHOT
     */
    String name();

    /**
     * This represents the description of the {@link AbstractCommand} which is annotated with this {@link CommandDescription} has.
     *
     * @return The description of the command, default as an empty string.
     * @since 1.0-SNAPSHOT
     */
    String description() default "";

    /**
     * This represents an array of Strings which could trigger the {@link AbstractCommand} which is annotated with this {@link CommandDescription} has.
     *
     * @return An Array of Strings which could trigger the command.
     * @since 1.0-SNAPSHOT
     */
    String[] triggers();

    /**
     * This represents an Array of {@link CommandAttribute} that the {@link AbstractCommand} which is annotated with this {@link CommandDescription} has.
     *
     * @return An array of {@link CommandAttribute}s, will return an empty array if no attributes are used.
     * @since 1.0-SNAPSHOT
     */
    CommandAttribute[] attributes() default {};

}
