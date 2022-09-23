package com.trips.create_service.parser;

import lombok.Getter;

public enum Tokens {

    GENERATE(true),
    HELP(false),
    HYDRATE(false);
    @Getter
    private final boolean dataRequired;

    Tokens(boolean dataRequired) {
        this.dataRequired = dataRequired;
    }
}
