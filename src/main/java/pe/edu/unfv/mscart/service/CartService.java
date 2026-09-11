package pe.edu.unfv.mscart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.unfv.mscart.configuration.error.ResourceNotFoundException;
import pe.edu.unfv.mscart.model.dto.CartRequestDeleteDto;
import pe.edu.unfv.mscart.model.dto.CartRequestDto;
import pe.edu.unfv.mscart.model.dto.CartResponseDto;
import pe.edu.unfv.mscart.model.dto.ProductResponseDto;
import pe.edu.unfv.mscart.model.entity.CartEntity;
import pe.edu.unfv.mscart.model.entity.CartItemEntity;
import pe.edu.unfv.mscart.model.mapper.CartMapper;
import pe.edu.unfv.mscart.repository.CartRepository;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartMapper mapper;

    private final ProductService productService;

    @Transactional(readOnly = true)
    public CartResponseDto findByCustomerId(Long customerId) {
        log.info("findByCustomerId");
        return cartRepository.findByCustomerId(customerId)
                .map(mapper::entityToResponse).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Transactional
    public CartResponseDto addItem(Long customerId, CartRequestDto cartRequestDto) {
        log.info("addItem");

        CartEntity cartEntity = findCartByCustomerId(customerId);

        cartRequestDto.getItems().forEach(item -> {
            Optional<CartItemEntity> cartItemEntityOptional = cartEntity.getItems().stream().filter(p -> p.getProductId() == item.getProductId()).findFirst();
            if (cartItemEntityOptional.isPresent()) {
                ProductResponseDto product = productService.findById(item.getProductId());
                if (product == null) {
                    throw new ResourceNotFoundException("Product not found with id: " +  item.getProductId());
                }else {
                    log.info("ms-product port: {}", product.getPort());
                    cartEntity.getItems().add(mapper.responseToEntity(product, item.getQuantity()));
                }
            }
        });
        cartRepository.save(cartEntity);
        return mapper.entityToResponse(cartEntity);
    }

    @Transactional
    public CartResponseDto removeItem(Long customerId, CartRequestDeleteDto cartRequestDeleteDto) {
        log.info("removeItem");

        CartEntity cartEntity = findCartByCustomerId(customerId);

        cartRequestDeleteDto.getItems().forEach(item -> {
            cartEntity.getItems().removeIf(p -> p.getProductId() == item.getProductId());
        });
        cartRepository.save(cartEntity);
        return mapper.entityToResponse(cartEntity);
    }

    private CartEntity findCartByCustomerId(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
}
