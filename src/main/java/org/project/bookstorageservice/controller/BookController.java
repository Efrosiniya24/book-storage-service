package org.project.bookstorageservice.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("books/book-storage")
public class BookController {
    private final BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        BookDTO savedBook = bookService.addBook(bookDTO);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping("/all-books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> listOfBooks = bookService.listOfBooks();
        return ResponseEntity.ok(listOfBooks);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        try {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<BookDTO> getBookByIsbn(@PathVariable String isbn) {
        try {
            BookDTO bookDTO = bookService.findBookByIsbn(isbn);
            return ResponseEntity.ok(bookDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/update-book")
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        try {
            BookDTO updatedBook = bookService.updateBook(bookDTO);
            return ResponseEntity.ok(updatedBook);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book with id = " + id + " is deleted");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
