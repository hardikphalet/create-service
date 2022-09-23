package com.trips.create_service.parser;

import lombok.Getter;

public enum Tokens {

    GENERATE(true),
    HELP(false),
    HYDRATE(false),
    DEV(false); // TODO to remove before release,

    @Getter
    private final boolean dataRequired;

    Tokens(boolean dataRequired) {
        this.dataRequired = dataRequired;
    }
}
