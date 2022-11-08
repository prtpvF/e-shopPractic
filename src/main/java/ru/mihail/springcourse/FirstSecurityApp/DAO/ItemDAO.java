package ru.mihail.springcourse.FirstSecurityApp.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.mihail.springcourse.FirstSecurityApp.models.Person;

@Component
public class ItemDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void buy(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE Item SET person_id=? WHERE id=?", selectedPerson.getId(), id);
    }
}
