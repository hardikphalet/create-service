package com.trips.create_service.parser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder // Using builder as this class may grow with time.
public class CommandWrapper {
    private Tokens token;
    private String data;
}
