package pe.edu.unfv.mscart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import pe.edu.unfv.mscart.model.entity.CartEntity;
import pe.edu.unfv.mscart.model.entity.CartItemEntity;
import pe.edu.unfv.mscart.repository.CartRepository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Test
    void whenValidGetByCustomerId_ThenReturnCart(){
        Optional<CartEntity> cartEntity = cartRepository.findByCustomerId(333L);
        assertTrue(cartEntity.isPresent());
        assertEquals(2, cartEntity.get().getItems().size());
    }

    @Test
    void whenValidSave_ThenReturnCart(){
        CartItemEntity cartItemEntity1 = CartItemEntity.builder()
                .productId(3L)
                .name("Teclado")
                .price(BigDecimal.valueOf(300))
                .quantity(3)
                .build();

        CartItemEntity cartItemEntity2 = CartItemEntity.builder()
                .productId(2L)
                .name("Monitor")
                .price(BigDecimal.valueOf(200))
                .quantity(2)
                .build();

        CartEntity cartEntity = CartEntity.builder()
                .customerId(1L)
                .items(List.of(cartItemEntity1, cartItemEntity2))
                .build();

        cartRepository.save(cartEntity);

        Optional<CartEntity> cartEntityFound = cartRepository.findByCustomerId(1L);
        assertTrue(cartEntityFound.isPresent());
        assertEquals(2, cartEntityFound.get().getItems().size());
        assertEquals(new BigDecimal(900), cartEntityFound.get().getItems().getFirst().getSubTotal());
    }

    @Test
    void whenValidDeleteCartItemByCustomerId_ThenDeleted(){
        List<Long> productIdList = List.of(1L, 2L);
        Optional<CartEntity> cartEntity = cartRepository.findByCustomerId(333L);
        productIdList.forEach(productId -> {
            cartEntity.get().getItems().removeIf(item -> item.getProductId().equals(productId));
        });
        Optional<CartEntity> cartEntityFound = cartRepository.findByCustomerId(333L);
        assertEquals(0, cartEntityFound.get().getItems().size());
    }
}
