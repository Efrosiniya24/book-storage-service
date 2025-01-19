package org.project.bookstorageservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.entity.BookEntity;
import org.project.bookstorageservice.mapper.BookMapper;
import org.project.bookstorageservice.repository.BookRepository;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;


@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void addBook() {
        //given
        BookDTO bookDTO = new BookDTO(1L, "isbn", "name", "genre", "description", "author");
        BookEntity bookEntity = new BookEntity();

        //when
        when(bookMapper.toBookEntity(bookDTO)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookMapper.toBookDTO(bookEntity)).thenReturn(bookDTO);

        //then
        BookDTO book = bookService.addBook(bookDTO);
        assertAll(
                () -> assertEquals(bookDTO, book),
                () -> assertNotNull(book)
        );

        verify(bookMapper, times(1)).toBookEntity(bookDTO);
        verify(bookRepository, times(1)).save(bookEntity);
        verify(bookMapper, times(1)).toBookDTO(bookEntity);
    }
}

