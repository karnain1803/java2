package Interfaces;

import DTO.UserQuery;
import DTO.UserResult;
import lombok.NonNull;

import java.util.stream.Stream;

public interface IChecker {
    @NonNull Stream<UserResult> checkUsers(@NonNull Stream<UserQuery> users);
}
