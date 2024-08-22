package com.kinoved.common.enums.kinopoisk;

public enum KinopoiskImageType {
    STILL("still"),
    SHOOTING("shooting"),
    BACKDROPS("backdrops"),
    COVER("cover"),
    FRAME("frame"),
    PROMO("promo"),
    SCREENSHOT("screenshot"),
    WALLPAPER("wallpaper");

    private final String type;

    KinopoiskImageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
