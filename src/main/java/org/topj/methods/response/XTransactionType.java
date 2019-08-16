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
