package exercise.mapper;

import exercise.dto.*;
import exercise.model.Category;
import exercise.model.Product;
import org.mapstruct.*;

// BEGIN
@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CategoryMapper {
//    @Mapping(target = "categoryId", source = "category")
    public abstract Category map(CategoryCreateDTO dto);

//    @Mapping(source = "category.id", target = "categoryId")
    public abstract CategoryDTO map(Category model);

}
// END
