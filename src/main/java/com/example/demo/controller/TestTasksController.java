package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.Books;
import com.example.demo.utils.JsonWrapper;
import com.example.demo.utils.MultiSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TestTasksController {

    private final ApiController apiController;

    private int _age;
    private int asc;
    private final String noGenre = "UNKNOWN GENRE";

    @Autowired
    public TestTasksController(ApiController apiController) {
        this.apiController = apiController;
    }

    /**
     * Param <code>age</code> is optional. Default equals 55.
     * Param <code>desc</code> is optional. Not null for sorted by descending list. Null - for ascending
     * */
    @GetMapping("/older")
    public @ResponseBody
    JsonWrapper showAuthorsOlderParamAndSortByBorn(
            @RequestParam(value = "age", required = false, defaultValue = "55") String age,
            @RequestParam(value = "desc", required = false) String desc) {
        asc = (desc == null ? 1 : -1);
        if(age != null) {
            try {
                _age = Integer.parseInt(age);
            } catch (NumberFormatException e) {return new JsonWrapper("Incorrect param age", JsonWrapper.STATUS.ERROR);}
        }
        List<Author> authors = null;

        try {
            authors = (List<Author>) apiController.getAllAuthors(null).getInnerObject();
        } catch (ClassCastException e) {return new JsonWrapper("Internal Error", JsonWrapper.STATUS.ERROR);};
        authors = authors.stream().filter(this::isOlder).sorted(this::ageComparator)
                .collect(Collectors.toList());
        if(authors.size() == 0) return new JsonWrapper("No such authors older than " + _age, JsonWrapper.STATUS.NO_DATA);
        return new JsonWrapper(authors, JsonWrapper.STATUS.OK);
    }

    @GetMapping("/books")
    public JsonWrapper booksWhoseAuthorHasMoreThan1WrittenBooks() {
        List<Books> books = null;
        try {
            books = (List<Books>) apiController.getAllBooks(null).getInnerObject();
        } catch (ClassCastException e) {return new JsonWrapper("Internal Error", JsonWrapper.STATUS.ERROR);};
        books = books.stream().filter(i -> {
            for(Author a : i.getAuthorSet()) if(a.getBookCount() > 1) return true;
            return false;
        }).collect(Collectors.toList());
        if(books.size() == 0) return new JsonWrapper("No such books whose author has more than 1 written books", JsonWrapper.STATUS.NO_DATA);
        return new JsonWrapper(books, JsonWrapper.STATUS.OK);
    }

    private int max = 0;
    @GetMapping("/mostbooks")
    public JsonWrapper authorWhichHasMostBooks() {
        List<Author> authors = null;

        try {
            authors = (List<Author>) apiController.getAllAuthors(null).getInnerObject();
        } catch (ClassCastException e) {return new JsonWrapper("Internal Error", JsonWrapper.STATUS.ERROR);};
        for(Author a : authors) if(a.getBookCount() > max) max = a.getBookCount();
        authors = authors.stream().filter(i -> i.getBookCount() == max).collect(Collectors.toList());
        if(authors.size() == 0) return new JsonWrapper("No authors" + _age, JsonWrapper.STATUS.NO_DATA);
        return new JsonWrapper(authors, JsonWrapper.STATUS.OK);
    }

    @GetMapping("/genres")
    public JsonWrapper byGenres() {
        MultiSet genres = new MultiSet();
        List<Books> books = null;
        try {
            books = (List<Books>) apiController.getAllBooks(null).getInnerObject();
        } catch (ClassCastException e) {return new JsonWrapper("Internal Error", JsonWrapper.STATUS.ERROR);};
        books.forEach(i -> {
            if(i.getGenre() == null) genres.add(noGenre);
            else genres.add(i.getGenre());
        });
        if(genres.size() == 0) return new JsonWrapper("No books" + _age, JsonWrapper.STATUS.NO_DATA);
        return new JsonWrapper(genres, JsonWrapper.STATUS.OK);
    }


    private boolean isOlder(@NotNull Author a) {
        if(a.getBorn() == null) return false;
        return Period.between(new Date(a.getBorn().getTime()).toLocalDate(),
                new Date(new java.util.Date().getTime()).toLocalDate()).getYears() >= _age;
    }

    private int ageComparator(@NotNull Author a1, @NotNull Author a2) {
        return asc * (int)((a1.getBorn().getTime() - a2.getBorn().getTime()) / 1000000);
    }

}
