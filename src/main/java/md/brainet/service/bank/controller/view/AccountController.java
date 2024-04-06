package md.brainet.service.bank.controller.view;

import lombok.AllArgsConstructor;
import md.brainet.service.bank.controller.view.payload.NewAccountPayload;
import md.brainet.service.bank.controller.view.payload.TransferMoneyPayload;
import md.brainet.service.bank.entity.User;
import md.brainet.service.bank.service.AccountService;
import md.brainet.service.bank.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AccountController {

    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/transfer")
    public String getTransferPage(Model model,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("accounts", userService.find(userDetails.getUsername()).getAccounts());
        return "transfer-page";
    }

    @GetMapping("/accounts")
    public String getPageAccounts(@AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        model.addAttribute("accounts", userService.findByUsername(userDetails.getUsername()).getAccounts());
        return "accounts";
    }

    @GetMapping("/account/{accountId}")
    @PreAuthorize("@accountService.findAccountBy(#id).owner.username == principal.username")
    public String getPageOfConcreteAccount(@PathVariable("accountId") Integer id,
                                  Model model) {
        model.addAttribute("account", accountService.findAccountBy(id));
        return "account-view-page";
    }

    @PostMapping("/constructor/account")
    public String createNewAccount(@AuthenticationPrincipal UserDetails userDetails,
                                   NewAccountPayload newAccountPayload) {
        User user = userService.find(userDetails.getUsername());
        accountService.saveNewAccount(newAccountPayload, user);
        return "redirect:/accounts";
    }

    @PostMapping("/transfer")
    public String transferMoney(TransferMoneyPayload payload) {
        accountService.transfer(payload);
        return "redirect:/account/" + payload.sender();
    }
}
