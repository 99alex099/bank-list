package controllers;

import dto.TotalAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import services.implementations.AccountServiceImpl;
import services.implementations.UserServiceImpl;
import services.interfaces.AccountService;
import services.interfaces.UserService;

@Controller
public class MainController {

    private final AccountService accountService;

    @Autowired
    public MainController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/richest_user")
    public String richest(Model model) {

        TotalAccount totalAccount = accountService.findMaxTotalAccountSum();

        if (totalAccount != null) {
            model.addAttribute("user", totalAccount.getOwner().toString());
            model.addAttribute("account", totalAccount.getSum());
        }

        return "richest_user";
    }
}
