package md.brainet.service.bank.controller.view;

import md.brainet.service.bank.entity.AccountType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class PageController {

    @GetMapping("/")
    public String getMainPage() {
        return "index";
    }

    @GetMapping("/profile")
    public String getUserProfileInfo(@AuthenticationPrincipal
                                     @ModelAttribute(name = "user")
                                     UserDetails user) {
        return "profile-page";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration";
    }

    @GetMapping("/constructor/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String getConstructorPage() {
        return "create-admin-page";
    }

    @GetMapping("/new/account")
    public String getCreateAccountPage(Model model) {
        model.addAttribute("accountTypes", AccountType.values());
        return "account-create-page";
    }
}
