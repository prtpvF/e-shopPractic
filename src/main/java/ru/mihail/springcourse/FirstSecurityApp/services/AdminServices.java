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
import ru.mihail.springcourse.FirstSecurityApp.repositorys.PeopleRepository;
import ru.mihail.springcourse.FirstSecurityApp.security.PersonDetails;

import java.util.List;

@Service
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Transactional(readOnly = true)
public class AdminServices {
    private final PeopleRepository peopleRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public AdminServices(PeopleRepository peopleRepository, ItemRepository itemRepository) {
        this.peopleRepository = peopleRepository;
        this.itemRepository = itemRepository;
    }

    public void doAdminStuff(){
        System.out.println("only admin here");
    }

    public List<Person>showAll(){
        return peopleRepository.findAll();
    }
    public Person findOne(int id){
        return peopleRepository.findById(id).stream().filter(person -> person.getId()==id).findAny().orElse(null);
    }

    @Transactional
    public void create(Item item){
          itemRepository.save(item);
    }
    public List<Item> showAllItem(){
        return itemRepository.findAll();
    }
    @Transactional
    public void update(int id, Item updatedItem){
        Item itemToBeUpdated = itemRepository.findById(id).get();
        updatedItem.setId(id);
        updatedItem.setOwner(itemToBeUpdated.getOwner());
        itemRepository.save(updatedItem);

    }
    public Item findOneItem(int id){
         return itemRepository.findById(id).orElse(null);
    }
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void delete(int id){
        itemRepository.deleteById(id);
    }

}
