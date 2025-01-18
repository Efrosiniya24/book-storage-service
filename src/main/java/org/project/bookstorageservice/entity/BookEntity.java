package org.project.bookstorageservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String name;

    private String genre;
    private String description;

    @Column(nullable = false)
    private String author;
}
