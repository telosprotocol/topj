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
package org.topj.methods.response;

public class XActionType {
    public final static Short AssertOut = 0;
    public final static Short SourceNull = 1;
    public final static Short CreateUserAccount = 2;
    public final static Short CreateConstractAccount = 3;
    public final static Short DeployConstract = 4;
    public final static Short RunConstract = 5;
    public final static Short AssetIn = 6;
    public final static Short PropertyOp = 7;
    public final static Short AliasName = 8;
    public final static Short GlobalName = 9;
    public final static Short JoinRequest = 11;
    public final static Short SetAccountKeys = 12;
    public final static Short LockToken = 13;
    public final static Short UnlockToken = 14;
    public final static Short GetConsensusRandom = 16;
}