package org.project.bookstorageservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.entity.BookEntity;
import org.project.bookstorageservice.feign.BookFeign;
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
    private final BookFeign bookFeign;

    public BookDTO addBook(BookDTO bookDTO) {
        BookEntity bookEntity = bookMapper.toBookEntity(bookDTO);
        BookEntity savedBook = bookRepository.save(bookEntity);

        bookFeign.createBook(savedBook.getId());

        return bookMapper.toBookDTO(savedBook);
    }

    public List<BookDTO> listOfBooks() {
        return bookMapper.toBookDTOList(bookRepository.findAll());
    }

    public BookDTO getBookById(Long id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book with id = " + id + " not found"));
        return bookMapper.toBookDTO(book);
    }

    public BookDTO findBookByIsbn(String isbn) {
        Optional<BookEntity> bookEntity = bookRepository.findByIsbn(isbn);
        return bookEntity.map(bookMapper::toBookDTO)
                .orElseThrow(() -> new EntityNotFoundException("Book with ISBN = " + isbn + " not found"));
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        BookEntity requiredBook = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id = " + bookDTO.getId() + " not found"));

        if (bookDTO.getIsbn() != null) {
            requiredBook.setIsbn(bookDTO.getIsbn());
        }

        if (bookDTO.getName() != null) {
            requiredBook.setName(bookDTO.getName());
        }

        if (bookDTO.getAuthor() != null) {
            requiredBook.setAuthor(bookDTO.getAuthor());
        }

        if (bookDTO.getDescription() != null) {
            requiredBook.setDescription(bookDTO.getDescription());
        }

        if (bookDTO.getGenre() != null) {
            requiredBook.setGenre(bookDTO.getGenre());
        }

        BookEntity updatedBook = bookRepository.save(requiredBook);
        return bookMapper.toBookDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id = " + id + " not found");
        }
        bookRepository.deleteById(id);

        bookFeign.deleteBook(id);
    }
}
