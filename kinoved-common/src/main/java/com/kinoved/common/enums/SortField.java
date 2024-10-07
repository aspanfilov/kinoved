package com.kinoved.common.enums;

public enum SortField {
    RATING("rating.kp"),
    DOWNLOAD_DATE("createdAt");

    private final String fieldName;

    SortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
