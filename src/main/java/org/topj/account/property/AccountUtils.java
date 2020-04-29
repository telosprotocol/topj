package org.topj.account.property;

public class AccountUtils {

    public static Integer makeLedgerId(ChainId chainId, ZoneIndex zoneIndex) {
        Integer ledgerId = chainId.getValue() << 4;
        ledgerId |= zoneIndex.getValue() & 0xF;
        return ledgerId;
    }

//    public static Integer makeSubAddrOfLedger()
}
