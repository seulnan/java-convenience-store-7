package store.validator;

import store.exception.InvalidInputException;

public class InputValidator {
    public static void validateProductSelection(String input) {
        if (!input.matches("\\[.*-\\d+]")) {
            throw new InvalidInputException("[ERROR] 올바른 입력 형식이 아닙니다.");
        }
    }
}