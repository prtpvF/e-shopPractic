package ru.mihail.springcourse.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mihail.springcourse.FirstSecurityApp.services.ItemServices;
import ru.mihail.springcourse.FirstSecurityApp.services.PersonDetailsServices;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final ItemServices itemServices;
    private final PersonDetailsServices personDetailsServices;


    @Autowired
    public PersonController(ItemServices itemServices, PersonDetailsServices personDetailsServices) {
        this.itemServices = itemServices;
        this.personDetailsServices = personDetailsServices;
    }

    @RequestMapping("/hello")
    public String sayHello( Model model){
        model.addAttribute("person", personDetailsServices.forHomePage());
        return "homePage";
    }


    @GetMapping("/index/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("items", personDetailsServices.getItemByPersonId(id));
        model.addAttribute("person", personDetailsServices.findOne(id));
        return "/person/index";
    }


    @GetMapping("/item")
    public String allItem(Model model){
        model.addAttribute("items", itemServices.allItem());
        return "item/all";
    }
//    @GetMapping("/{id}/index")
//    public String index(@PathVariable("id") int id, Model model){
//        model.addAttribute("person", personDetailsServices.findOne(id));
//        return "/person/index";
//    }


}






