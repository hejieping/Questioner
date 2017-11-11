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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrowseHistoryPK)) return false;

        BrowseHistoryPK that = (BrowseHistoryPK) o;

        if (!getUserid().equals(that.getUserid())) return false;
        return getItemid().equals(that.getItemid());
    }

    @Override
    public int hashCode() {
        int result = getUserid().hashCode();
        result = 31 * result + getItemid().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BrowseHistoryPK{" +
                "userid=" + userid +
                ", itemid=" + itemid +
                '}';
    }
}
