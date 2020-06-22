package ru.project.wakepark.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.project.wakepark.repository.CrudClientRepository;
import ru.project.wakepark.util.ClientUtil;

@Controller
public class RootController {

    @Autowired
    public CrudClientRepository repository;

    @GetMapping("/clients")
    public String getClientsPage(Model model) {
        model.addAttribute("thead", ClientUtil.getTableName());
        return "clients";
    }

    @GetMapping("/tickets")
    public String getTicketsPage(Model model) {
        return "tickets";
    }




}
