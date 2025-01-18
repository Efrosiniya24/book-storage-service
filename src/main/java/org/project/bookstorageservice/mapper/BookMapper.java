package org.project.bookstorageservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.project.bookstorageservice.dto.BookDTO;
import org.project.bookstorageservice.entity.BookEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    BookDTO toBookDTO(BookEntity bookEntity);
    BookEntity toBookEntity(BookDTO bookDTO);
    List<BookDTO> toBookDTOList(List<BookEntity> bookEntityList);
}
