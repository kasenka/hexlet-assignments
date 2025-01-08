package exercise.mapper;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

// BEGIN
@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class ProductMapper {
    // Маппинг из categoryId в Product (переводим поле categoryId в Product)
    @Mapping(target = "category", source = "categoryId")
    public abstract Product map(ProductCreateDTO dto);

    // Маппинг из Product в ProductDTO (извлекаем category.id в categoryId)
    @Mapping(source = "category.id", target = "categoryId")
    public abstract ProductDTO map(Product model);

    // Маппинг для обновления Product (обновляем поле category с categoryId)
    @Mapping(target = "category", source = "categoryId")
    public abstract void update(ProductUpdateDTO dto, @MappingTarget Product model);
}


// END
