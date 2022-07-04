package DTO;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserQuery {
    @NonNull private String login;
    @NonNull private String password;
}
