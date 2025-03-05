package store.model.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Promotion {
    private final String name;
    private final int buyCount;
    private final int getCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buyCount, int getCount, String startDate, String endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
    }

    public String getName() {
        return name;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public int getGetCount() {
        return getCount;
    }

    /**
     * ✅ 프로모션이 현재 날짜에 유효한지 확인
     */
    public boolean isActive(LocalDate today) {
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }
}