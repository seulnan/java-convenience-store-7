package store.model.dto;

public class FreeItem {
    private final String productName;
    private final int quantity;

    public FreeItem(String productName, int quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}