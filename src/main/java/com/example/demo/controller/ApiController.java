package com.example.demo.controller;

import com.example.demo.model.Author;
import com.example.demo.model.AuthorBookRelation;
import com.example.demo.model.Books;
import com.example.demo.service.AuthorBookRelationService;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BooksSevice;
import com.example.demo.utils.JsonWrapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api")
@RestController
public class ApiController {

    private final BooksSevice booksService;

    private final AuthorService authorService;

    private final AuthorBookRelationService authorBookRelationService;

    private final Logger logger;

    @Autowired
    public ApiController(BooksSevice booksService, AuthorService authorService, AuthorBookRelationService authorBookRelationService, Logger logger) {
        this.booksService = booksService;
        this.authorService = authorService;
        this.authorBookRelationService = authorBookRelationService;
        this.logger = logger;
    }

    // Authors
    /** Return all authors or author with typed <code>id</code>. <code>id<code/> is optional. */
    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public @ResponseBody
    JsonWrapper getAllAuthors(@RequestParam(value = "id", required = false) String id) {
        if(id == null) return new JsonWrapper(authorService.getAllAuthors(), JsonWrapper.STATUS.OK);
        Author _a = authorService.getAuthorByID(Integer.parseInt(id));
        if(_a == null) return new JsonWrapper("Incorrect author id", JsonWrapper.STATUS.ERROR);
        return new JsonWrapper(_a, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/authors/add", method = RequestMethod.POST)
    public @ResponseBody JsonWrapper createAuthor(@ModelAttribute Author author) {
        if(author.getId() != -1) return new JsonWrapper("Incorrect endpoint, perhaps you should use /update", JsonWrapper.STATUS.ERROR);
        if(!authorService.save(author)) return new JsonWrapper("Author with id: " + author.getId() + " doesn\'t created", JsonWrapper.STATUS.ERROR);
        logger.info("Saving object with type " + author.getClass() + " to database with id: " + author.getId());
        return new JsonWrapper(author, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/authors/update", method = RequestMethod.PUT)
    public @ResponseBody JsonWrapper updateAuthor(@ModelAttribute Author author) {
        Author _author = authorService.getAuthorByID(author.getId());
        if(_author == null) return new JsonWrapper("Cannot update author with id: " + author.getId(), JsonWrapper.STATUS.ERROR);
        if(!authorService.save(author)) return new JsonWrapper("Author with id: " + author.getId() + " doesn\'t updated", JsonWrapper.STATUS.ERROR);
        logger.info("Updating object with type " + author.getClass() + " to database with id: " + author.getId());
        return new JsonWrapper(author, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/authors/delete", method = RequestMethod.DELETE)
    public @ResponseBody JsonWrapper deleteAuthor(@RequestParam String id) {
        if(id == "") return new JsonWrapper("Incorrect id", JsonWrapper.STATUS.ERROR);
        int _id = Integer.parseInt(id);
        authorBookRelationService.deleteAllAuthorBooks(_id);
        logger.info("Deleted relation with id=" + _id);
        authorService.delete(_id);
        logger.info("Deleted author with id=" + id);
        return new JsonWrapper("Successfully deleted author with id: " + id, JsonWrapper.STATUS.OK);
    }

    // Books
    /** Return all books or book with typed <code>id</code>. <code>id<code/> is optional. */
    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public @ResponseBody JsonWrapper getAllBooks(@RequestParam(value = "id", required = false) String id) {
        if(id == null) return new JsonWrapper(booksService.getAllBooks(), JsonWrapper.STATUS.OK);
        Books _book = booksService.getBookByID(Integer.parseInt(id));
        if(_book == null) return new JsonWrapper("Incorrect book id", JsonWrapper.STATUS.ERROR);
        return new JsonWrapper(_book, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/books/add", method = RequestMethod.POST)
    public @ResponseBody JsonWrapper createBook(@ModelAttribute Books book) {
        if(book.getId() != -1) return new JsonWrapper("Incorrect endpoint, perhaps you should use /update", JsonWrapper.STATUS.ERROR);
        booksService.save(book);
        logger.info("Saving object with type " + book.getClass() + " to database with id: " + book.getId());
        return new JsonWrapper(book, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/books/update", method = RequestMethod.PUT)
    public @ResponseBody JsonWrapper updateBook(@ModelAttribute Books book) {
        Books _book = booksService.getBookByID(book.getId());
        if(_book == null) return new JsonWrapper("Cannot update book with id: " + book.getId(), JsonWrapper.STATUS.ERROR);
        booksService.save(book);
        logger.info("Updating object with type " + book.getClass() + " to database with id: " + book.getId());
        return new JsonWrapper(book, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/books/delete", method = RequestMethod.DELETE)
    public @ResponseBody JsonWrapper deleteBook(@RequestParam String id) {
        if(id == "") return new JsonWrapper("Incorrect id", JsonWrapper.STATUS.ERROR);
        int _id = Integer.parseInt(id);
        authorBookRelationService.deleteAllBookAuthors(_id);
        logger.info("Deleted relation with id=" + _id);
        booksService.delete(_id);
        logger.info("Deleted book with id=" + id);
        return new JsonWrapper("Successfully deleted book with id: " + id, JsonWrapper.STATUS.OK);
    }

    // Relationship Authors-Books
    /** Return all relations or relation with typed <code>id</code>. <code>id<code/> is optional. */
    @RequestMapping(value = "/rel", method = RequestMethod.GET)
    public @ResponseBody
    JsonWrapper getAllRelations(@RequestParam(value = "id", required = false) Object id) {
        if(id == null) return new JsonWrapper(authorBookRelationService.getAllRelations(), JsonWrapper.STATUS.OK);
        AuthorBookRelation _a = authorBookRelationService.getRelationByID(Integer.parseInt((String)id));
        if(_a == null) return new JsonWrapper("Incorrect relation id", JsonWrapper.STATUS.ERROR);
        return new JsonWrapper(_a, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/rel/add", method = RequestMethod.POST)
    public @ResponseBody JsonWrapper createRelation(@ModelAttribute AuthorBookRelation rel) {
        if(rel.getId() != -1) return new JsonWrapper("Incorrect endpoint, perhaps you should use /update", JsonWrapper.STATUS.ERROR);
        if(authorService.getAuthorByID(rel.getAuthorId()) == null || booksService.getBookByID(rel.getBookId()) == null)
            return new JsonWrapper("Incorrect bookId/authorId", JsonWrapper.STATUS.ERROR);
        if(!authorBookRelationService.save(rel)) return new JsonWrapper("Same relation already exist", JsonWrapper.STATUS.ERROR);
        logger.info("Saving object with type " + rel.getClass() + " to database with id: " + rel.getId());
        return new JsonWrapper(rel, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/rel/update", method = RequestMethod.PUT)
    public @ResponseBody JsonWrapper updateRelation(@ModelAttribute AuthorBookRelation rel) {
        AuthorBookRelation _rel = authorBookRelationService.getRelationByID(rel.getId());
        if(_rel == null) return new JsonWrapper("Cannot update relation with id: " + rel.getId(), JsonWrapper.STATUS.ERROR);
        if(!authorBookRelationService.save(rel)) return new JsonWrapper("Same relation already exist", JsonWrapper.STATUS.ERROR);
        logger.info("Updating object with type " + rel.getClass() + " to database with id: " + rel.getId());
        return new JsonWrapper(rel, JsonWrapper.STATUS.OK);
    }

    @RequestMapping(value = "/rel/delete", method = RequestMethod.DELETE)
    public @ResponseBody JsonWrapper deleteRelation(@RequestParam String id) {
        if(id == "") return new JsonWrapper("Incorrect id", JsonWrapper.STATUS.ERROR);
        int _id = Integer.parseInt(id);
        authorBookRelationService.delete(_id);
        logger.info("Deleted relation with id=" + id);
        return new JsonWrapper("Successfully deleted relation with id: " + id, JsonWrapper.STATUS.OK);
    }

    public JsonWrapper deleteRelationByData(String authorId, String bookId) {
        if((authorId == null || authorId.isEmpty()) || (bookId == null || bookId.isEmpty())) return new JsonWrapper("Incorrect id", JsonWrapper.STATUS.ERROR);
        Integer relId = authorBookRelationService.getRelationByData(Integer.parseInt(authorId), Integer.parseInt(bookId));
        if(relId == null) return new JsonWrapper("Relation does not exist", JsonWrapper.STATUS.ERROR);
        return deleteRelation(Integer.toString(relId));
    }
}
