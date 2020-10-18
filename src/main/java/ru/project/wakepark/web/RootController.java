package ru.project.wakepark.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.project.wakepark.util.ClientUtil;

@Controller
public class RootController {

    @GetMapping("/")
    public String root() {
        return "redirect:main";
    }

    @GetMapping("/clients")
    public String getClientsPage(Model model) {
        model.addAttribute("thead", ClientUtil.getTableName());
        model.addAttribute("authUser", SecurityUtil.get().getUserTo());
        return "clients";
    }

    @GetMapping("/tickets")
    public String getTicketsPage(Model model) {
        model.addAttribute("authUser", SecurityUtil.get().getUserTo());
        return "tickets";
    }

    @GetMapping("/main")
    public String getMainPage(Model model) {
        model.addAttribute("authUser", SecurityUtil.get().getUserTo());
        return "main";
    }

    @GetMapping("/users")
    public String getUserPage(Model model) {
        model.addAttribute("authUser", SecurityUtil.get().getUserTo());
        return "users";
    }

    @GetMapping("/queue/screen")
    public String getQueueScreen(Model model) {
        return "screen";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

}
