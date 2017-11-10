package com.sitp.questioner.entity;

import java.io.Serializable;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/11/6 下午9:13
 */
public class BrowseHistoryPK implements Serializable {
    Long userid;
    Long itemid;

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getItemid() {
        return itemid;
    }

    public void setItemid(Long itemid) {
        this.itemid = itemid;
    }
}
