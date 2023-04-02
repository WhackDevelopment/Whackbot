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

import com.whackbot.dependencies.Dependency;
import com.whackbot.logging.LogConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.jar.JarFile;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * WhackBot; com.whackbot:Agent
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Agent implements ClassFileTransformer {

    @Getter
    private static final Logger logger;
    @Getter
    private static File libDir = new File(System.getProperty("dependencyLibDir") != null ? System.getProperty("dependencyLibDir") : ".libs");
    @Getter
    private static Instrumentation instrumentation;
    @Getter
    private static Agent agent;
    private static boolean bbInit = true;

    static {
        InputStream stream = Agent.class.getClassLoader().getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger = Logger.getLogger(Agent.class.getName());
        logger.severe(LogConstants.PREMAIN + "Trying to load Agent...");
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        logger.severe(LogConstants.PREMAIN + " Loading...");
        agentmain(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        if (instrumentation != null) {
            return;
        }
        logger.severe(LogConstants.AGENT + "Loading...");
        agent = new Agent();
        instrumentation = inst;
        instrumentation.addTransformer(agent);
        bbInit = false;
        if (!libDir.exists()) {
            try {
                logger.info(LogConstants.FILES + "Creating LibsPath");
                Files.createDirectories(libDir.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean is200(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        conn.connect();
        int rs = conn.getResponseCode();
        conn.disconnect();
        return rs == 200;
    }

    private void appendJarFile(JarFile file) {
        Instrumentation instrumentation = Agent.instrumentation != null ? Agent.instrumentation : getBBInst();
        if (instrumentation != null) {
            instrumentation.appendToSystemClassLoaderSearch(file);
        } else {
            throw new ExceptionInInitializerError("Instrumentation is null");
        }
    }

    private @Nullable Instrumentation getBBInst() {
        if (bbInit) {
            // TODO: load another agent
        }
        return null;
    }

    public void load(Dependency dependency) throws IOException {
        String filePath = dependency.group().replace(".", "/") + "/" + dependency.name() + "/" + dependency.version();
        String fileName = dependency.name() + "-" + dependency.version() + ".jar";
        File folder = new File(libDir, filePath);
        File dest = new File(folder, fileName);

        if (dependency.name().equalsIgnoreCase("JDA")) {
            if (!dest.exists()) {
                logger.severe(LogConstants.AGENT + "Downloading " + dependency.toString() + " JarFile to: " + dest.getName());
                try {
                    if (!folder.exists()) {
                        Files.createDirectories(folder.toPath());
                    }
                    URL server = new URL("https://github.com/DV8FromTheWorld/JDA/releases/download/v" + dependency.version() + "/JDA-" + dependency.version() + "-withDependencies-min.jar");
                    InputStream stream = server.openStream();
                    Files.copy(stream, dest.toPath());
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (!dest.exists()) {
                logger.severe(LogConstants.AGENT + "Downloading " + dependency.toString() + " JarFile to: " + dest.getName());
                try {
                    if (!folder.exists()) {
                        Files.createDirectories(folder.toPath());
                    }
                    URL server = new URL("https://repo1.maven.org/maven2/" + filePath + "/" + fileName);
                    InputStream stream = server.openStream();
                    Files.copy(stream, dest.toPath());
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String digest = "";
        try {
            MessageDigest fileCheckDigest = MessageDigest.getInstance("MD5");
            fileCheckDigest.update(Files.readAllBytes(dest.toPath()));
            byte[] b = fileCheckDigest.digest();
            for (int i = 0; i < b.length; i++) {
                digest += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return;
        }
        logger.severe(LogConstants.AGENT + "Append JarFile " + dest.getName() + " Digest: " + digest);
        agent.appendJarFile(new JarFile(dest));
    }

    public void load(String dependencyStr) throws IOException {
        String[] splittet = dependencyStr.split(":");
        load(new Dependency(splittet[0], splittet[1], splittet[2]));
    }

}
