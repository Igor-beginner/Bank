package md.brainet.service.bank.controller.view.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import md.brainet.service.bank.entity.Role;
import md.brainet.service.bank.validator.Password;

public record AdminPayloadOfUserCreation(

        @Size(min = 5, max = 20, message = "Username must be less then 5 symbols and more 20")
        @NotBlank(message = "username cannot be null")
        String username,

        @Password
        String password,

        @NotNull
        Role role,

        boolean enabled
) {
}
