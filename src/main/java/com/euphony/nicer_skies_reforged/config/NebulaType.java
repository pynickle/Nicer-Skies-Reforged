package com.euphony.nicer_skies_reforged.config;

public enum NebulaType {
    RAINBOW("Rainbow");

    private final String type;

    NebulaType(String type) {
        this.type = type;
    }

    public String getTypeString() {
        return type;
    }
}
