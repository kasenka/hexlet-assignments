package exercise.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import exercise.dto.CarCreateDTO;
import exercise.dto.CarUpdateDTO;
import exercise.dto.CarDTO;
import exercise.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// BEGIN
@Component
@Mapper(
        // Подключение JsonNullableMapper
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class CarMapper {
    @Autowired
    private JsonNullableMapper jsonNullableMapper;
    // Остальные методы
    public abstract Car map(CarCreateDTO dto);
    public abstract CarDTO map(Car model);

    public void update(CarUpdateDTO dto, Car model) {
        if ( dto == null ) {
            return;
        }
        if ( jsonNullableMapper.isPresent( dto.getModel() ) ) {
            model.setModel( jsonNullableMapper.unwrap( dto.getModel() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getManufacturer() ) ) {
            model.setManufacturer( jsonNullableMapper.unwrap( dto.getManufacturer() ) );
        }
        if ( jsonNullableMapper.isPresent( dto.getEnginePower() ) ) {
            model.setEnginePower( jsonNullableMapper.unwrap( dto.getEnginePower() ) );
        }
    }
}


// END
