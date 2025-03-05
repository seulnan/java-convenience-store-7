package store.enums;

import java.util.Arrays;

public enum PromotionType {
    BUY_TWO_GET_ONE("탄산2+1"),
    MD_RECOMMENDED("MD추천상품"),
    FLASH_DISCOUNT("반짝할인"),
    NONE("null");

    private final String label;

    PromotionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static PromotionType of(String label) {
        return Arrays.stream(values())
                .filter(type -> type.label.equals(label))
                .findFirst()
                .orElse(NONE);
    }
}