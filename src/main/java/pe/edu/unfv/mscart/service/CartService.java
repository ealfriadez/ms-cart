package pe.edu.unfv.mscart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.unfv.mscart.configuration.error.ResourceNotFoundException;
import pe.edu.unfv.mscart.model.dto.CartResponseDto;
import pe.edu.unfv.mscart.model.mapper.CartMapper;
import pe.edu.unfv.mscart.repository.CartRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartMapper mapper;

    @Transactional
    public CartResponseDto findByCustomerId(Long customerId) {
        log.info("findByCustomerId");
        return cartRepository.findByCustomerId(customerId)
                .map(mapper::entityToResponse).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }
}
