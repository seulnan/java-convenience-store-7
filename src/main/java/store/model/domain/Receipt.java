package store.model.domain;

import java.util.List;
import store.model.dto.FreeItem;
import store.model.dto.PurchaseItem;

public class Receipt {
    private final List<PurchaseItem> purchasedItems;
    private final List<FreeItem> freeItems;
    private final int totalAmount;
    private final int promotionDiscount;
    private final int membershipDiscount;
    private final int finalAmount;

    public Receipt(List<PurchaseItem> purchasedItems, List<FreeItem> freeItems, int totalAmount, int promotionDiscount, int membershipDiscount, int finalAmount) {
        this.purchasedItems = purchasedItems;
        this.freeItems = freeItems;
        this.totalAmount = totalAmount;
        this.promotionDiscount = promotionDiscount;
        this.membershipDiscount = membershipDiscount;
        this.finalAmount = finalAmount;
    }

    public List<PurchaseItem> getPurchasedItems() {
        return purchasedItems;
    }

    public List<FreeItem> getFreeItems() {
        return freeItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getPromotionDiscount() {
        return promotionDiscount;
    }

    public int getMembershipDiscount() {
        return membershipDiscount;
    }

    public int getFinalAmount() {
        return finalAmount;
    }
}