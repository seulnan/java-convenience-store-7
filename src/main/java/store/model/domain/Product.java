package store.model.domain;

import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    private static final NumberFormat PRICE_FORMAT = NumberFormat.getInstance(Locale.KOREA);

    private final String name;
    private final int price;
    private int stock;
    private int promotionStock;
    private final Promotion promotion;

    public Product(String name, int price, int stock, int promotionStock, Promotion promotion) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.promotionStock = promotionStock;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getPromotionStock() {
        return promotionStock;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    /**
     * ✅ 재고 감소 메서드
     * - PromotionCalculator에서 프로모션 적용 여부를 판단한 후 호출해야 함.
     */
    public void reduceStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("[ERROR] 재고 부족");
        }
        stock -= quantity;
    }

    /**
     * ✅ 프로모션 재고 감소 메서드
     * - PromotionCalculator에서 프로모션을 확인한 후 호출해야 함.
     */
    public void reducePromotionStock(int quantity) {
        if (quantity > promotionStock) {
            throw new IllegalArgumentException("[ERROR] 프로모션 재고 부족");
        }
        promotionStock -= quantity;
    }

    @Override
    public String toString() {
        String formattedPrice = PRICE_FORMAT.format(price) + "원";
        String formattedStock = (stock > 0) ? stock + "개" : "재고 없음";
        String promoText = (promotion != null) ? promotion.getName() : "";

        return "- " + name + " " + formattedPrice + " " + formattedStock + (promoText.isEmpty() ? "" : " " + promoText);
    }
}