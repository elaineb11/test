package com.ms_demo.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetailModel {
    private BigDecimal buy_amount;
    private String level;
    private BigDecimal rate;
    private BigDecimal sell_amount;

    public BigDecimal getBuy_amount() {
        return buy_amount;
    }

    public void setBuy_amount(BigDecimal buy_amount) {
        this.buy_amount = buy_amount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getSell_amount() {
        return sell_amount;
    }

    public void setSell_amount(BigDecimal sell_amount) {
        this.sell_amount = sell_amount;
    }

}
