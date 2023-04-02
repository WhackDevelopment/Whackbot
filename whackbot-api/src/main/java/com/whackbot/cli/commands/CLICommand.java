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

package com.whackbot.cli.commands;

import java.util.Collections;
import java.util.List;

/**
 * WhackBot; com.whackbot.cli.commands:CLICommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 02.04.2023
 */
public abstract class CLICommand implements CLICommandExecutor {

    private String name;
    private List<String> aliases;

    private List<CLICommandExecutor> subcommands;

    public CLICommand(String name) {
        this.name = name;
        this.aliases = Collections.emptyList();
        this.subcommands = Collections.emptyList();
    }

    public CLICommand(String name, List<String> aliases) {
        this.name = name;
        this.aliases = aliases;
        this.subcommands = Collections.emptyList();
    }

    @Override
    public abstract boolean executeCommand(final String label, final List<String> args);

    @Override
    public List<String> aliases() {
        return aliases != null ? aliases : Collections.emptyList();
    }

    @Override
    public void aliases(final List<String> aliases) {
        this.aliases = aliases;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public List<CLICommandExecutor> subcommands() {
        return subcommands;
    }

    @Override
    public void subcommands(final List<CLICommandExecutor> subs) {
        this.subcommands = subs;
    }

}

