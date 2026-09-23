package edu.ku.book_api.controller;

import edu.ku.book_api.model.Book;
import edu.ku.book_api.model.BookInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final List<Book> books = new ArrayList<>(List.of(
        new Book(1L, "Java Programming", "John Smith", 5),
        new Book(2L, "Web Development", "Sara Ahmad", 3),
        new Book(3L, "Database Systems", "Ali Khan", 4)
    ));

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateBook(
        @PathVariable Long bookId,
        @RequestBody BookInput input
    ) {
        for (int i = 0; i < books.size(); i++) {
            Book existing = books.get(i);
            if (existing.id().equals(bookId)) {
                Book updated = new Book(
                    existing.id(),
                    input.title(),
                    input.author(),
                    input.availableCopies()
                );
                books.set(i, updated);
                return ResponseEntity.ok(updated);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        boolean removed = books.removeIf(book -> book.id().equals(bookId));
        return removed
            ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}

