package md.brainet.service.bank.controller.view.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserPayload(
        @Size(min = 5, max = 20, message = "Username must be less then 5 symbols and more 20")
        @NotNull(message = "username cannot be null")
        String username,

        @Size(min = 5, max = 20, message = "Password must be less then 5 symbols and more 20")
        @NotNull(message = "password cannot be null")
        String password
        ) {
}
