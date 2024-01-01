package ru.korovin.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.korovin.springcourse.models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private static JdbcTemplate jdbcTemplate;
    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection connection;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate){
        PersonDAO.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person",new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id=?",new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person)  {
        jdbcTemplate.update("INSERT INTO Person (fio) VALUES (?)",person.getFIO());
    }

    public void update(int id, Person person){
        Person toBeUpdated = show(id);
        if(toBeUpdated != null){
            jdbcTemplate.update("UPDATE Person SET fio=? WHERE person_id=?",person.getFIO(), id);
        }
    }

    public void delete(int id){
        if(show(id) != null)
            jdbcTemplate.update("DELETE FROM Person WHERE person_id=?",id);
    }

    public Optional<Person> getBookOwner(int book_id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE person_id = " +
                "(SELECT person_id FROM Book WHERE book_id = ?)", new Object[]{book_id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

}
