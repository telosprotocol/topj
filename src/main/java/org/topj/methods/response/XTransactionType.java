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

public class XTransactionType {
    public final static Short CreateUserAccount = 0;
    public final static Short CreateContractAccount =1;
    public final static Short DeployContract = 2;
    public final static Short RunContract = 3;
    public final static Short Transfer = 4;
    public final static Short PropertyOp = 5;
    public final static Short AliasName = 6;
    public final static Short GlobalName = 7;
    public final static Short FreeJoinRequest = 8;
    public final static Short GlobalOp = 9;
    public final static Short GlobalOpReverse = 10;
    public final static Short SetAccountKeys = 11;
    public final static Short LockToken = 12;
    public final static Short UnlockToken = 13;
    public final static Short GetConsensusRandom = 15;
    public final static Short CreateChildAccount = 16;
}
