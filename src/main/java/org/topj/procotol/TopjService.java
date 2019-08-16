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

package org.topj.procotol;

import org.topj.methods.response.ResponseBase;

import java.io.IOException;
import java.util.Map;

public interface TopjService {

    <T> ResponseBase<T> send(Map<String, String> args, Class<T> responseClass);

    /**
     * Closes resources used by the service.
     *
     * @throws IOException thrown if a service failed to close all resources
     */
    void close() throws IOException;
}
