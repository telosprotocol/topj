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

public class XTransactionType {
    public final static BigInteger CreateUserAccount = new BigInteger("0");
    public final static BigInteger CreateContractAccount = new BigInteger("1");
    public final static BigInteger DeployContract = new BigInteger("2");
    public final static BigInteger RunContract = new BigInteger("3");
    public final static BigInteger Transfer = new BigInteger("4");
    public final static BigInteger PropertyOp = new BigInteger("5");
    public final static BigInteger AliasName = new BigInteger("6");
    public final static BigInteger GlobalName = new BigInteger("7");
    public final static BigInteger FreeJoinRequest = new BigInteger("8");
    public final static BigInteger GlobalOp = new BigInteger("9");
    public final static BigInteger GlobalOpReverse = new BigInteger("10");
    public final static BigInteger SetAccountKeys = new BigInteger("11");
    public final static BigInteger LockToken = new BigInteger("12");
    public final static BigInteger UnlockToken = new BigInteger("13");
    public final static BigInteger GetConsensusRandom = new BigInteger("15");

    public final static BigInteger CreateChildAccount = new BigInteger("16");
    public final static BigInteger DestroyAccount = new BigInteger("17");

    /**
     * 获得投票
     */
    public final static BigInteger GetVote = new BigInteger("18");

    /**
     * 返还投票
     */
    public final static BigInteger ReturnVote = new BigInteger("19");

    /**
     * 发起投票
     */
    public final static BigInteger Vote = new BigInteger("20");

    /**
     * 放弃投票
     */
    public final static BigInteger AbolishVote = new BigInteger("21");

//    public final static BigInteger Registration = 22;

    public final static BigInteger PledgeTokenTgas = new BigInteger("22");

    public final static BigInteger RedeemTokenTgas = new BigInteger("23");

    public final static BigInteger PledgeTokenDisk = new BigInteger("24");

    public final static BigInteger RedeemTokenDisk = new BigInteger("25");

    public final static BigInteger UnfreezeResource = new BigInteger("26");

    public final static BigInteger UpdatePledgeContract = new BigInteger("27");
}
