package pe.edu.unfv.mscart.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pe.edu.unfv.mscart.model.dto.CartItemResponseDto;
import pe.edu.unfv.mscart.model.dto.CartResponseDto;
import pe.edu.unfv.mscart.model.dto.ProductResponseDto;
import pe.edu.unfv.mscart.model.entity.CartEntity;
import pe.edu.unfv.mscart.model.entity.CartItemEntity;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(source = "entity.customerId", target = "customerId")
    @Mapping(source = "entity.creationDate", target = "creationDate")
    @Mapping(source = "entity.items", target = "items", qualifiedByName = "entityToResponseForItems")
    CartResponseDto entityToResponse(CartEntity entity);

    @Mapping(source = "entity.productId", target = "productId")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.price", target = "price")
    @Mapping(source = "entity.quantity", target = "quantity")
    @Mapping(source = "entity.subTotal", target = "subTotal")
    @Mapping(source = "entity.creationDate", target = "creationDate")
    CartItemResponseDto entityToResponse(CartItemEntity entity);

    @Named("entityToResponseForItems")
    default List<CartItemResponseDto> entityToResponseForItems(List<CartItemEntity> items) {
        return items.stream().map(this::entityToResponse).collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true) // Ignora el ID autogenerado de la entidad CartItemEntity
    @Mapping(source = "response.id", target = "productId")
    @Mapping(source = "response.name", target = "name")
    @Mapping(source = "response.price", target = "price")
    @Mapping(source = "quantity", target = "quantity")
    CartItemEntity responseToEntity(ProductResponseDto response, Integer quantity);
}
