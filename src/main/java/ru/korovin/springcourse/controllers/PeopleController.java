package ru.korovin.springcourse.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.korovin.springcourse.dao.BookDAO;
import ru.korovin.springcourse.dao.PersonDAO;
import ru.korovin.springcourse.models.Book;
import ru.korovin.springcourse.models.Person;
import ru.korovin.springcourse.util.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private PersonDAO personDAO;
    private BookDAO bookDAO;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator, BookDAO bookDAO) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people",personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model ){
        model.addAttribute("person",personDAO.show(id));
        model.addAttribute("books",bookDAO.getBooksByOwner(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "/people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult res){
        personValidator.validate(person, res);
        if(res.hasErrors())
            return "people/new";
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult res,
                         @PathVariable("id") int id){
        personValidator.validate(person, res);
        if(res.hasErrors())
            return "people/edit";
        personDAO.update(id, person);
        return "redirect:/people";
    }

}
