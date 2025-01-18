package org.project.bookstorageservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.entity.BookEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    BookDTO toBookDTO(BookEntity bookEntity);
    BookEntity toBookEntity(BookDTO bookDTO);
}
