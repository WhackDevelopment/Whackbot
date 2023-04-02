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

package com.whackbot.launcher;

import com.whackbot.Agent;
import com.whackbot.WhackBotLoader;
import com.whackbot.dependencies.DependencyInit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * WhackBot; com.whackbot.launcher:Bootstrap
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Bootstrap {

    @Getter
    private static final Logger logger;
    private static WhackBotLoader loader;

    // private static WhackBotWebLoader webLoader;

    static {
        logger = Logger.getLogger(Bootstrap.class.getName());
    }

    public static void main(String[] args) {
        if (loader != null) throw new RuntimeException("WhackBot can ony be initialized once.");

        logger.severe("LOADING WhackBot DEPENDENCIES...");
        for (String depend : DependencyInit.getDependencies()) {
            try {
                Agent.getAgent().load(depend);
            } catch (IOException e) {
                logger.warning("COULD NOT LOD DEPENDENCY: " + depend);
                e.printStackTrace();
            }
        }
        logger.severe("WhackBot DEPENDENCIES LOADED...");
        try {
            Path currentRelativePath = Paths.get("");
            loader = WhackBotLoader.INSTANCE;
            // webLoader = WhackBotWebLoader.INSTANCE;
            new Thread(() -> loader.start(Agent.getLibDir(), new File(currentRelativePath.toAbsolutePath().toString()))).start();
            // new Thread(() -> webLoader.start(Agent.getLibDir(), new File(currentRelativePath.toAbsolutePath().toString()))).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (loader != null) loader.stop();
            // if(webLoader != null) webLoader.stop();
            loader = null;
            // webLoader = null;
        }));

    }

}
