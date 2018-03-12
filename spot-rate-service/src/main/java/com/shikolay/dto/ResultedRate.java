package com.shikolay.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultedRate {
    private String rate;

    public ResultedRate() {
    }


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
