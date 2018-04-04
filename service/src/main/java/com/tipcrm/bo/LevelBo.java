package com.tipcrm.bo;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.tipcrm.dao.entity.Level;
import org.springframework.util.CollectionUtils;

public class LevelBo {
    private Integer id;

    private String name;

    private BigDecimal defaultPayment;

    private String entryUser;

    private Date entryTime;

    public static List<LevelBo> toLevelBos(List<Level> levels) {
        if (!CollectionUtils.isEmpty(levels)) {
            return levels.stream().map(level -> toLevelBo(level)).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public static LevelBo toLevelBo(Level level) {
        if (level != null) {
            LevelBo levelBo = new LevelBo();
            levelBo.setId(level.getId());
            levelBo.setName(level.getDisplayName());
            levelBo.setDefaultPayment(level.getDefaultPaymentPercent());
            levelBo.setEntryUser(level.getEntryUser().getUserName());
            levelBo.setEntryTime(level.getEntryTime());
            return levelBo;
        }
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getDefaultPayment() {
        return defaultPayment;
    }

    public void setDefaultPayment(BigDecimal defaultPayment) {
        this.defaultPayment = defaultPayment;
    }

    public String getEntryUser() {
        return entryUser;
    }

    public void setEntryUser(String entryUser) {
        this.entryUser = entryUser;
    }

    public Date getEntryTime() {
        return entryTime == null ? null : (Date) entryTime.clone();
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime == null ? null : (Date) entryTime.clone();
    }
}
