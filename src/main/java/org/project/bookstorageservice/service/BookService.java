package org.project.bookstorageservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.entity.BookEntity;
import org.project.bookstorageservice.mapper.BookMapper;
import org.project.bookstorageservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.toBookEntity(bookDTO);
        BookEntity savedBook = bookRepository.save(bookEntity);
        return bookMapper.toBookDTO(savedBook);
    }

    public List<BookDTO> listOfBooks() {
        return bookMapper.toBookDTOList(bookRepository.findAll());
    }

    public BookDTO getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id = " + id + " not found"));
        return bookMapper.toBookDTO(bookEntity);
    }
}
