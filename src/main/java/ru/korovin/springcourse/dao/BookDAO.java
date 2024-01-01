package ru.korovin.springcourse.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.korovin.springcourse.models.Book;
import ru.korovin.springcourse.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate){
        BookDAO.jdbcTemplate = jdbcTemplate;
    }


    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book newBook){
        jdbcTemplate.update("INSERT INTO book (name, author, year) VALUES (?,?,?);",
                newBook.getName(),
                newBook.getAuthor(),
                newBook.getYear());
    }

    public Optional<Book> show(int id){
        return jdbcTemplate.query("SELECT * FROM book WHERE book_id = ?",new Object[]{id},new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void setBookOwner(int book_id, Person person){
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE book_id = ?;",
                person.getPerson_id(),
                book_id);
    }

    public void freeBookOwner(int book_id){
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE book_id = ?",book_id);
    }

    public void deleteBook(int book_id){
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ? ", book_id);
    }

    public void updateBook(Book updated){
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year = ? WHERE book_id = ?",
                updated.getName(),
                updated.getAuthor(),
                updated.getYear(),
                updated.getBook_id());
    }

    public List<Book> getBooksByOwner(int person_id){
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?",new Object[]{person_id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
