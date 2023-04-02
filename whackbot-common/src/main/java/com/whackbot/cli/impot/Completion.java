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

package com.whackbot.cli.impot;

import com.whackbot.cli.CLI;
import com.whackbot.cli.commands.CLICommandExecutor;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * WhackBot; com.whackbot.cli.impot:Completion
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 02.04.2023
 */
public class Completion {

    public static Completer commandCompleter() {
        return new AggregateCompleter(buildCommands(CLI.getCommandMap().values()));
    }

    private static Completer buildCommands(Collection<CLICommandExecutor> execs) {
        if (execs == null) {
            return NullCompleter.INSTANCE;
        }
        List<Completer> completers = new ArrayList<>();

        for (CLICommandExecutor cmd : execs) {
            List<CLICommandExecutor> subs = cmd.subcommands();
            Completer subsCompleter = buildCommands(subs);
            if (cmd.aliases() != null) {
                completers.add(buildArgumentCompleter(cmd.name(), cmd.aliases()));
            } else {
                completers.add(buildArgumentCompleter(cmd.name()));
            }
        }
        return new AggregateCompleter(completers);
    }

    private static Completer buildArgumentCompleter(String command) {
        // NullCompleter is used to indicate the command is complete and the last word should not be repeated
        return new ArgumentCompleter(new StringsCompleter(command), NullCompleter.INSTANCE);
    }

    private static Completer buildArgumentCompleter(String command, List<String> options) {
        return new ArgumentCompleter(new StringsCompleter(command), new StringsCompleter(options), NullCompleter.INSTANCE);
    }

}
