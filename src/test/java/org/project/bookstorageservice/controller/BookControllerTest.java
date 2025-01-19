package org.project.bookstorageservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockitoBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addBookTest() throws Exception {
        //given
        BookDTO bookDTO = new BookDTO(1L, "isbn", "name", "genre", "description", "author");
        String bookDTOJson = objectMapper.writeValueAsString(bookDTO);

        //when
        when(bookService.addBook(bookDTO)).thenReturn(bookDTO);

        //then
        mockMvc.perform(post("/books/book-storage/add-book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDTOJson))
                .andExpect(status().isOk());
        verify(bookService, times(1)).addBook(bookDTO);
    }

    @Test
    void getAllBooksTest() throws Exception {
        //given
        List<BookDTO> booksList = Arrays.asList(
                new BookDTO(1L, "isbn", "name", "genre", "description", "author"),
                new BookDTO(2L, "isbn2", "name2", "genre2", "description2", "author2")
        );
        String booksListJson = objectMapper.writeValueAsString(booksList);

        //when
        when(bookService.listOfBooks()).thenReturn(booksList);

        //then
        mockMvc.perform(get("/books/book-storage/all-books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(booksListJson))
                .andExpect(status().isOk());
        verify(bookService, times(1)).listOfBooks();
    }

    @Test
    void getBookByIdTest() throws Exception {
        //given
        BookDTO bookDTO = new BookDTO(1L, "isbn", "name", "genre", "description", "author");
        String bookDTOJson = objectMapper.writeValueAsString(bookDTO);

        //when
        when(bookService.getBookById(1L)).thenReturn(bookDTO);

        //then
        mockMvc.perform(get("/books/book-storage/book/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDTOJson))
                .andExpect(status().isOk());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void bookByIdIsNotFoundTest() throws Exception {
        //given

        //when
        when(bookService.getBookById(1L)).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(get("/books/book-storage/book/{id}", 1L))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void getBookByIsbnTest() throws Exception {
        //given
        BookDTO bookDTO = new BookDTO(1L, "isbn", "name", "genre", "description", "author");
        String bookDTOJson = objectMapper.writeValueAsString(bookDTO);

        //when
        when(bookService.findBookByIsbn("isbn")).thenReturn(bookDTO);

        //then
        mockMvc.perform(get("/books/book-storage/book/isbn/{id}", "isbn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDTOJson))
                .andExpect(status().isOk());
        verify(bookService, times(1)).findBookByIsbn("isbn");
    }

    @Test
    void bookByIsbnIsNotfoundTest() throws Exception {
        //given

        //when
        when(bookService.findBookByIsbn("isbn")).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(get("/books/book-storage/book/isbn/{id}", "isbn"))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).findBookByIsbn("isbn");
    }

    @Test
    void updateBookTest() throws Exception {
        //given
        BookDTO bookDTO = new BookDTO(1L, "isbn", "name", "genre", "description", "author");
        String bookDTOJson = objectMapper.writeValueAsString(bookDTO);

        //when
        when(bookService.updateBook(bookDTO)).thenReturn(bookDTO);

        //then
        mockMvc.perform(patch("/books/book-storage/updateBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDTOJson))
                .andExpect(status().isOk());
        verify(bookService, times(1)).updateBook(bookDTO);
    }

    @Test
    void idUpdateBookNotFoundTest() throws Exception {
        //given
        BookDTO bookDTO = new BookDTO(1L, "isbn", "name", "genre", "description", "author");
        String bookDTOJson = objectMapper.writeValueAsString(bookDTO);

        //when
        when(bookService.updateBook(bookDTO)).thenThrow(EntityNotFoundException.class);

        //then
        mockMvc.perform(patch("/books/book-storage/updateBook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDTOJson))
                .andExpect(status().isNotFound());
        verify(bookService, times(1)).updateBook(bookDTO);
    }
}