package ru.mihail.springcourse.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.mihail.springcourse.FirstSecurityApp.models.Person;
import ru.mihail.springcourse.FirstSecurityApp.services.ItemServices;
import ru.mihail.springcourse.FirstSecurityApp.services.PersonDetailsServices;

@Controller
@RequestMapping("item")
public class ItemController {
    private final ItemServices itemServices;
    private final PersonDetailsServices personDetailsServices;


    @Autowired
    public ItemController(ItemServices itemServices, PersonDetailsServices personDetailsServices) {
        this.itemServices = itemServices;
        this.personDetailsServices = personDetailsServices;

    }

    @GetMapping("/show")
    public String showAll(Model model) {
        model.addAttribute("items", itemServices.findAll());
        return "/item/show";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        model.addAttribute("items", itemServices.findOne(id));
        return "/item/index";
    }

    @PatchMapping("/{id}/buy")
    public String buy(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        System.out.println("вызываем метод");
        itemServices.buy(id, person);
        System.out.println("вызвали успешно");
        return "redirect:/person/item";
    }
    @PatchMapping("/{id}/release")
    public String deleteItemFromSHopList(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        itemServices.deleteFromShopList(id,person);
        return "redirect:/person/hello";

    }





}
