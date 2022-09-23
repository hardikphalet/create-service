package com.trips.create_service.parser;

import com.trips.create_service.exceptions.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
public class Parser {
    private static final HashSet<Tokens> tokenSet = (HashSet<Tokens>) Arrays.stream(new Tokens[]{Tokens.HELP, Tokens.HYDRATE, Tokens.DEV, Tokens.GENERATE}).collect(Collectors.toSet());

    public static CommandWrapper parse(String[] arguments) {
        String providedTokenArgument = arguments[0];

        Tokens providedToken = null;

        try {
            providedToken = getTokenFromArgument(providedTokenArgument);
        } catch (InvalidTokenException e) {
            log.debug(e.getMessage());
            providedToken = Tokens.HELP;
        }
        if (providedToken.isDataRequired()) {
            return CommandWrapper.builder().token(providedToken).data(arguments[1]).build();
        }

        return CommandWrapper.builder().token(providedToken).build();
    }

    private static Tokens getTokenFromArgument(String arg) {
        if (tokenSet.stream().map(Enum::toString).collect(Collectors.toSet()).contains(arg.toUpperCase())) {
            return Enum.valueOf(Tokens.class, arg.toUpperCase());
        }
        throw new InvalidTokenException(String.format("%s is not a valid input.", arg));
    }

}
