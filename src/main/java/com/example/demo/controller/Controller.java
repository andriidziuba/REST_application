package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.AuthorBookRelation;
import com.example.demo.model.Books;
import com.example.demo.utils.JsonWrapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {

    private final ApiController apiController;

    private final Logger logger;

    @Autowired
    public Controller(ApiController apiController, Logger logger) {
        this.apiController = apiController;
        this.logger = logger;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody ModelAndView printMain() {
        return new ModelAndView("redirect:/authors");
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody ModelAndView authorList() {
        ModelAndView modelAndView = new ModelAndView("AuthorView");
        JsonWrapper authorList = apiController.getAllAuthors(null);
        modelAndView.addObject("authorList", authorList.getInnerObject());
        modelAndView.addObject("author", new Author());
        modelAndView.addObject("authorBookRelation", new AuthorBookRelation());
        return modelAndView;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody ModelAndView bookList() {
        ModelAndView modelAndView = new ModelAndView("BooksView");
        JsonWrapper bookList = apiController.getAllBooks(null);
        modelAndView.addObject("bookList", bookList.getInnerObject());
        modelAndView.addObject("book", new Books());
        modelAndView.addObject("authorBookRelation", new AuthorBookRelation());
        return modelAndView;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.POST)
    public @ResponseBody ModelAndView authorListPost(@ModelAttribute Author a, BindingResult b) {
        if(b.hasErrors()) {
            System.out.println(b);
        }
        if(a == null) return null;
        Object _obj = apiController.getAllAuthors(Integer.toString(a.getId())).getInnerObject();

        try {
            if(_obj instanceof String) {
                apiController.createAuthor(a);
            } else if(!_obj.equals(a)) {
                apiController.updateAuthor(a);
            }
        } catch (NullPointerException e) {
            logger.debug("Author was not saved");
        }
        ModelAndView modelAndView = authorList();
        return modelAndView;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public @ResponseBody ModelAndView bookListPost(@ModelAttribute Books a, BindingResult b) {
        if(b.hasErrors()) {
            System.out.println(b);
        }
        if(a == null) return null;
        Object _obj = apiController.getAllBooks(Integer.toString(a.getId())).getInnerObject();

        try {
            if(_obj instanceof String) {
                apiController.createBook(a);
            } else if(!_obj.equals(a)) {
                apiController.updateBook(a);
            }
        } catch (NullPointerException e) {
            logger.debug("Book was not saved");
        }
        ModelAndView modelAndView = bookList();
        return modelAndView;
    }

    @RequestMapping(value = "/authors/del", params = "id", method = RequestMethod.GET)
    public @ResponseBody ModelAndView delAuthor(@RequestParam(value = "id") String id) {
        apiController.deleteAuthor(id);
        return new ModelAndView("redirect:/authors");
    }

    @RequestMapping(value = "/books/del", params = "id", method = RequestMethod.GET)
    public @ResponseBody ModelAndView delBook(@RequestParam(value = "id") String id) {
        apiController.deleteBook(id);
        return new ModelAndView("redirect:/books");
    }

    @PostMapping(value = "/authors", params = "addRel")
    public ModelAndView addRelationAuthor(@ModelAttribute AuthorBookRelation rel, BindingResult b) {
        if(b.hasErrors()) System.out.println(b);
        ModelAndView model = authorList();
        System.out.println(rel);
        JsonWrapper obj = apiController.createRelation(rel);
        if(obj.getStatus() == JsonWrapper.STATUS.ERROR) {
            model.addObject("error", obj.getInnerObject());
            model.addObject("firstId", rel.getAuthorId());
            return model;
        }
        model = authorList();
        return model;
    }

    @PostMapping(value = "/books", params = "addRel")
    public ModelAndView addRelationBook(@ModelAttribute AuthorBookRelation rel, BindingResult b) {
        if(b.hasErrors()) System.out.println(b);
        ModelAndView model = bookList();
        System.out.println(rel);
        JsonWrapper obj = apiController.createRelation(rel);
        if(obj.getStatus() == JsonWrapper.STATUS.ERROR) {
            model.addObject("error", obj.getInnerObject());
            model.addObject("firstId", rel.getAuthorId());
            return model;
        }
        model = bookList();
        return model;
    }

    @RequestMapping(value = "/rel/del", method = RequestMethod.GET)
    public @ResponseBody ModelAndView delRel(@RequestParam(value = "authorId") String authorId,
                                             @RequestParam(value = "bookId") String bookId,
                                             @RequestParam(value = "from") String from) {
        if(apiController.deleteRelationByData(authorId, bookId).getStatus() == JsonWrapper.STATUS.OK)
            return new ModelAndView("redirect:/" + from);
        return null;
    }
}
