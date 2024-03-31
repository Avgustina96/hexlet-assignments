package exercise.mapper;

import exercise.dto.CategoryCreateDTO;
import exercise.dto.CategoryDTO;
import exercise.model.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(
//        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CategoryMapper {

//    @Mapping(target = "category.id", source = "categoryId")
    public abstract Category map(CategoryCreateDTO dto);

//    @Mapping(source = "category.id", target = "categoryId")
    public abstract CategoryDTO map(Category model);

}