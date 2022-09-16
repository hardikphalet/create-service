package com.trips.create_service;

import com.trips.create_service.parser.Parser;
import com.trips.create_service.parser.Tokens;
import com.trips.create_service.tasks.Generate;
import com.trips.create_service.tasks.Hydrate;

public class Application {
    public static void main(String[] args) {

        Tokens t = Parser.parse(args).getToken();
        switch (t) {
            case GENERATE:
                System.out.println(Parser.parse(args).getData());
                Generate.builder().data(Parser.parse(args).getData()).build().execute();
                break;
            case HYDRATE:
                Hydrate.execute();
                break;
            case HELP:
                break;
        }

    }
}
