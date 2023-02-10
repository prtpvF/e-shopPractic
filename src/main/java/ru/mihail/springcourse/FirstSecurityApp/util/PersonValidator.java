package ru.mihail.springcourse.FirstSecurityApp.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.mihail.springcourse.FirstSecurityApp.models.Person;
import ru.mihail.springcourse.FirstSecurityApp.services.PersonDetailsServices;
import ru.mihail.springcourse.FirstSecurityApp.services.RegistrationServices;

import javax.persistence.Access;

@Component
public class PersonValidator implements Validator {
   private PersonDetailsServices personDetailsServices;

   @Autowired
    public PersonValidator(PersonDetailsServices personDetailsServices) {
        this.personDetailsServices = personDetailsServices;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
      Person person = (Person) target;
      if(personDetailsServices.findByUsername(person.getUsername()).isPresent()){
          errors.rejectValue("username","", "user with this username was exist");
      }
      //есть ли человек с таким же никнеймом
    }
}
