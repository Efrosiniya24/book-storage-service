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


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    void addBookTest() {
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

    @Test
    void listOfBooksTest(){
        //given
        List<BookDTO> booksList = Arrays.asList(
                new BookDTO(1L, "isbn", "name", "genre", "description", "author"),
                new BookDTO(2L, "isbn2", "name2", "genre2", "description2", "author2")
        );

        List<BookEntity> booksListEntity = Arrays.asList(
                new BookEntity(1L, "isbn", "name", "genre", "description", "author"),
                new BookEntity(2L, "isbn2", "name2", "genre2", "description2", "author2")
        );

        //when
        when(bookRepository.findAll()).thenReturn(booksListEntity);
        when(bookMapper.toBookDTOList(booksListEntity)).thenReturn(booksList);

        //then
        List<BookDTO> books = bookService.listOfBooks();
        assertEquals(booksList, books);
        verify(bookMapper, times(1)).toBookDTOList(booksListEntity);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookByIdTest() {
        //given
        BookDTO bookDTO = new BookDTO(1L, "isbn", "name", "genre", "description", "author");
        BookEntity bookEntity = new BookEntity(1L, "isbn", "name", "genre", "description", "author");

        //when
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.toBookDTO(bookEntity)).thenReturn(bookDTO);

        //then
        BookDTO book = bookService.getBookById(1L);
        assertEquals(bookDTO, book);

        verify(bookRepository, times(1)).findById(1L);
        verify(bookMapper, times(1)).toBookDTO(bookEntity);
    }
}

