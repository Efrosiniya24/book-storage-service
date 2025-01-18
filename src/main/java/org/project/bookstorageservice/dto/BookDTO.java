package org.project.bookstorageservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class BookDTO {

    private UUID id;
    private String isbn;
    private String name;
    private String genre;
    private String description;
    private String author;
}
