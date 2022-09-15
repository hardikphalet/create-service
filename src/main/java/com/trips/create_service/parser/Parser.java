package com.trips.create_service.parser;

import org.apache.commons.cli.*;

public class Parser {

    public static final Options options = new Options()
            .addOption(getGenerateOption())
            .addOption(getHydrateOption())
            .addOption(getHelpOption());

    public static Tokens parse(String[] arguments) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            CommandLine line = parser.parse(Parser.options, arguments);
            if(line.hasOption("generate")) {
                return Tokens.GENERATE;
            }
            if(line.hasOption("hydrate")) {
                return Tokens.HYDRATE;
            }
            if(line.hasOption("help")) {
                helpFormatter.printHelp("sfc", Parser.options);
                return Tokens.HELP;
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("sfc", Parser.options);
        }

        return Tokens.HELP;
    }

    private static Option getGenerateOption() {
        return Option.builder("generate").desc("Used to generate a skeleton for service framework")
                .build();
    }

    private static Option getHydrateOption() {
        return Option.builder("hydrate").desc("Used to hydrate the skeleton project with required " +
                        "classes on the basis of classes present in entities package")
                .build();
    }

    private static Option getHelpOption() {
        return Option.builder("help").desc("Documentation")
                .build();
    }

}
