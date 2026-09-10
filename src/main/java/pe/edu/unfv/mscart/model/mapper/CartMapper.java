package pe.edu.unfv.mscart.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import pe.edu.unfv.mscart.model.dto.CartResponseDto;
import pe.edu.unfv.mscart.model.entity.CartEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(source = "entity.customerId", target = "customerId")
    @Mapping(source = "entity.creationDate", target = "creationDate")
    @Mapping(source = "entity.items", target = "items", qualifiedByName = "entityToResponseForItems")
    CartResponseDto entityToResponse(CartEntity entity);
}
