package org.project.bookstorageservice.service;

import lombok.AllArgsConstructor;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.entity.BookEntity;
import org.project.bookstorageservice.mapper.BookMapper;
import org.project.bookstorageservice.repository.BookRepository;
import org.springframework.stereotype.Service;

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

}
