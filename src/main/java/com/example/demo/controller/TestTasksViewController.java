package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Books;
import com.example.demo.utils.JsonWrapper;
import com.example.demo.utils.MultiSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task/view")
public class TestTasksViewController {

    @Autowired
    private TestTasksController testTasksController;

    @GetMapping("/older")
    public @ResponseBody
    ModelAndView showAuthorsOlderParamAndSortByBorn(
            @RequestParam(value = "age", required = false, defaultValue = "55") String age,
            @RequestParam(value = "desc", required = false) String desc) {
        JsonWrapper obj = testTasksController.showAuthorsOlderParamAndSortByBorn(age, desc);
        ModelAndView model = new ModelAndView("TaskView");
        model.addObject("authorList", obj.getInnerObject());
        model.addObject("message", "Authors older than parameter with sort by date function");
        model.addObject("sortable", true);
        return model;
    }

    @GetMapping("/books")
    public ModelAndView booksWhoseAuthorHasMoreThan1WrittenBooks() {
        JsonWrapper obj = testTasksController.booksWhoseAuthorHasMoreThan1WrittenBooks();
        ModelAndView model = new ModelAndView("TaskView");
        model.addObject("bookList", obj.getInnerObject());
        model.addObject("message", "Books whose author has more than one written book");
        return model;
    }

    @GetMapping("/mostbooks")
    public ModelAndView authorWhichHasMostBooks() {
        JsonWrapper obj = testTasksController.authorWhichHasMostBooks();
        ModelAndView model = new ModelAndView("TaskView");
        model.addObject("authorList", obj.getInnerObject());
        model.addObject("message", "Author(\'s) which has most books");
        return model;
    }

    @GetMapping("/genres")
    public ModelAndView byGenres() {
        JsonWrapper obj = testTasksController.byGenres();
        ModelAndView model = new ModelAndView("TaskView");
        model.addObject("genres", ((MultiSet)obj.getInnerObject()).getMap());
        model.addObject("message", "Number of books by genre");
        return model;
    }

}
