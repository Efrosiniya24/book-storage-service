package org.project.bookstorageservice.controller;

import lombok.AllArgsConstructor;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.service.BookService;
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
}
