package com.trips.create_service.parser;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandWrapper {
    private Tokens token;
    private String data;
}
