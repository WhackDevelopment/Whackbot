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

package com.whackbot.logging;

/**
 * WhackBot; com.whackbot.logging:TerminalColor
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WhackUtils; com.whackdevelopment.utils.terminal:TerminalColor
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 30.03.2023
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public enum TerminalColor {
    // Normal colors
    BLACK("\033[0;30m"),
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    YELLOW("\033[0;33m"),
    BLUE("\033[0;34m"),
    PURPLE("\033[0;35m"),
    CYAN("\033[0;36m"),
    WHITE("\033[0;37m"),

    // Lighter colors
    LIGHT_BLACK("\033[0;90m"),
    LIGHT_RED("\033[0;91m"),
    LIGHT_GREEN("\033[0;92m"),
    LIGHT_YELLOW("\033[0;93m"),
    LIGHT_BLUE("\033[0;94m"),
    LIGHT_PURPLE("\033[0;95m"),
    LIGHT_CYAN("\033[0;96m"),
    LIGHT_WHITE("\033[0;97m"),

    // Darker colors
    DARK_BLACK("\033[0;30;1m"),
    DARK_RED("\033[0;31;1m"),
    DARK_GREEN("\033[0;32;1m"),
    DARK_YELLOW("\033[0;33;1m"),
    DARK_BLUE("\033[0;34;1m"),
    DARK_PURPLE("\033[0;35;1m"),
    DARK_CYAN("\033[0;36;1m"),
    DARK_WHITE("\033[0;37;1m"),

    // Formatting options
    RESET("\u001B[0m"),
    BOLD("\u001B[1m"),
    DIM("\u001B[2m"),
    UNDERLINED("\u001B[4m"),
    BLINK("\u001B[5m"),
    REVERSE("\u001B[7m"),
    HIDDEN("\u001B[8m");

    @Getter
    private final String value;

    public static TerminalColor fromString(String colorName) {
        for (TerminalColor color : TerminalColor.values()) {
            if (color.toString().equalsIgnoreCase(colorName)) {
                return color;
            }
        }
        throw new IllegalArgumentException("Invalid color name: " + colorName);
    }

    public static String getColors() {
        StringBuilder builder = new StringBuilder();
        for (TerminalColor color : TerminalColor.values()) {
            builder.append(color.toString().toLowerCase()).append(", ");
        }
        return builder.substring(0, builder.length() - 2);
    }

    public static String wrapBlack(String input) {
        return BLACK.wrap(input);
    }

    public static String wrapRed(String input) {
        return RED.wrap(input);
    }

    public static String wrapGreen(String input) {
        return GREEN.wrap(input);
    }

    public static String wrapYellow(String input) {
        return YELLOW.wrap(input);
    }

    public static String wrapBlue(String input) {
        return BLUE.wrap(input);
    }

    public static String wrapPurple(String input) {
        return PURPLE.wrap(input);
    }

    public static String wrapCyan(String input) {
        return CYAN.wrap(input);
    }

    public static String wrapWhite(String input) {
        return WHITE.wrap(input);
    }

    public static String wrapLightBlack(String input) {
        return LIGHT_BLACK.wrap(input);
    }

    public static String wrapLightRed(String input) {
        return LIGHT_RED.wrap(input);
    }

    public static String wrapLightGreen(String input) {
        return LIGHT_GREEN.wrap(input);
    }

    public static String wrapLightYellow(String input) {
        return LIGHT_YELLOW.wrap(input);
    }

    public static String wrapLightBlue(String input) {
        return LIGHT_BLUE.wrap(input);
    }

    public static String wrapLightPurple(String input) {
        return LIGHT_PURPLE.wrap(input);
    }

    public static String wrapLightCyan(String input) {
        return LIGHT_CYAN.wrap(input);
    }

    public static String wrapLightWhite(String input) {
        return LIGHT_WHITE.wrap(input);
    }

    public static String wrapDarkBlack(String input) {
        return DARK_BLACK.wrap(input);
    }

    public static String wrapDarkRed(String input) {
        return DARK_RED.wrap(input);
    }

    public static String wrapDarkGreen(String input) {
        return DARK_GREEN.wrap(input);
    }

    public static String wrapDarkYellow(String input) {
        return DARK_YELLOW.wrap(input);
    }

    public static String wrapDarkBlue(String input) {
        return DARK_BLUE.wrap(input);
    }

    public static String wrapDarkPurple(String input) {
        return DARK_PURPLE.wrap(input);
    }

    public static String wrapDarkCyan(String input) {
        return DARK_CYAN.wrap(input);
    }

    public static String wrapDarkWhite(String input) {
        return DARK_WHITE.wrap(input);
    }

    public static String wrapBold(String input) {
        return BOLD.wrap(input);
    }

    public static String wrapDim(String input) {
        return DIM.wrap(input);
    }

    public static String wrapUnderlined(String input) {
        return UNDERLINED.wrap(input);
    }

    public static String wrapBlink(String input) {
        return BLINK.wrap(input);
    }

    public static String wrapReverse(String input) {
        return REVERSE.wrap(input);
    }

    public static String wrapHidden(String input) {
        return HIDDEN.wrap(input);
    }

    public static TerminalColor black() {
        return BLACK;
    }

    public static TerminalColor red() {
        return RED;
    }

    public static TerminalColor green() {
        return GREEN;
    }

    public static TerminalColor yellow() {
        return YELLOW;
    }

    public static TerminalColor blue() {
        return BLUE;
    }

    public static TerminalColor purple() {
        return PURPLE;
    }

    public static TerminalColor cyan() {
        return CYAN;
    }

    public static TerminalColor white() {
        return WHITE;
    }

    public static TerminalColor lightBlack() {
        return LIGHT_BLACK;
    }

    public static TerminalColor lightRed() {
        return LIGHT_RED;
    }

    public static TerminalColor lightGreen() {
        return LIGHT_GREEN;
    }

    public static TerminalColor lightYellow() {
        return LIGHT_YELLOW;
    }

    public static TerminalColor lightBlue() {
        return LIGHT_BLUE;
    }

    public static TerminalColor lightPurple() {
        return LIGHT_PURPLE;
    }

    public static TerminalColor lightCyan() {
        return LIGHT_CYAN;
    }

    public static TerminalColor lightWhite() {
        return LIGHT_WHITE;
    }

    public static TerminalColor darkBlack() {
        return DARK_BLACK;
    }

    public static TerminalColor darkRed() {
        return DARK_RED;
    }

    public static TerminalColor darkGreen() {
        return DARK_GREEN;
    }

    public static TerminalColor darkYellow() {
        return DARK_YELLOW;
    }

    public static TerminalColor darkBlue() {
        return DARK_BLUE;
    }

    public static TerminalColor darkPurple() {
        return DARK_PURPLE;
    }

    public static TerminalColor darkCyan() {
        return DARK_CYAN;
    }

    public static TerminalColor darkWhite() {
        return DARK_WHITE;
    }

    // Formatting
    public static TerminalColor reset() {
        return RESET;
    }

    public static TerminalColor bold() {
        return BOLD;
    }

    public static TerminalColor dim() {
        return DIM;
    }

    public static TerminalColor underlined() {
        return UNDERLINED;
    }

    public static TerminalColor blink() {
        return BLINK;
    }

    public static TerminalColor reverse() {
        return REVERSE;
    }

    public static TerminalColor hidden() {
        return HIDDEN;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public String wrap(String input) {
        return this + input + TerminalColor.RESET;
    }

}
