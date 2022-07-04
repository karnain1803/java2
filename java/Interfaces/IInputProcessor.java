package Interfaces;

import DTO.UserQuery;
import lombok.NonNull;

import java.util.stream.Stream;

public interface IInputProcessor {
    @NonNull Stream<UserQuery> getProcessedInputStream();
}
