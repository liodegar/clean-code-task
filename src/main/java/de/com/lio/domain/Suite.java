package de.com.lio.domain;

import static de.com.lio.domain.Constants.EMPTY_STRING;
import static de.com.lio.domain.Constants.NEW_LINE;

/**
 * Immutable enum that defines the only two possible page data suite (Setup, TearDown), as well as the suite attributes.
 */
public enum Suite {
    SETUP("suiteSetupName", "SetUp", "!include -setup .", EMPTY_STRING),
    TEAR_DOWN("suiteTearDownName", "TearDown", "!include -teardown .", NEW_LINE);

    private final String name;
    private final String type;
    private final String message;
    private final String initialSeparator;

    Suite(String name, String type, String message, String initialSeparator) {
        this.name = name;
        this.type = type;
        this.message = message;
        this.initialSeparator = initialSeparator;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public String getInitialSeparator() {
        return initialSeparator;
    }
}
