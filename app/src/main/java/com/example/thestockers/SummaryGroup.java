package com.example.thestockers;

import java.util.List;

public class SummaryGroup {
    private String summaryGroupName;

    private List<SummaryGroupItem> summaryGroupItems;

    public SummaryGroup(String summaryGroupName, List<SummaryGroupItem> summaryGroupItems) {
        this.summaryGroupName = summaryGroupName;
        this.summaryGroupItems = summaryGroupItems;
    }

    public String getSummaryGroupName() {
        return summaryGroupName;
    }

    public void setSummaryGroupName(String summaryGroupName) {
        this.summaryGroupName = summaryGroupName;
    }

    public List<SummaryGroupItem> getSummaryGroupItems() {
        return summaryGroupItems;
    }

    public void setSummaryGroupItems(List<SummaryGroupItem> summaryGroupItems) {
        this.summaryGroupItems = summaryGroupItems;
    }
}
