package ru.mihail.springcourse.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihail.springcourse.FirstSecurityApp.models.Item;
import ru.mihail.springcourse.FirstSecurityApp.models.Person;

import ru.mihail.springcourse.FirstSecurityApp.repositorys.ItemRepository;
import ru.mihail.springcourse.FirstSecurityApp.repositorys.PeopleRepository;
import ru.mihail.springcourse.FirstSecurityApp.security.PersonDetails;

import java.util.List;
import java.util.Optional;

@Service
@PreAuthorize("hasRole('ROLES_USER')")
@Transactional(readOnly = true)
public class PersonDetailsServices implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public PersonDetailsServices(PeopleRepository peopleRepository, ItemRepository itemRepository) {
        this.peopleRepository = peopleRepository;

        this.itemRepository = itemRepository;
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("делается запрос");
        Optional<Person> person = peopleRepository.findByUsername(username);
        System.out.println("запрос сделали, проверяем на наличие");
        if(person.isEmpty()) {
            System.out.println("пусто");
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("не пустой");
         return new PersonDetails(person.get());
    }

    @Transactional
    public void  delete(int id){
        peopleRepository.deleteById(id);
    }


    @Transactional
    public Object buyItem(int id){
        return  null;
    }

    public Person findOne(int id){

        return peopleRepository.findById(id).stream().filter(person -> person.getId()==id).findAny().orElse(null);
    }
    public Person forHomePage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails)authentication.getPrincipal();
        Person person1 = personDetails.getPerson();
        int person2 = person1.getId();
        return peopleRepository.findById(person2).orElse(null);
    }

    public List<Item> getItemByPersonId(int id){

        Optional<Person> person =  peopleRepository.findById(id);
        return person.get().getItems();
    }



}
