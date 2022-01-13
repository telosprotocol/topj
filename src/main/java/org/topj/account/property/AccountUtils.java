package org.topj.account.property;

import net.jpountz.xxhash.XXHashFactory;
import org.topj.account.Account;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;

public class AccountUtils {

    public static Integer makeLedgerId(ChainId chainId, ZoneIndex zoneIndex) {
        Integer ledgerId = chainId.getValue() << 4;
        ledgerId |= zoneIndex.getValue() & 0xF;
        return ledgerId;
    }

//    public static Integer makeSubAddrOfLedger()


    /**
     * create account by table [0-63]
     * @return account
     */
    public static Account genAccount(int table) {
        if(table <0 || table >63){
            throw new InputMismatchException("table value range [0,63]");
        }
        Account account = new Account();
        int tableId = getAddressTableId(account.getAddress());
        System.out.println("t1>>" + tableId + " a1>>" + account.getAddress());
        if(tableId != table){
            return genAccount(table);
        }
        return account;
    }

    /**
     *  address sub table
     * @param address
     * @return
     * @throws IOException
     */
    public static String getAddressTable(String address) throws IOException {
        return TopjConfig.getShardingTableBlockAddr() +"@" + getAddressTableId(address);
    }

    /**
     *  address sub tableId
     * @param address
     * @return
     */
    public static int getAddressTableId(String address){
        XXHashFactory factory = XXHashFactory.fastestInstance();
        byte[] data = new byte[0];
        try {
            data = address.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        long hash64 = factory.hash64().hash(data,0,address.length(),0);
        return (int)(hash64&0x3f);
    }

    public static void main(String[] args) {
        Account account = genAccount(0);
        System.out.println(account.getAddress());
        System.out.println(getAddressTableId(account.getAddress()));
    }
}
