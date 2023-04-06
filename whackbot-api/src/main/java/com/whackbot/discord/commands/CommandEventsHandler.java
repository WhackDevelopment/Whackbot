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

import com.whackbot.discord.DiscordBot;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

/**
 * WhackBot; com.whackbot.discord.commands:CommandEventsHandler
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 06.04.2023
 */
public class CommandEventsHandler extends ListenerAdapter {

    private final DiscordBot bot;
    private boolean debug = false;

    public CommandEventsHandler(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
        bot.executors().submit(() -> {
            // COMPLETELY IGNORE OTHER BOTS
            if (event.getMessage().getAuthor().isBot()) {
                return;
            }
            // IGNORE PRIVATE MSGs/CMDs
            if (!event.getMessage().isFromGuild()) {
                return;
            }

            String content = event.getMessage().getContentRaw();
            if (content == null || content == "") {
                return;
            }
            if (!content.startsWith(bot.getPrefix())) {
                return;
            }
            String firstWord = content.split("\\s+")[0].replaceFirst(bot.getPrefix(), "");
            try {
                bot.getMessageCommandHandler().findAndExecute(firstWord, event.getMessage(), content.replaceFirst(bot.getPrefix() + firstWord, "").trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onSlashCommandInteraction(@NotNull final SlashCommandInteractionEvent event) {
        new Thread(() -> {
            if (debug) {
                System.out.println(
                        "onSlashCommandInteraction(user=User(" +
                                "name=" + event.getUser().getName() + ", id=" + event.getUser().getId() + "), " +
                                "interacted=Interaction(type=slash, id=" + event.getCommandId() + "))"
                );
            }
            try {
                bot.getSlashCommandInteractionCommandHandler().findAndExecute(event.getName(), event.getInteraction(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "slash-command-" + event.getInteraction().getCommandId()).start();

    }

    @Override
    public void onButtonInteraction(@NotNull final ButtonInteractionEvent event) {
        bot.executors().submit(() -> {
            if (debug) {
                System.out.println(
                        "onButtonInteraction(user=User(" +
                                "name=" + event.getUser().getName() + ", id=" + event.getUser().getId() + "), " +
                                "interacted=Interaction(type=button, id=" + event.getButton().getId() + "))"
                );
            }
            try {
                bot.getButtonInteractionCommandHandler().findAndExecute(event.getButton().getId(), event.getInteraction(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onModalInteraction(@NotNull final ModalInteractionEvent event) {
        bot.executors().submit(() -> {
            if (debug) {
                System.out.println(
                        "onModalInteraction(user=User(" +
                                "name=" + event.getUser().getName() + ", id=" + event.getUser().getId() + "), " +
                                "interacted=Interaction(type=modal, id=" + event.getModalId() + "))"
                );
            }
            try {
                bot.getModalInteractionCommandHandler().findAndExecute(event.getModalId(), event.getInteraction(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        bot.executors().submit(() -> {
            if (debug) {
                System.out.println(
                        "onStringSelectInteraction(user=User(" +
                                "name=" + event.getUser().getName() + ", id=" + event.getUser().getId() + "), " +
                                "interacted=Interaction(type=string-select, id=" + event.getInteraction().getComponentId() + "))"
                );
            }
            try {
                bot.getSelectMenuInteractionCommandHandler().findAndExecute(event.getInteraction().getComponentId(), event.getInteraction(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        bot.executors().submit(() -> {
            if (debug) {
                System.out.println(
                        "onEntitySelectInteraction(user=User(" +
                                "name=" + event.getUser().getName() + ", id=" + event.getUser().getId() + "), " +
                                "interacted=Interaction(type=entity-select, id=" + event.getInteraction().getComponentId() + "))"
                );
            }
            try {
                bot.getEntitySelectMenuInteractionCommandHandler().findAndExecute(event.getInteraction().getComponentId(), event.getInteraction(), "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {

    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {

    }

}
