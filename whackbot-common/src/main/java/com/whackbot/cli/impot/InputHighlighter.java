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

package com.whackbot.cli.impot;

import org.jline.reader.Highlighter;
import org.jline.reader.LineReader;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;

import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.jline.utils.AttributedStyle.*;

/**
 * WhackBot; com.whackbot.cli.impot:InputHighlighter
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 02.04.2023
 */
public class InputHighlighter implements Highlighter {

    private static final AttributedStyle KEYWORD_STYLE = BOLD;
    private static final AttributedStyle STRING_STYLE = DEFAULT.foreground(GREEN);
    private static final AttributedStyle NUMBER_STYLE = DEFAULT.foreground(CYAN);
    private static final AttributedStyle COMMENT_STYLE = DEFAULT.foreground(BRIGHT).italic();
    private static final AttributedStyle ERROR_STYLE = DEFAULT.foreground(RED);

    private static final Set<String> KEYWORDS = Arrays.asList("exit", "help", "quit", "clear").stream().collect(Collectors.toSet());

    private static boolean isKeyword(String text) {
        return KEYWORDS.contains(text.toLowerCase());
    }

    @Override
    public AttributedString highlight(LineReader reader, String buffer) {
        AttributedStringBuilder builder = new AttributedStringBuilder();

        String args[] = buffer.toLowerCase().split("\\s+");
        for (String word : args) {
            if (isKeyword(word)) {
                builder.styled(KEYWORD_STYLE, word);
            } else {
                builder.append(word);
            }
        }

        return builder.toAttributedString();
    }

    // MANY TODO 's ty ty XD

    @Override
    public void setErrorPattern(Pattern pattern) {
    }

    @Override
    public void setErrorIndex(int i) {
    }


}

