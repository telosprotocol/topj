package org.topj.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
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
     * @param x
     * @return
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
     * @param str
     * @return
     */
    public BufferUtils stringToBytes(String str) {
        byte[] ret = IntToBytes.intToBytes((str.length()));
        byte[] strBytes =  str.getBytes();

        byte[] result = byteMergerAll(ret, strBytes);
        bl.add(result);
        _offset += result.length;
        return this;
    }

    /**
     * hex to bytes 小序字节转换
     * @param hex
     * @return
     */
    public BufferUtils hexToBytes(String hex) {
        int m = 0, n = 0;
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

    public BufferUtils mapToBytes(Map<String, Long> voteInfo) {
        if (voteInfo == null) {
            return this;
        }
        this.int32ToBytes(voteInfo.size());
        voteInfo.forEach((key, value) -> {
            this.stringToBytes(key);
            this.longToBytes(value);
        });
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
