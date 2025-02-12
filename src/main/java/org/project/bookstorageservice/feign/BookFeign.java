package org.project.bookstorageservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "book-tracker-service")
public interface BookFeign {

    @PostMapping("books/book-tracker/create-book/{id}")
    ResponseEntity<String> createBook(@PathVariable Long id);

    @DeleteMapping("books/book-tracker/delete-book/{id}")
    ResponseEntity<String> deleteBook(@PathVariable Long id);
}
