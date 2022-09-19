package com.trips.create_service.parser;

import org.apache.commons.cli.*;

public class Parser {

    public static final Options options = new Options()
            .addOption(getGenerateOption())
            .addOption(getHydrateOption())
            .addOption(getHelpOption());

    public static CommandWrapper parse(String[] arguments) {
        CommandLineParser parser = new DefaultParser();
        HelpFormatter helpFormatter = new HelpFormatter();

        try {
            CommandLine line = parser.parse(Parser.options, arguments);
            if(line.hasOption("generate")) {
                return new CommandWrapper(Tokens.GENERATE,line.getOptionValue("generate"));
            }
            if(line.hasOption("hydrate")) {
                return new CommandWrapper(Tokens.HYDRATE,"");
            }
            if(line.hasOption("help")) {
                helpFormatter.printHelp("sfc", Parser.options);
                return new CommandWrapper(Tokens.HELP,"");
            }
            else {
                System.out.println(arguments + " is not a valid input.");
                helpFormatter.printHelp("sfc", Parser.options);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helpFormatter.printHelp("sfc", Parser.options);
        }
        return new CommandWrapper(Tokens.HELP,"");
    }

    private static Option getGenerateOption() {
        return Option.builder("generate").desc("Used to generate a skeleton for service framework")
                .hasArg()
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
