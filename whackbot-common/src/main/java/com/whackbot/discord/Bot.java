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
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * WhackBot; com.whackbot.discord:Bot
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
public class Bot extends ListenerAdapter {

    private WhackBot whackBot;

    @Getter
    private Map<Integer, JDA> shards = new HashMap<>();

    private Thread botThread;

    public Bot(WhackBot whackBot) {
        this.whackBot = whackBot;

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

    public void enable() throws LoginException, InterruptedException {

        JDABuilder builder = JDABuilder.createDefault(
                whackBot.getBotConfig().get().getToken(),
                Arrays.asList(GatewayIntent.values())
        );

        builder.setLargeThreshold(10000);
        builder.setChunkingFilter(ChunkingFilter.NONE);

        builder.addEventListeners(this);

        int shards = this.whackBot.getBotConfig().get().getShards();
        for (int i = 0; i < shards; i++) {
            JDA shardJDA = builder.useSharding(i, shards).build();
            shardJDA.getPresence().setActivity(Activity.of(Activity.ActivityType.LISTENING, "Command /help "));
            this.shards.put(i, shardJDA);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.shards.values().forEach(shard -> shard.shutdownNow());
        }));
    }

    private void setStatus(Activity activity) {
        // TODO: ???
    }

    @Override
    public void onReady(@NotNull final ReadyEvent event) {
        WhackBot.info("[Bot Login] successful. User: " + event.getJDA().getSelfUser().getAsTag() + " Shard: " + event.getJDA().getShardInfo().getShardId());
    }

}
