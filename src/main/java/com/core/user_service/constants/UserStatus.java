package com.core.user_service.constants;

public enum UserStatus {
    ACTIVE("true"),
    DISABLED("false");

    private final String isActive;

    UserStatus(String isActive) {
        this.isActive = isActive;
    }

    public String getIsActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return isActive;
    }
}
