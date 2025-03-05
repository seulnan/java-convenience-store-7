package store.model.dto;

public class PurchaseItem {
    private final String productName;
    private final int quantity;
    private final int totalPrice;

    public PurchaseItem(String productName, int quantity, int totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}