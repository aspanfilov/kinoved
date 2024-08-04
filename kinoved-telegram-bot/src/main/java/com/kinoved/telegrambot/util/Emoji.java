package com.kinoved.telegrambot.util;

public enum Emoji {
    INFO("ℹ"),
    WARNING("⚠"),
    ERROR("❌"),
    DONE("✅"),
    FOLDER("📁"),
    ARROW_UP("⬆️"),
    ARROW_DOWN("⬇️"),
    ARROW_LEFT("⬅️"),
    ARROW_RIGHT("➡️");

    private final String emoji;

    Emoji(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}
