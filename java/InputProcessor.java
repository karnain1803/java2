import DTO.UserQuery;
import Interfaces.IInputProcessor;
import lombok.NonNull;

import java.util.Scanner;
import java.util.stream.Stream;

public class InputProcessor implements IInputProcessor {
    @NonNull private final Scanner input;


    public void InputProxy(@NonNull Scanner input){

        


    }

    public InputProcessor(@NonNull Scanner input) {
        this.input = input;
    }

    @Override
    @NonNull public Stream<UserQuery> getProcessedInputStream() {
        return input.useDelimiter("\n").tokens().map(s -> {
            String[] split = s.split(":", 2);
            if (split.length < 2) throw new RuntimeException("Bad format of Login:Password! Line: " + s);
            return new UserQuery(split[0], split[1]);
        });
    }
}
