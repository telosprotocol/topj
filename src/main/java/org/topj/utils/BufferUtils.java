package org.topj.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BufferUtils {
    private List<byte[]> bl = new ArrayList<>();
    private Integer _offset = 0;

    public BufferUtils byteToBytes(byte b) {
        byte[] ret = new byte[1];
        ret[0] = b;
        bl.add(ret);
        _offset += ret.length;
        return this;
    }

    /**
     * int32 to bytes
     * @param x x
     * @return bytes
     */
    public BufferUtils int32ToBytes(int x) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(x);
        byte[] result = buffer.array();
        bl.add(result);
        _offset += result.length;
        return this;
    }

    /**
     * int64 通过lang转成两个int32
     * @param x long args
     * @return this
     */
    public BufferUtils longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putLong(x);
        byte[] result = buffer.array();
        bl.add(result);
        _offset += result.length;
        return this;
    }

    /**
     * JAVA中long就是64位，直接使用上面longToBytes
     * @deprecated 直接使用上面longToBytes
     * @param x x
     * @return bytes
     */
    @Deprecated
    public BufferUtils int64ToBytes(long x){
        int lowWord = (int) (x & 0x000000ffffffffL);
        int highWord = (int) (x >> 32);
        byte[] lowResult = IntToBytes.intToBytes(lowWord);
        byte[] highResult = IntToBytes.intToBytes(highWord);
        byte[] lastResult = byteMergerAll(lowResult, highResult);
        bl.add(lastResult);
        _offset += 8;
        return this;
    }

    /**
     * short to bytes
     * @param x x
     * @return bytes
     */
    public BufferUtils shortToBytes(short x) {
        byte[] ret = new byte[2];
        ret[0] = (byte)(x & 0xff);
        ret[1] = (byte)((x >> 8) & 0xff);
        bl.add(ret);
        _offset += ret.length;
        return this;
    }

    /**
     * bytes to bytes, do nothing
     * @param ba bytes
     * @return bytes
     */
    public BufferUtils bytesArray(byte[] ba) {
        bl.add(ba);
        _offset += ba.length;
        return this;
    }

    /**
     * 前2个字节表示字符串长度
     * @param str string args
     * @return this
     */
    public BufferUtils stringToBytes(String str) {
        try {
            byte[] strBytes =  str.getBytes("UTF-8");
            byte[] ret = IntToBytes.intToBytes(strBytes.length);

            byte[] result = byteMergerAll(ret, strBytes);
            bl.add(result);
            _offset += result.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

    public BufferUtils boolToBytes(Boolean b) {
        byte[] ret = new byte[2];
        ret[0] = b ? (byte)0x01 : (byte)0x00;
        bl.add(ret);
        _offset += ret.length;
        return this;
    }

    /**
     * hex to bytes 小序字节转换
     * @param hex hex args
     * @return this
     */
    public BufferUtils hexToBytes(String hex) {
        int m = 0, n = 0;
        if (hex.length() % 2 == 1) {
            hex = "0" + hex;
        }
        int byteLen = hex.length() / 2;
        byte[] ret = new byte[byteLen];
        for (int i = byteLen - 1; i >= 0; i--) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[byteLen - 1 - i] = Byte.valueOf((byte)intVal);
        }

        ByteBuffer buffer = ByteBuffer.allocate(ret.length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(ret);
        byte[] result = buffer.array();
        bl.add(result);
        _offset += result.length;
        return this;
    }

    public BufferUtils mapToBytes(Map<String, BigInteger> voteInfo) {
        if (voteInfo == null) {
            return this;
        }
        this.int32ToBytes(voteInfo.size());
        voteInfo.forEach((key, value) -> {
            this.stringToBytes(key);
            this.BigIntToBytes(value, 64);
        });
        return this;
    }

    public BufferUtils BigIntToBytes(BigInteger val, Integer byteLen) {
        List<Integer> lenList = Arrays.asList(new Integer[]{8, 16, 32, 64});
        if (!lenList.contains(byteLen)) {
            throw new Error("not support byte length");
        }
        byte[] vb = val.toByteArray();
        byte[] b = new byte[byteLen / 8];
        int minLen = Math.min(vb.length, b.length);
        for (int i = 0; i < minLen; i++) {
            b[i] = vb[vb.length - i - 1];
        }
        bl.add(b);
        _offset += b.length;
        return this;
    }

    /**
     * 拼裝bytes
     * @return bytes
     */
    public byte[] pack(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(_offset);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        for (byte[] ba : bl) {
            byteBuffer.put(ba);
        }
        return byteBuffer.array();
    }

    /**
     * 合併bytes
     * @param values bytes array
     * @return bytes
     */
    public byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (int i = 0; i < values.length; i++) {
            length_byte += values[i].length;
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (int i = 0; i < values.length; i++) {
            byte[] b = values[i];
            System.arraycopy(b, 0, all_byte, countLength, b.length);
            countLength += b.length;
        }
        return all_byte;
    }
}
