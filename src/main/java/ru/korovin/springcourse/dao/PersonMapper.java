package ru.korovin.springcourse.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.korovin.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setPerson_id(rs.getInt("id"));
        person.setFIO(rs.getString("fio"));
        return person;
    }
}
