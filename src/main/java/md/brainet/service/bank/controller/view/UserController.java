package md.brainet.service.bank.controller.view;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import md.brainet.service.bank.controller.view.payload.AdminPayloadOfUserCreation;
import md.brainet.service.bank.controller.view.payload.NewUserPayload;
import md.brainet.service.bank.controller.view.payload.UpdateUserPayload;
import md.brainet.service.bank.entity.Role;
import md.brainet.service.bank.entity.User;
import md.brainet.service.bank.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/profile")
    public String saveProfileChanges(UpdateUserPayload updateUserPayload,
                                     @AuthenticationPrincipal
                                     UserDetails userDetails,
                                     Model model) {
        userService.edit(updateUserPayload, userDetails.getUsername());
        model.addAttribute("user", userDetails);
        return "profile-page";
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editPage(@PathVariable("userId") int userId, Model model) {
        model.addAttribute("user", userService.find(userId));
        model.addAttribute("roles", Role.values());
        return "admin-edit-page";
    }

    @PostMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editUser(@Validated(Default.class)
                               AdminPayloadOfUserCreation payload,
                           @P("n") @RequestParam("oldUsername") String oldUsername,
                           @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.find(oldUsername);
        if(Role.valueOf(userDetails
                .getAuthorities()
                .iterator()
                .next())
                .getPriority() <= user.getRole().getPriority()) {
            throw new NotAccessException();
        }


        userService.edit(payload, oldUsername);
        return "redirect:/users";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "all-users";
    }

    @PostMapping("/constructor/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String createUser(@Valid AdminPayloadOfUserCreation payload) {
        userService.saveNewUser(payload.username(),
                payload.username(),
                payload.role());
        return "redirect:/users";
    }

    @PostMapping("/user")
    public String saveNewUser(@Valid NewUserPayload payload, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Invalid credentinals");
        }
        userService.saveNewUser(payload.username(), payload.password());
        return "/login";
    }
}
