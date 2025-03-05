package store.utils;

import java.util.Arrays;
import store.model.domain.Product;
import store.model.domain.Promotion;
import store.enums.PromotionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class FileParser {
    public static List<Product> parseProducts(String filePath, List<Promotion> promotions) {
        try (BufferedReader reader = getBufferedReader(filePath)) {
            return reader.lines()
                    .skip(1) // 첫 번째 줄(헤더) 건너뛰기
                    .map(line -> parseProduct(line, promotions))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 읽을 수 없습니다: " + filePath, e);
        }
    }

    public static List<Promotion> parsePromotions(String filePath) {
        try (BufferedReader reader = getBufferedReader(filePath)) {
            return reader.lines()
                    .skip(1)
                    .map(FileParser::parsePromotion)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalStateException("[ERROR] 파일을 읽을 수 없습니다: " + filePath, e);
        }
    }

    private static BufferedReader getBufferedReader(String filePath) {
        ClassLoader classLoader = FileParser.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IllegalStateException("[ERROR] 리소스 파일을 찾을 수 없습니다: " + filePath);
        }

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    /**
     * ✅ `promotionStock`을 고려한 Product 객체 생성
     */
    private static Product parseProduct(String line, List<Promotion> promotions) {
        String[] parts = line.split(",");

        if (parts.length != 4) {
            throw new IllegalArgumentException("[ERROR] 상품 데이터 형식이 올바르지 않습니다: " + line);
        }

        String name = parts[0].trim();
        int price = Integer.parseInt(parts[1].trim());
        int stock = Integer.parseInt(parts[2].trim());
        String promotionName = parts[3].trim();

        System.out.println("[DEBUG] Extracting - Name: " + name + ", Price: " + price + ", Stock: " + stock+ ", Promotion: " + promotionName);

        Promotion promotion = promotions.stream()
                .filter(p -> p.getName().equals(promotionName))
                .findFirst()
                .orElse(null);

        return new Product(name, price, stock, promotion); // ✅ promotionStock 포함
    }

    private static Promotion parsePromotion(String line) {
        String[] parts = line.split(",");
        if (parts.length < 5) {
            throw new IllegalArgumentException("[ERROR] 프로모션 데이터 형식이 올바르지 않습니다.");
        }

        String name = parts[0].trim();
        int buyCount = Integer.parseInt(parts[1].trim());
        int getCount = Integer.parseInt(parts[2].trim());
        String startDate = parts[3].trim();
        String endDate = parts[4].trim();

        return new Promotion(name, buyCount, getCount, startDate, endDate);
    }
}