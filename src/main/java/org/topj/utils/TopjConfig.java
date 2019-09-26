/*
 * Copyright 2019 Sawyer Song
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.topj.utils;

import java.io.IOException;
import java.util.Properties;

public class TopjConfig {
    private static final String VERSION = "version";
    private static final String DEPOSIT = "deposit";
    private static final String CREATE_ACCOUNT_LAST_TRANS_HASH = "createAccountLastTransHash";
    private static final String EXPIRE_DURATION = "expireDuration";

    public static String getVersion() throws IOException {
        return loadProperties().getProperty(VERSION);
    }

    public static Integer getDeposit() throws IOException {
        String depositStr = loadProperties().getProperty(DEPOSIT);
        return Integer.valueOf(depositStr);
    }

    public static String getCreateAccountLastTransHash() throws IOException {
        return loadProperties().getProperty(CREATE_ACCOUNT_LAST_TRANS_HASH);
    }

    public static Short getExpireDuration() throws IOException {
        String expireDurationStr = loadProperties().getProperty(EXPIRE_DURATION);
        return Short.valueOf(expireDurationStr);
    }

    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        properties.load(TopjConfig.class.getResourceAsStream("/topj.properties"));
        return properties;
    }
}