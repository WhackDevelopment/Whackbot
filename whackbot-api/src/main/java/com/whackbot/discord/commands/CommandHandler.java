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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * WhackBot; com.whackbot.discord.commands:CommandHandler
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 06.04.2023
 */
public class CommandHandler<T> {

    /**
     * A set of all of the commands that this {@link CommandHandler} has registered.
     *
     * @see #getCommands()
     * @since 1.0-SNAPSHOT
     */
    private final Set<AbstractCommand<T>> commands = new HashSet<>();

    /**
     * A method to register {@link AbstractCommand}s with this {@link CommandHandler}.
     *
     * @param commands The {@link AbstractCommand}s to register.
     * @see #registerCommand(AbstractCommand)
     * @since 1.0-SNAPSHOT
     */
    public void registerCommands(Set<AbstractCommand<T>> commands) {
        this.commands.addAll(commands);
    }

    /**
     * A method to register {@link AbstractCommand}s with this {@link CommandHandler}.
     *
     * @param commands The {@link AbstractCommand}s to register.
     * @see #registerCommand(AbstractCommand)
     * @see #registerCommands(Set)
     * @since 1.0-SNAPSHOT
     */
    @SafeVarargs
    public final void registerCommands(AbstractCommand<T>... commands) {
        Collections.addAll(this.commands, commands);
    }

    /**
     * A method to register a {@link AbstractCommand} with this {@link CommandHandler}.
     *
     * @param command The {@link AbstractCommand} to register.
     * @see #registerCommands(Set)
     * @since 1.0-SNAPSHOT
     */
    public void registerCommand(AbstractCommand<T> command) {
        this.registerCommands(command);
    }

    /**
     * A method to unregister {@link AbstractCommand}s with this {@link CommandHandler}.
     *
     * @param commands The commands to unregister.
     * @see #unregisterCommand(AbstractCommand)
     * @see #unregisterCommands(Set)
     * @since 1.0-SNAPSHOT
     */
    public void unregisterCommands(Set<AbstractCommand<T>> commands) {
        this.commands.removeAll(commands);
    }

    /**
     * A method to unregister {@link AbstractCommand}s with this {@link CommandHandler}.
     *
     * @param commands The commands to unregister.
     * @see #unregisterCommand(AbstractCommand)
     * @see #unregisterCommands(Set)
     * @since 1.0-SNAPSHOT
     */
    @SafeVarargs
    public final void unregisterCommands(AbstractCommand<T>... commands) {
        this.commands.removeAll(Arrays.asList(commands));
    }

    /**
     * A method to unregister a {@link AbstractCommand} with this {@link CommandHandler}.
     *
     * @param command The command to unregister.
     * @see #unregisterCommands(Set)
     * @see #unregisterCommands(AbstractCommand...)
     * @since 1.0-SNAPSHOT
     */
    public void unregisterCommand(AbstractCommand<T> command) {
        this.unregisterCommands(command);
    }

    /**
     * A method to get all of the {@link AbstractCommand}s registered with this {@link CommandHandler}
     *
     * @return All of the commands registered with this command handler.
     * @since 1.0-SNAPSHOT
     */
    public Set<AbstractCommand<T>> getCommands() {
        return commands;
    }

    /**
     * Method which attempts to find a {@link AbstractCommand} from the given trigger
     *
     * @param trigger The trigger of the command to find.
     * @return The {@link AbstractCommand} that was found, sometimes <code>null</code>
     * @since 1.0-SNAPSHOT
     */
    public AbstractCommand<T> findCommand(String trigger) {
        return commands.stream().filter(cd -> Arrays.asList(cd.getDescription().triggers(), cd.getDescription().name()).contains(trigger)).findFirst().orElse(null);
    }

    /**
     * Method which attempts to execute the given {@link AbstractCommand}.
     *
     * @param command The {@link AbstractCommand} to execute.
     * @param message The {@link T} which triggered the command.
     * @param args    The arguments of the command.
     * @since 1.0-SNAPSHOT
     */
    public void execute(AbstractCommand<T> command, T message, String... args) {
        CommandDescription cd = command.getDescription();
        if (cd == null) {
            return;
        }

        command.execute(message, args);
    }

    /**
     * A method which calls {@link #findCommand(String)}, and then {@link #execute(AbstractCommand, T, String...)} if the found {@link AbstractCommand} is not <code>null</code>
     *
     * @param trigger The trigger of the command.
     * @param message The {@link T} which triggered the command.
     * @param args    The args of the command.
     * @see #findCommand(String)
     * @see #execute(AbstractCommand, T, String...)
     * @since 1.0-SNAPSHOT
     */
    public void findAndExecute(String trigger, T message, String args) {
        AbstractCommand<T> command = this.findCommand(trigger);
        if (command == null || command.getDescription() == null) {
            return;
        }
        this.execute(command, message, args, trigger);
    }

}
