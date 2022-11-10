package ru.mihail.springcourse.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihail.springcourse.FirstSecurityApp.models.Item;
import ru.mihail.springcourse.FirstSecurityApp.models.Person;
import ru.mihail.springcourse.FirstSecurityApp.repositorys.ItemRepository;
import ru.mihail.springcourse.FirstSecurityApp.security.PersonDetails;


import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ItemServices {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemServices(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll(){
        return  itemRepository.findAll();
    }
    public Item findOne(int id){
        Optional<Item> foundedItem = itemRepository.findById(id);
        return foundedItem.orElse(null);
    }

    @Transactional
    public void save(Item item){
        itemRepository.save(item);
    }


    @Transactional
    public void buy(int id, Person person){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        Person person1 = personDetails.getPerson();
        itemRepository.findById(id).ifPresent(
                item ->
                        item.setOwner(person1
        ));
    }

    public List<Item> allItem(){
        return itemRepository.findAll();
    }
    public Person getOwner(int id){
       return itemRepository.findById(id).map(Item::getOwner).orElse(null);
    }
}
