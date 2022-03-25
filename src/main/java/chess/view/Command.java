package chess.view;

import java.util.Arrays;

public enum Command {

    START("start"),
    END("end"),
    MOVE("move"),
    STATUS("status");

    private static final String ERROR_MESSAGE = "잘못된 명령입니다.";

    private final String value;

    Command(final String value) {
        this.value = value;
    }

    public static void validate(final String input) {
        Arrays.stream(values())
                .filter(it -> input.startsWith(it.value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE));
    }

    public static Command splitCommand(final String text) {
        final String[] splitText = text.split(" ");
        return Arrays.stream(values())
                .filter(it -> it.value.equals(splitText[0]))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ERROR_MESSAGE));
    }

    public static String getFromPosition(final String text) {
        final String[] splitText = text.split(" ");
        if (splitText.length != 3) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
        return splitText[1];
    }

    public static String getToPosition(final String text) {
        return text.split(" ")[2];
    }
}
