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

import com.whackbot.resource.ResourceReader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * WhackBot; com.whackbot:Constants
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 01.04.2023
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final OffsetDateTime STARTUP = OffsetDateTime.now();
    public static final String SUPPORT_SERVER_ID = "1075538521340776489";

    public static final String WEBSITE_URL = "https://whackbot.com";
    public static final String API_URL = "https://api.whackbot.com";
    public static final String API_HOST = "api.whackbot.com";
    public static final String DEV_WEBSITE_URL = "https://whackdevelopment.com";
    public static final String INVITE_URL = WEBSITE_URL + "/invite";
    public static final String DONATE_URL = WEBSITE_URL + "/donate";
    public static final String SUPPOR_URLT = WEBSITE_URL + "/support";
    public static final String SUMMARY_URL = WEBSITE_URL + "/summary";
    public static final String DASHBOARD_URL = WEBSITE_URL + "/dashboard";
    public static final String TERMS_URL = WEBSITE_URL + "/terms";
    public static final String PRIVACY_URL = WEBSITE_URL + "/privacy";
    public static final String CONTACT_URL = WEBSITE_URL + "/contact";


    public static final String GITHUB_ORG_URL = "https://github.com/WhackDevelopment";
    public static final String GITHUB_REPO_URL = "https://github.com/WhackDevelopment/WhackBot";
    public static final String PAYPAL_URL = "https://paypal.me/WhackDevelopment";
    public static final String DISCORD_VANITY_URL = "https://discord.gg/WhackDevelopment";
    public static final String DISCORD_URL = "https://discord.gg/kGgMqCf29m";

    public static final String OWNER_NAME = "LuciferMorningstar#6660";

    public static final String LAUNCHER_VERSION = ResourceReader.contentTrimmed("/launcher-version.txt", "utf-8");
    public static final String API_VERSION = ResourceReader.contentTrimmed("/api-version.txt", "utf-8");
    public static final String BOT_VERSION = ResourceReader.contentTrimmed("/common-version.txt", "utf-8");

}
