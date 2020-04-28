package org.topj.methods.Model;

import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;

public class Proposal {
    private String proposalId;
    private String parameter;
    private String origValue;
    private String newValue;
    private String modificationDescription;
    private String proposalClientAddress;
    private BigInteger deposit;
    private BigInteger chainTimerHeight;
    private String updateType;
    private BigInteger priority;

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.stringToBytes(proposalId)
                .stringToBytes(parameter)
                .stringToBytes(origValue)
                .stringToBytes(newValue)
                .stringToBytes(modificationDescription)
                .stringToBytes(proposalClientAddress)
                .BigIntToBytes(deposit, 64)
                .BigIntToBytes(chainTimerHeight, 64)
                .stringToBytes(updateType)
                .BigIntToBytes(priority, 16);
        return bufferUtils.pack();
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getOrigValue() {
        return origValue;
    }

    public void setOrigValue(String origValue) {
        this.origValue = origValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getModificationDescription() {
        return modificationDescription;
    }

    public void setModificationDescription(String modificationDescription) {
        this.modificationDescription = modificationDescription;
    }

    public String getProposalClientAddress() {
        return proposalClientAddress;
    }

    public void setProposalClientAddress(String proposalClientAddress) {
        this.proposalClientAddress = proposalClientAddress;
    }

    public BigInteger getDeposit() {
        return deposit;
    }

    public void setDeposit(BigInteger deposit) {
        this.deposit = deposit;
    }

    public BigInteger getChainTimerHeight() {
        return chainTimerHeight;
    }

    public void setChainTimerHeight(BigInteger chainTimerHeight) {
        this.chainTimerHeight = chainTimerHeight;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }

    public BigInteger getPriority() {
        return priority;
    }

    public void setPriority(BigInteger priority) {
        this.priority = priority;
    }
}
