package com.ms_demo.model;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RateModel {

    private String buy_currency;
    private String conversion_date;
    private String created_at;
    private String currency_pair;
    private String dealt_currency;
    private BigDecimal rate;
    private List<DetailModel>  rate_details;
    private String sell_currency;

    public String getBuy_currency() {
        return buy_currency;
    }

    public void setBuy_currency(String buy_currency) {
        this.buy_currency = buy_currency;
    }

    public String getConversion_date() {
        return conversion_date;
    }

    public void setConversion_date(String conversion_date) {
        this.conversion_date = conversion_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCurrency_pair() {
        return currency_pair;
    }

    public void setCurrency_pair(String currency_pair) {
        this.currency_pair = currency_pair;
    }

    public String getDealt_currency() {
        return dealt_currency;
    }

    public void setDealt_currency(String dealt_currency) {
        this.dealt_currency = dealt_currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<DetailModel> getRate_details() {
        return rate_details;
    }

    public void setRate_details(List<DetailModel> rate_details) {
        this.rate_details = rate_details;
    }

    public String getSell_currency() {
        return sell_currency;
    }

    public void setSell_currency(String sell_currency) {
        this.sell_currency = sell_currency;
    }

}
