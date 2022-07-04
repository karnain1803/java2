package Interfaces;

import DTO.UserResult;
import lombok.NonNull;

import java.util.stream.Stream;

public interface IOutputProcessor {
    void processResultToOutput(@NonNull Stream<UserResult> resultStream);
}
