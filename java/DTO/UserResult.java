package DTO;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserResult {
    @NonNull private UserQuery user;
    @NonNull private Boolean isGood;
}
