package exercise.mapper;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.model.Book;
import org.mapstruct.*;

@Mapper(
        uses = {JsonNullableMapper.class, ReferenceMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class BookMapper {

    @Mapping(target = "authorId", source = "author.id")
    public abstract BookDTO map(Book dto);

    @Mapping(target = "author", source = "authorId")
//    @Mapping(target = "author.firstName", source = "authorFirstName")
//    @Mapping(target = "author.lastName", source = "authorLastName")
    public abstract Book map(BookCreateDTO dto);

    @Mapping(target = "author", source = "authorId")
    public abstract void update(BookUpdateDTO dto, @MappingTarget Book model);
}
