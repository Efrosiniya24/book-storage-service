package org.project.bookstorageservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
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
    void addBook() throws Exception {
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
}