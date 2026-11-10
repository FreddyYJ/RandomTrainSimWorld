package com.github.freddyyj.randomtrainsimworld;

/**
 * Nation enum used in {@link Route} and {@link Locomotive}
 */
public enum Nation {
    USA("USA"),
    UK("UK"),
    Germany("Germany"),
    Switzerland("Swiss"),
    Japan("Japan"),
    Netherland("Netherland"),
    Austria("Austria"),
    France("France"),
    Czech("Czech"),
    Canada("Canada");

    /**
     * Name to show in the UI
     */
    private final String displayName;

    Nation(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the name used in the JSON data files (e.g. "Swiss" for {@link #Switzerland}).
     */
    @Override
    public String toString() {
        return displayName;
    }

    /**
     * Parses a nation name as written in the JSON data files.
     * Matching is case-insensitive and also accepts the enum constant name.
     *
     * @throws NationNotFoundException if the name matches no nation
     */
    public static Nation fromString(final String nation) {
        if (nation != null) {
            final String trimmed = nation.trim();
            for (final Nation n : values()) {
                if (n.displayName.equalsIgnoreCase(trimmed) || n.name().equalsIgnoreCase(trimmed)) {
                    return n;
                }
            }
        }
        throw new NationNotFoundException(nation);
    }

    /**
     * Exception when a given name is not in the enum
     */
    static class NationNotFoundException extends RuntimeException {
        private final String unknownNation;
        public NationNotFoundException(String unknownNation) {
            super("Nation " + unknownNation + " not found.");
            this.unknownNation = unknownNation;
        }

        public String getUnknownNation() {
            return unknownNation;
        }
    }
}
