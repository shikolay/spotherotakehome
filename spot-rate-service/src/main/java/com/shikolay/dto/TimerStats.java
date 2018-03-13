package com.shikolay.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimerStats {
    private double mean;
    private double median;
    private double stdDev;

    public TimerStats(double mean, double median, double stdDev) {
        this.mean = mean;
        this.median = median;
        this.stdDev = stdDev;
    }

    public TimerStats() {
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public double getStdDev() {
        return stdDev;
    }
}
