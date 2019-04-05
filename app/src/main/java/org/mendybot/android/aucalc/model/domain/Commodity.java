package org.mendybot.android.aucalc.model.domain;

import java.time.LocalDate;
import java.util.Date;

public class Commodity {
//    private Date date;
    private double gold;
    private double silver;
    private double platinum;

    /*
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    */

    public double getGold() {
        return gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public double getSilver() {
        return silver;
    }

    public void setSilver(double silver) {
        this.silver = silver;
    }

    public double getPlatinum() {
        return platinum;
    }

    public void setPlatinum(double platinum) {
        this.platinum = platinum;
    }
}
