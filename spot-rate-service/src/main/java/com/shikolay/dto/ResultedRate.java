package com.shikolay.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultedRate {
    private Long rate;

    public ResultedRate() {
    }


    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }
}
