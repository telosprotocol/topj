package org.topnetwork.methods.Model;

import org.topnetwork.utils.IntToBytes;
import org.topnetwork.utils.StringUtils;

public class TransferActionParam {
    private String coinType;
    private Long amount;

    public TransferActionParam (){}

    public TransferActionParam(String coinType, Long amount){
        this.coinType = coinType;
        this.amount = amount;
    }

    public void decode(String actionParamHex){
        if (actionParamHex == null || actionParamHex == ""){
            return;
        }
        actionParamHex = actionParamHex.replaceFirst("0x", "");
        byte[] paramBytes = StringUtils.hexToByte(actionParamHex);

        int bIndex = 0;

        // coinType
        int coinTypeLength = IntToBytes.bytesToInt(paramBytes, bIndex);
        byte[] coinTypeBytes = splitBytes(paramBytes, bIndex + 4, bIndex + coinTypeLength + 4);
        if (coinTypeBytes == null){
            this.coinType = "";
        } else {
            this.coinType = new String(coinTypeBytes);
        }
        bIndex = bIndex + coinTypeLength + 4;

        this.amount = longFrom8Bytes(paramBytes, bIndex, true);
        bIndex = bIndex + 8;

        // note
//        int noteLength = IntToBytes.bytesToInt(paramBytes, bIndex);
//        byte[] noteBytes = splitBytes(paramBytes, bIndex + 4, bIndex + noteLength + 4);
//        if (noteBytes == null){
//            this.note = "";
//        } else {
//            this.note = new String(noteBytes);
//        }
    }

    private long longFrom8Bytes(byte[] input, int offset, boolean littleEndian){
        long value=0;
        for(int  count=0;count<8;++count){
            int shift=(littleEndian?count:(7-count))<<3;
            value |=((long)0xff<< shift) & ((long)input[offset+count] << shift);
        }
        return value;
    }

    private byte[] splitBytes(byte[] bytes, int beginIndex, int endIndex){
        int length = bytes.length;
        if (beginIndex == 0 && endIndex == length){
            return bytes;
        }
        if(beginIndex < 0 || endIndex > length || endIndex <= beginIndex){
            return null;
        }
        byte[] subBytes = new byte[endIndex - beginIndex];
        for(int i=beginIndex;i<endIndex;i++){
            subBytes[i-beginIndex] = bytes[i];
        }
        return subBytes;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
