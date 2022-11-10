package ru.mihail.springcourse.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mihail.springcourse.FirstSecurityApp.DAO.ItemDAO;
import ru.mihail.springcourse.FirstSecurityApp.models.Item;
import ru.mihail.springcourse.FirstSecurityApp.models.Person;
import ru.mihail.springcourse.FirstSecurityApp.services.ItemServices;
import ru.mihail.springcourse.FirstSecurityApp.services.PersonDetailsServices;

import javax.validation.Valid;

@Controller
@RequestMapping("item")
public class ItemController {
    private final ItemServices itemServices;
    private final PersonDetailsServices personDetailsServices;
    private final ItemDAO itemDAO;

    @Autowired
    public ItemController(ItemServices itemServices, PersonDetailsServices personDetailsServices, ItemDAO itemDAO) {
        this.itemServices = itemServices;
        this.personDetailsServices = personDetailsServices;
        this.itemDAO = itemDAO;
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
        return "redirect:/item/show";
    }





}
