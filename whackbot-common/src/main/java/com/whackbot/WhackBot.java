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

package com.whackbot;

import com.whackbot.cli.WhackCLI;
import com.whackbot.config.*;
import com.whackbot.discord.Bot;
import com.whackbot.entity.Uptimer;
import com.whackbot.logging.LogConstants;
import com.whackbot.logging.TerminalColor;
import com.whackbot.resource.ResourceReader;
import com.whackbot.zipping.Zipper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * WhackBot; com.whackbot:WhackBot
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
public class WhackBot {

    public static final List<String> OWNERS = List.of("427212136134213644", "801860642402467850");
    @Getter
    private static final Logger logger;
    @Setter
    static boolean shouldClose = false;
    @Getter
    private static WhackBot instance;

    static {
        logger = Logger.getLogger(WhackBot.class.getName());
    }

    @Getter
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2000);
    @Getter
    private File libsDir;
    @Getter
    private File currentWorkingDir;
    @Getter
    private File configPath;
    @Getter
    private Thread worker;
    @Getter
    private WhackCLI whackCLI;
    @Getter
    private JsonConfig<BotConfig> botConfig;
    @Getter
    private JsonConfig<DatabaseConfig> databaseConfig;

    @Getter
    private Bot bot;

    @Getter
    private Uptimer uptimer;

    public WhackBot(File libs, File cwd) {
        instance = this;

        try {
            this.setupLogging();
        } catch (Exception e) {
            e.printStackTrace();
        }

        info("INITIALIZING WhackBot");


        this.libsDir = libs;
        this.currentWorkingDir = cwd;
        this.configPath = new File(cwd, "configs/");
        if (!this.configPath.exists()) {
            this.configPath.mkdirs();
        }

        this.worker = new Thread(createRunner(), "WhackBot");

        new Timer("ShouldClose-Check").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (shouldClose) {
                    instance.stop();
                }
            }
        }, 1, 1000);
    }

    public static void log(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.info(TerminalColor.wrapBlue(LogConstants.WHACK) + " " + TerminalColor.wrapWhite(String.format(format, args)));
    }

    public static void info(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.info(TerminalColor.wrapBlue(LogConstants.WHACK) + " " + TerminalColor.wrapGreen(String.format(format, args)));
    }

    public static void debug(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.severe(TerminalColor.wrapBlue(LogConstants.WHACK) + " " + TerminalColor.wrapCyan(String.format(format, args)));
    }

    public static void warn(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.warning(TerminalColor.wrapBlue(LogConstants.WHACK) + " " + TerminalColor.wrapYellow(String.format(format, args)));
    }

    public static void blink(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.warning(TerminalColor.wrapBlue(LogConstants.WHACK) + " " + TerminalColor.wrapBlink(TerminalColor.wrapWhite(String.format(format, args))));
    }

    public boolean isAlive() {
        return this.worker.isAlive();
    }

    public void start() {
        shouldClose = false;
        this.uptimer = new Uptimer();
        this.uptimer.start();
        this.worker.start();
    }

    public void stop() {
        this.worker.interrupt();
        this.uptimer.shutdown();
        this.whackCLI.stop();
        this.worker = null;
        this.uptimer = null;
        shouldClose = false;
    }

    public void restart() {
        this.stop();
        this.start();
    }

    private Runnable createRunner() {
        return () -> {
            try {
                info("STARTING WhackBot");
                this.setupConfigs();
                this.whackCLI = new WhackCLI(this.getLibsDir(), this.getCurrentWorkingDir(), this);
                this.whackCLI.start();
                this.bot = new Bot(this);
                info("Started WhackBot successfully.\n" + ResourceReader.contentTrimmed("/banner.txt", "utf-8") + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private void setupConfigs() {
        try {
            File botConfigFile = new File(configPath, "bot.json");
            this.botConfig = new JsonConfig<>(BotConfig.class, botConfigFile);
            this.botConfig.setDefault(BotConfig.class, new DefaultBotConfig());
            this.botConfig.load(true);
            this.botConfig.save(false);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            File databaseConfigFile = new File(configPath, "database.json");
            this.databaseConfig = new JsonConfig<>(DatabaseConfig.class, databaseConfigFile);
            this.databaseConfig.setDefault(DatabaseConfig.class, new DefaultDatabaseConfig());
            this.databaseConfig.load(true);
            this.databaseConfig.save(false);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void setupLogging() throws IOException {
        File loggerPath = new File(this.currentWorkingDir, "logs/");
        if (!loggerPath.exists()) {
            loggerPath.mkdirs();
        }
        String fl = loggerPath.toPath().toAbsolutePath() + "/latest.log";
        File logFile = new File(fl);
        if (logFile.exists()) {
            Zipper.compressGzip(logFile.toPath(), Path.of(loggerPath.toPath().toAbsolutePath() + "/log." + loggerPath.listFiles().length + ".log.gz"));
        }
        FileHandler fileHandler = new FileHandler(fl, 50000, 1);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);
        log("Logger File: '" + fl + "'");
    }

}
