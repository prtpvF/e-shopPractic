package ru.mihail.springcourse.FirstSecurityApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mihail.springcourse.FirstSecurityApp.models.Person;
import ru.mihail.springcourse.FirstSecurityApp.repositorys.PeopleRepository;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class RegistrationServices {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServices(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        Date dateOfRegistration = new Date();
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_ADMIN");
        person.setCreatedAt(dateOfRegistration);

        peopleRepository.save(person);


    }
}
