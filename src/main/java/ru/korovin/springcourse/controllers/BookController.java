package ru.korovin.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.korovin.springcourse.dao.BookDAO;
import ru.korovin.springcourse.dao.PersonDAO;
import ru.korovin.springcourse.models.Book;
import ru.korovin.springcourse.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO){
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("books",bookDAO.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("newBook",new Book());
        return "books/new";
    }

    @PostMapping("/new")
    public String newBook(@ModelAttribute("newBook") Book newBook){
        bookDAO.save(newBook);
        System.out.println(newBook);
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book",bookDAO.show(id).get());
        Optional<Person> bookOwner = personDAO.getBookOwner(id);
        if(bookOwner.isPresent()){
            model.addAttribute("bookOwner",bookOwner.get());
        }else{
            model.addAttribute("people",personDAO.index());
            model.addAttribute("person",new Person());
        }
        return "books/show";
    }

    @PatchMapping("/{id}/setBookOwner")
    public String setBookOwner(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        bookDAO.setBookOwner(id, person);
        return "redirect:/books/{id}";
    }

    @PatchMapping("{id}/freeBook")
    public String freeBook(@PathVariable("id") int id){
        bookDAO.freeBookOwner(id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("{id}/deleteBook")
    public String deleteBook(@PathVariable("id") int id){
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id).get());
        return "books/edit";
    }

    @PatchMapping("{id}/edit")
    public String edit(@ModelAttribute("book") Book book, @PathVariable("id") int id){
        bookDAO.updateBook(book);
        return "redirect:books/{id}";
    }
}
