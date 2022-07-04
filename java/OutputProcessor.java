import DTO.UserResult;
import Interfaces.IOutputProcessor;
import lombok.NonNull;

import java.io.PrintStream;
import java.util.stream.Stream;

public class OutputProcessor implements IOutputProcessor {
    @NonNull private final PrintStream goodPrinter;
    @NonNull private final PrintStream badPrinter;

    public OutputProcessor(@NonNull PrintStream goodPrinter, @NonNull PrintStream badPrinter) {
        this.goodPrinter = goodPrinter;
        this.badPrinter = badPrinter;
    }

    @Override
    public void processResultToOutput(@NonNull Stream<UserResult> resultStream) {
        boolean isSame = goodPrinter.equals(badPrinter);

        resultStream.sequential().forEach(u -> {
            boolean isGood = u.getIsGood();
            if (isSame) {
                String result = isGood ? "GOOD" : "BAD" + " "
                        + u.getUser().getLogin() + ":" + u.getUser().getPassword();
                goodPrinter.println(result);
            } else {
                String result = u.getUser().getLogin() + ":" + u.getUser().getPassword();
                (isGood ? goodPrinter : badPrinter).println(result);
            }
        });
    }
}
