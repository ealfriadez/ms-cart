package pe.edu.unfv.mscart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.unfv.mscart.model.mapper.CartMapper;
import pe.edu.unfv.mscart.repository.CartRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartMapper cartMapper;
}
