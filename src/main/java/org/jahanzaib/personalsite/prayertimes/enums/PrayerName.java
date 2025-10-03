package org.jahanzaib.personalsite.prayertimes.enums;

public enum PrayerName {
    FAJR("Fajr"),
    SUNRISE("Sunrise"),
    DHUHR("Zuhr"),
    JUMAH("Jum'ah"),
    ASR("Asr"),
    MAGHRIB("Maghrib"),
    ISHA("Isha");

    private final String name;

    PrayerName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
