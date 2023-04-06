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

package com.whackbot.discord.commands.slash.global;

import com.whackbot.discord.Bot;
import com.whackbot.discord.commands.AbstractCommand;
import com.whackbot.discord.commands.CommandDescription;
import com.whackbot.discord.commands.SlasCommandDataGetter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

/**
 * WhackBot; com.whackbot.discord.commands.slash.global:GlobalHelpSlashCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 06.04.2023
 */
@CommandDescription(name = "help", triggers = {}, description = "Global Help Command")
public class GlobalHelpSlashCommand implements AbstractCommand<SlashCommandInteraction>, SlasCommandDataGetter {

    private Bot bot;

    public GlobalHelpSlashCommand(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void execute(SlashCommandInteraction interaction, String... args) {
        interaction.reply(":x: Currently there is no help.").setEphemeral(true).queue();
    }

    @Override
    public SlashCommandData data() {
        return Commands.slash(getDescription().name(), getDescription().description());
    }

    @Override
    public SlashCommandData data(String guild) {
        return data();
    }

    @Override
    public boolean global() {
        return true;
    }
}
