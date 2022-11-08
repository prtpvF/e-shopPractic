package ru.mihail.springcourse.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mihail.springcourse.FirstSecurityApp.models.Item;
import ru.mihail.springcourse.FirstSecurityApp.services.AdminServices;
import ru.mihail.springcourse.FirstSecurityApp.services.ItemServices;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminServices adminServices;
    private final ItemServices itemServices;

    @Autowired
    public AdminController(AdminServices adminServices, ItemServices itemServices) {
        this.adminServices = adminServices;
        this.itemServices = itemServices;
    }

    @GetMapping
    public String sayHello() {
        return "hello";
    }


    @GetMapping("/show")
    public String showAll(Model model) {
        model.addAttribute("persons", adminServices.showAll());
        return "/admin/userPage";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("item", itemServices.findOne(id));
        return "admin/edit";
    }

    @PatchMapping("/{id}/item")
    public String update(@ModelAttribute("item") Item item , BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "admin/edit";
        adminServices.update(id, item);
        return "redirect:/admin/all";
    }



    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        adminServices.delete(id);
        return "redirect:/admin/all";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", adminServices.findOne(id));
        return "/admin/index";


    }

    @GetMapping("/new")
    public String newItem(@ModelAttribute("item") Item Item) {
        return "admin/new";
    }

    @GetMapping("/all")
    public String allItem(Model model) {
        model.addAttribute("items", adminServices.showAllItem());
        return "admin/items";
    }


    @PostMapping()
    public String createItem(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/new";
        }
        adminServices.create(item);
        return "redirect:admin/all";
    }



    @GetMapping("/{id}/item")
    public String oneItem(@PathVariable("id") int id, Model model){
        model.addAttribute("item", adminServices.findOneItem(id));
        return "/admin/itemIndex";
    }



    }
