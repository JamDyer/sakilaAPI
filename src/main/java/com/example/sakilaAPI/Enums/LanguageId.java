package com.example.sakilaAPI.Enums;

public enum LanguageId {

    ENGLISH(1),
    ITALIAN(2),
    JAPANESE(3),
    MANDARIN(4),
    FRENCH(5),
    GERMAN(6);

    private final int value;

    LanguageId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static LanguageId fromValue(int value) {
        for (LanguageId id : LanguageId.values()) {
            if (id.getValue() == value) {
                return id;
            }
        }
        throw new IllegalArgumentException("Invalid language ID: " + value);
    }
}
