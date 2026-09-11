package pe.edu.unfv.mscart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mapstruct.factory.Mappers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.edu.unfv.mscart.model.dto.CartResponseDto;
import pe.edu.unfv.mscart.model.entity.CartEntity;
import pe.edu.unfv.mscart.model.entity.CartItemEntity;
import pe.edu.unfv.mscart.model.mapper.CartMapper;
import pe.edu.unfv.mscart.repository.CartRepository;
import pe.edu.unfv.mscart.service.CartService;
import pe.edu.unfv.mscart.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductService productService;

    private CartMapper mapper = Mappers.getMapper(CartMapper.class);

    //@InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {

        cartService = new CartService(cartRepository, mapper, productService);

        CartItemEntity  cartItemEntity1 = CartItemEntity.builder()
                .productId(3L)
                .name("Teclado")
                .price(BigDecimal.valueOf(300))
                .quantity(3)
                .build();

        CartItemEntity  cartItemEntity2 = CartItemEntity.builder()
                .productId(2L)
                .name("Monitor")
                .price(BigDecimal.valueOf(200))
                .quantity(2)
                .build();

        CartEntity cartEntity = CartEntity.builder()
                .customerId(1L)
                .items(List.of(cartItemEntity1, cartItemEntity2))
                .build();

        Mockito.when(cartRepository.findByCustomerId(1L)).thenReturn(Optional.of(cartEntity));
    }

    @Test
    void whenValidGetId_ThenReturnCart() {
        CartResponseDto cartResponseDto = cartService.findByCustomerId(1L);
        assertEquals(1L, cartResponseDto.getCustomerId());
        assertEquals(2, cartResponseDto.getItems().size());
        assertEquals(3L, cartResponseDto.getItems().get(0).getProductId());
        assertEquals("Teclado", cartResponseDto.getItems().get(0).getName());
        assertEquals(BigDecimal.valueOf(300), cartResponseDto.getItems().get(0).getPrice());
        assertEquals(3, cartResponseDto.getItems().get(0).getQuantity());
    }
}
