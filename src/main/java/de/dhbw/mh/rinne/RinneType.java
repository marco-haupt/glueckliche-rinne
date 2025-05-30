package de.dhbw.mh.rinne;

import java.util.Objects;

public enum RinneType {

    GANZZAHL("Ganzzahl"), FLIEßZAHL("Fließzahl"), SCHNUR("Schnur"), WAHRHEITSWERT("Wahrheitswert");

    public final String keyword;

    private RinneType(String keyword) {
        this.keyword = keyword;
    }

    public static RinneType from(String keyword) {
        Objects.requireNonNull(keyword, "Keyword must not be null");
        for (RinneType type : values()) {
            if (type.keyword.equals(keyword)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown RinneType keyword: " + keyword);
    }

    @Override
    public String toString() {
        return keyword;
    }

}
