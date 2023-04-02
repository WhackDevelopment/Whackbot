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

package com.whackbot.cli.console;

import java.io.PrintStream;

import static com.whackbot.cli.CLI.isRealTerminal;
import static java.util.Objects.requireNonNull;

/**
 * WhackBot; com.whackbot.cli.console:ConsolePrinter
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 02.04.2023
 */
public class ConsolePrinter {

    private static final String ERASE_SCREEN_FORWARD = "\033[0J";
    private static final String ERASE_LINE_ALL = "\033[2K";

    private final PrintStream out;
    private int lines;

    public ConsolePrinter(PrintStream out) {
        this.out = requireNonNull(out, "out is null");
    }

    private static String cursorUp(int lines) {
        return "\033[" + lines + "A";
    }

    public void reprintLine(String line) {
        if (isRealTerminal()) {
            out.print(ERASE_LINE_ALL + line + "\n");
        } else {
            out.print('\r' + line);
        }
        out.flush();
        lines++;
    }

    public void repositionCursor() {
        if (lines > 0) {
            if (isRealTerminal()) {
                out.print(cursorUp(lines));
            } else {
                out.print('\r');
            }
            out.flush();
            lines = 0;
        }
    }

    public void resetScreen() {
        if (lines > 0) {
            if (isRealTerminal()) {
                out.print(cursorUp(lines) + ERASE_SCREEN_FORWARD);
            } else {
                out.print('\r');
            }
            out.flush();
            lines = 0;
        }
    }

}

