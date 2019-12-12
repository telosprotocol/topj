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
package org.topj.methods.property;

import java.math.BigInteger;

public class XActionType {
    public final static BigInteger AssertOut = new BigInteger("0");
    public final static BigInteger SourceNull = new BigInteger("1");
    public final static BigInteger CreateUserAccount = new BigInteger("2");
    public final static BigInteger CreateConstractAccount = new BigInteger("3");
    public final static BigInteger DeployConstract = new BigInteger("4");
    public final static BigInteger RunConstract = new BigInteger("5");
    public final static BigInteger AssetIn = new BigInteger("6");
    public final static BigInteger PropertyOp = new BigInteger("7");
    public final static BigInteger AliasName = new BigInteger("8");
    public final static BigInteger GlobalName = new BigInteger("9");
    public final static BigInteger JoinRequest = new BigInteger("11");
    public final static BigInteger SetAccountKeys = new BigInteger("12");
    public final static BigInteger LockToken = new BigInteger("13");
    public final static BigInteger UnlockToken = new BigInteger("14");
    public final static BigInteger GetConsensusRandom = new BigInteger("16");
}