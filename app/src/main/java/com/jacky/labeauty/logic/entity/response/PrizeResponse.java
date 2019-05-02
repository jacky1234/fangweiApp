package com.jacky.labeauty.logic.entity.response;

import com.jacky.labeauty.logic.entity.module.Prize;
import com.jacky.labeauty.logic.entity.module.PrizeLog;

import java.util.List;

public class PrizeResponse {
    /**
     * "prizes": [ // 奖品列表
     * {
     * "id": "5cc2854b2329468b8cfb5598",
     * "createTime": 1556251980000,
     * "updateTime": 1556259148000,
     * "name": "谢谢参与", // 奖品名称
     * "thumb": "", // 奖品图片
     * "targetId": "5caed8472329462c9037a7c3",
     * "targetType": "EMPTY" // 奖品类型。EMPTY：未中奖，GIFT：实物，COUPON：优惠券
     * }
     * ],
     * "prizeIndex": 0 // 中奖序号
     */

    private List<Prize> prizes;
    private int prizeIndex;
    private PrizeLog prizeLog;

    public List<Prize> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<Prize> prizes) {
        this.prizes = prizes;
    }

    public int getPrizeIndex() {
        return prizeIndex;
    }

    public void setPrizeIndex(int prizeIndex) {
        this.prizeIndex = prizeIndex;
    }

    public PrizeLog getPrizeLog() {
        return prizeLog;
    }

    public void setPrizeLog(PrizeLog prizeLog) {
        this.prizeLog = prizeLog;
    }
}
