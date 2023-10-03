package io.danknil.operation.json;

import io.danknil.operation.json.stat.PurchaseInfo;

import java.util.ArrayList;

public class StatJSON {
    private final String type = "stat";
    private final int totalDays;
    private ArrayList<PurchaseInfo> purchases = new ArrayList<>();
    private int totalExpenses;
    private int averageExpenses;

    public StatJSON(int totalDays) {
        this.totalDays = totalDays;
    }

    public void addPurchase(PurchaseInfo purchaseInfo) {
        purchases.add(purchaseInfo);
    }
}
