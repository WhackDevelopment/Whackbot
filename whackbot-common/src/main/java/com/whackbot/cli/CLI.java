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
import com.whackbot.cli.commands.CLICommandExecutor;
import com.whackbot.cli.commands.CLICommandMap;
import com.whackbot.cli.commands.defaults.ClearCommand;
import com.whackbot.cli.impot.Completion;
import com.whackbot.logging.LogConstants;
import com.whackbot.logging.TerminalColor;
import lombok.Getter;
import lombok.Setter;
import org.jline.reader.*;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.AttributedString;
import org.jline.utils.InfoCmp;
import org.jline.widget.AutosuggestionWidgets;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static org.jline.reader.LineReader.*;
import static org.jline.reader.LineReader.Option.HISTORY_IGNORE_SPACE;
import static org.jline.reader.LineReader.Option.HISTORY_TIMESTAMPED;
import static org.jline.utils.AttributedStyle.BRIGHT;
import static org.jline.utils.AttributedStyle.DEFAULT;

/**
 * WhackBot; com.whackbot.cli:CLI
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 02.04.2023
 */
public class CLI {

    @Getter
    private static final Logger logger;
    @Getter
    private static CLI instance;
    private static Thread cliThread;
    private static Thread clearPromptThread;
    private static int exitCode = -1;
    @Getter
    private static Terminal terminal;
    @Getter
    private static LineReader lineReader;
    @Getter
    private static CLICommandMap commandMap;

    static {
        logger = Logger.getLogger(CLI.class.getName());
    }

    @Setter
    boolean shouldClose = false;
    @Getter
    private WhackBot whackBot;


    public CLI(WhackBot whackBot) {
        this.whackBot = whackBot;

        commandMap = new CLICommandMap(
                new ClearCommand()
        );

        if (cliThread != null) {
            if (!cliThread.isInterrupted()) {
                cliThread.interrupt();
            }
            cliThread = null;
        }
        cliThread = new Thread(() -> listen(), "CLI");
        cliThread.start();

        clearPromptThread = new Thread(() -> {
            new Timer("PromptClearTimer").scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        lineReader.getTerminal().puts(InfoCmp.Capability.carriage_return);
                        lineReader.callWidget(LineReader.REDRAW_LINE);
                        lineReader.callWidget(LineReader.REDISPLAY);
                        lineReader.getTerminal().writer().flush();
                    } catch (Exception e) {
                    }
                }
            }, 1, 100);
        }, "PromptClearTimer");
        // clearPromptThread.start();

    }

    public static boolean exec(String command, List<String> args) {
        CLICommandExecutor cliCommand = commandMap.get(command);
        if (cliCommand != null) {
            try {
                return cliCommand.executeCommand(command, args);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static boolean isRealTerminal(Terminal terminal) {
        return !Terminal.TYPE_DUMB.equals(terminal.getType()) && !Terminal.TYPE_DUMB_COLOR.equals(terminal.getType());
    }

    public static boolean isRealTerminal() {
        return isRealTerminal(terminal);
    }

    public static Charset terminalEncoding() {
        return terminal.encoding();
    }

    private static String colored(String value) {
        return new AttributedString(value, DEFAULT.foreground(BRIGHT)).toAnsi();
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

    public void listen() {
        try {
            terminal = TerminalBuilder.builder().name("WhackBot").color(true).encoding("UTF-8").build();
            lineReader = LineReaderBuilder.builder().terminal(terminal).completer(Completion.commandCompleter()).variable(HISTORY_FILE, Path.of(whackBot.getCurrentWorkingDir().toPath().toAbsolutePath() + "/.terminal_history")).variable(
                    SECONDARY_PROMPT_PATTERN,
                    isRealTerminal() ? colored("%P -> ") : ""
            ).variable(BLINK_MATCHING_PAREN, 0).option(HISTORY_IGNORE_SPACE, false).build();
            lineReader.getKeyMaps().put(MAIN, lineReader.getKeyMaps().get(EditingMode.VIINS.getKeyMap()));
            lineReader.unsetOpt(HISTORY_TIMESTAMPED);
            AutosuggestionWidgets autosuggestionWidgets = new AutosuggestionWidgets(lineReader);
            autosuggestionWidgets.enable();


            // this.setOutputStream(lineReader.getTerminal().output());
            // SurvivalDiscordBotLoader.getLogger().addHandler(this);

            info("CLI listening for commands. Type '/help' or '?' to get help.");
            while (!shouldClose) {
                String line = null;
                try {
                    line = readLine("%P -> ", "");
                    String args[] = line.split("\\s+");
                    if (args.length > 0) {
                        List<String> argsList = new ArrayList<>();
                        String command = args[0];
                        for (int i = 1; i < args.length; i++) {
                            argsList.add(args[i]);
                        }
                        try {
                            if (!exec(command, argsList)) {
                                info("Type 'help' or '?' to get help.");
                            }
                        } catch (Exception ex) {
                            info("Command Error in Command: '" + command + "'");
                            ex.printStackTrace();
                        }
                    }
                    lineReader.getBuffer().down();
                    lineReader.getTerminal().flush();
                } catch (UserInterruptException e) {
                    Runtime.getRuntime().exit(0);
                } catch (EndOfFileException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readLine(String prompt, String buffer) {
        return lineReader.readLine(colored(prompt), null, buffer);
    }

    public int disable() {
        exitCode = 0;
        if (cliThread != null) {
            exitCode = cliThread.getState().hashCode();
            if (!cliThread.isInterrupted()) {
                cliThread.interrupt();
            }
            cliThread = null;
        }

        if (clearPromptThread != null) {
            if (!clearPromptThread.isInterrupted()) {
                clearPromptThread.interrupt();
            }
            clearPromptThread = null;
        }

        return exitCode;
    }

    public History getHistory() {
        return lineReader.getHistory();
    }

    public enum EditingMode {
        EMACS(LineReader.EMACS),
        VIOPP(LineReader.VIOPP),
        VIINS(LineReader.VIINS),
        VISUAL(LineReader.VISUAL),
        MAIN(LineReader.MAIN),
        SAFE(LineReader.SAFE),
        MENU(LineReader.MENU);

        private final String keyMap;

        EditingMode(String keyMap) {
            this.keyMap = keyMap;
        }

        public String getKeyMap() {
            return keyMap;
        }
    }

}
