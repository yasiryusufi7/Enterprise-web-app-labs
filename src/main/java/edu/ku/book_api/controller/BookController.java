package edu.ku.book_api.controller;

import edu.ku.book_api.model.Books;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final List<Books> books = new ArrayList<>(List.of(
        new Books(1L, "Clean Code", "Robert C. Martin",
            "9780132350884", 2008, "Software Engineering"),
        new Books(2L, "Effective Java", "Joshua Bloch",
            "9780134685991", 2018, "Java"),
        new Books(3L, "Designing Data-Intensive Applications", "Martin Kleppmann",
            "9781449373320", 2017, "Distributed Systems"),
        new Books(4L, "Spring in Action", "Craig Walls",
            "9781617297571", 2022, "Spring"),
        new Books(5L, "Computer Networks", "Andrew S. Tanenbaum",
            "9780132126953", 2010, "Networking")
    ));

    private final AtomicLong idCounter = new AtomicLong(5);

    @GetMapping
    public List<Books> getAllBooks() {
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Books> getBookById(@PathVariable Long id) {
        return books.stream()
            .filter(book -> book.getId().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Books> addBook(@RequestBody Books newBook) {
        newBook.setId(idCounter.incrementAndGet());
        books.add(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }
}