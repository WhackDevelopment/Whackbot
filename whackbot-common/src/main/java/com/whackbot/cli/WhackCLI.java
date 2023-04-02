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

package com.whackbot.cli;

import com.whackbot.WhackBot;
import com.whackbot.logging.LogConstants;
import com.whackbot.logging.TerminalColor;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

/**
 * WhackBot; com.whackbot.cli:WhackCLI
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 02.04.2023
 */
public class WhackCLI {

    @Getter
    private static final Logger logger;
    @Setter
    static boolean shouldClose = false;
    @Getter
    private static WhackCLI instance;

    static {
        logger = Logger.getLogger(WhackBot.class.getName());
    }

    @Getter
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2000);
    @Getter
    private WhackBot whackBot;
    @Getter
    private CLI cli;
    @Getter
    private File libsDir;
    @Getter
    private File currentWorkingDir;
    @Getter
    private File configPath;
    @Getter
    private Thread worker;

    public WhackCLI(File libs, File cwd, WhackBot whack) {
        instance = this;
        whackBot = whack;

        info("INITIALIZING WhackCLI");

        this.libsDir = libs;
        this.currentWorkingDir = cwd;
        this.configPath = new File(cwd, "configs/");
        if (!this.configPath.exists()) {
            this.configPath.mkdirs();
        }

        this.worker = new Thread(createRunner(), "WhackBot");

    }

    public static void log(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.info(TerminalColor.wrapBlue(LogConstants.CLI) + " " + TerminalColor.wrapWhite(String.format(format, args)));
    }

    public static void info(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.info(TerminalColor.wrapBlue(LogConstants.CLI) + " " + TerminalColor.wrapGreen(String.format(format, args)));
    }

    public static void debug(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.severe(TerminalColor.wrapBlue(LogConstants.CLI) + " " + TerminalColor.wrapCyan(String.format(format, args)));
    }

    public static void warn(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.warning(TerminalColor.wrapBlue(LogConstants.CLI) + " " + TerminalColor.wrapYellow(String.format(format, args)));
    }

    public static void blink(String format, String... args) {
        if (logger == null) {
            return;
        }
        logger.warning(TerminalColor.wrapBlue(LogConstants.CLI) + " " + TerminalColor.wrapBlink(TerminalColor.wrapWhite(String.format(format, args))));
    }

    public boolean isAlive() {
        return this.worker.isAlive();
    }

    public void start() {
        shouldClose = false;
        this.worker.start();
    }

    public void stop() {
        this.worker.interrupt();
        this.worker = null;
        shouldClose = false;
    }

    public void restart() {
        this.stop();
        this.start();
    }

    private Runnable createRunner() {
        return () -> {
            try {
                info("STARTING WhackCLI");
                this.cli = new CLI(whackBot);
                info("Started WhackCLI successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
