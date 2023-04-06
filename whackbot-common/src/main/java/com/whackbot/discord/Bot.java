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

package com.whackbot.discord;

import com.whackbot.WhackBot;
import com.whackbot.discord.commands.CommandEventsHandler;
import com.whackbot.discord.commands.CommandHandler;
import com.whackbot.discord.commands.SlasCommandDataGetter;
import com.whackbot.discord.commands.slash.global.GlobalHelpSlashCommand;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.context.MessageContextInteraction;
import net.dv8tion.jda.api.interactions.commands.context.UserContextInteraction;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectInteraction;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectInteraction;
import net.dv8tion.jda.api.interactions.modals.ModalInteraction;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WhackBot; com.whackbot.discord:Bot
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
public class Bot extends ListenerAdapter implements DiscordBot {

    private WhackBot whackBot;

    @Getter
    private Map<Integer, JDA> shards = new HashMap<>();

    @Getter
    private Thread botThread;

    @Getter
    private ExecutorService executors;

    @Getter
    private List<SlashCommandData> slashCommands;
    @Getter
    private List<SlashCommandData> slashCommandsGlobal;
    @Getter
    private CommandHandler<Message> messageCommandHandler;
    @Getter
    private CommandHandler<MessageContextInteraction> messageContextInteractionCommandHandler;
    @Getter
    private CommandHandler<UserContextInteraction> userContextInteractionCommandHandler;
    @Getter
    private CommandHandler<ButtonInteraction> buttonInteractionCommandHandler;
    @Getter
    private CommandHandler<ModalInteraction> modalInteractionCommandHandler;
    @Getter
    private CommandHandler<StringSelectInteraction> selectMenuInteractionCommandHandler;
    @Getter
    private CommandHandler<EntitySelectInteraction> entitySelectMenuInteractionCommandHandler;
    @Getter
    private CommandHandler<SlashCommandInteraction> slashCommandInteractionCommandHandler;

    // TODO
    @Getter
    private CommandHandler autoCompleteInteractionCommandHandler;

    public Bot(WhackBot whackBot) {
        this.whackBot = whackBot;
        this.executors = Executors.newFixedThreadPool(64);
        this.registerCommandsAndInteractions();

        if (botThread != null) {
            if (!botThread.isInterrupted()) {
                botThread.interrupt();
            }
            botThread = null;
        }
        botThread = new Thread(() -> {
            try {
                enable();
            } catch (LoginException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "WhackBot-DiscordBot");
        botThread.start();

    }

    private void registerCommandsAndInteractions() {
        this.slashCommands = new ArrayList<>();
        this.slashCommandsGlobal = new ArrayList<>();

        this.messageCommandHandler = new CommandHandler<>();
        this.messageContextInteractionCommandHandler = new CommandHandler<>();
        this.userContextInteractionCommandHandler = new CommandHandler<>();
        this.buttonInteractionCommandHandler = new CommandHandler<>();
        this.modalInteractionCommandHandler = new CommandHandler<>();
        this.selectMenuInteractionCommandHandler = new CommandHandler<>();
        this.entitySelectMenuInteractionCommandHandler = new CommandHandler<>();
        this.slashCommandInteractionCommandHandler = new CommandHandler<>();

        this.slashCommandInteractionCommandHandler.getCommands().add(new GlobalHelpSlashCommand(this));

        this.slashCommandInteractionCommandHandler.getCommands().forEach(slashCommand -> {
            if (slashCommand instanceof SlasCommandDataGetter) {
                SlasCommandDataGetter getter = (SlasCommandDataGetter) slashCommand;
                if (getter.global()) {
                    this.slashCommandsGlobal.add(getter.data());
                } else {
                    this.slashCommands.add(getter.data());
                }
            }
        });

    }

    public void enable() throws LoginException, InterruptedException {

        JDABuilder builder = JDABuilder.createDefault(
                whackBot.getBotConfig().get().getToken(),
                Arrays.asList(GatewayIntent.values())
        );

        builder.setLargeThreshold(10000);
        builder.setChunkingFilter(ChunkingFilter.NONE);

        builder.addEventListeners(this);
        builder.addEventListeners(new CommandEventsHandler(this));

        int shards = this.whackBot.getBotConfig().get().getShards();
        for (int i = 0; i < shards; i++) {
            JDA shardJDA = builder.useSharding(i, shards).build();
            shardJDA.getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, "Command /help "));
            this.shards.put(i, shardJDA);

            shardJDA.updateCommands()
                    .addCommands(this.slashCommandsGlobal)
                    .queue();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Good Bye :(");
            executors.shutdownNow();
            this.shards.values().forEach(shard -> shard.shutdownNow());
        }));
    }

    private void setStatus(Activity activity) {
        // TODO: ???
    }

    @Override
    public void onReady(@NotNull final ReadyEvent event) {
        // TODO: add non globals

        WhackBot.info("[Bot Login] successful. User: " + event.getJDA().getSelfUser().getAsTag() + " Shard: " + event.getJDA().getShardInfo().getShardId());
    }


    @Override
    public String getPrefix() {
        return "w!"; // TODO: config
    }

    @Override
    public ExecutorService executors() {
        return executors;
    }
}
