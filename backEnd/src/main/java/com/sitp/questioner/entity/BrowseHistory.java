package com.sitp.questioner.entity;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * @author <a href="mailto:jieping.hjp@cainiao.com">jieping.hjp</a>
 * @since 2017/11/6 下午9:08
 */
@Entity
@IdClass(BrowseHistoryPK.class)
public class BrowseHistory {
    @Id
    Long userid;
    @Id
    Long itemid;
    Double preference;
    Date timestamp = null;
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

    public Double getPreference() {
        return preference;
    }

    public void setPreference(Double preference) {
        this.preference = preference;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Time timestamp) {
        this.timestamp = timestamp;
    }
}
