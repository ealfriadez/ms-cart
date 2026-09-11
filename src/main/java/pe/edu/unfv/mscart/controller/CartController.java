package pe.edu.unfv.mscart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unfv.mscart.model.dto.CartRequestDeleteDto;
import pe.edu.unfv.mscart.model.dto.CartRequestDto;
import pe.edu.unfv.mscart.model.dto.CartResponseDto;
import pe.edu.unfv.mscart.service.CartService;

@RequestMapping(path = "v1")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping(value = "/{customerId}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
    public ResponseEntity<CartResponseDto> findByCustomerId(@PathVariable Long customerId) {
        CartResponseDto cartResponseDto = cartService.findByCustomerId(customerId);
        return ResponseEntity.ok(cartResponseDto);
    }

    @PostMapping(value = "/{customerId}/item", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
    public ResponseEntity<CartResponseDto> addItemToCart(@PathVariable Long customerId, @Valid @RequestBody CartRequestDto cartRequestDto) {
        CartResponseDto cartResponseDto = cartService.addItem(customerId, cartRequestDto);
        return ResponseEntity.ok(cartResponseDto);
    }

    @DeleteMapping(value = "/{customerId}/item", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE },
            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_PROBLEM_JSON_VALUE })
    public ResponseEntity<CartResponseDto> removeItemFromCart(@PathVariable Long customerId,  @Valid @RequestBody CartRequestDeleteDto cartRequestDeleteDto) {
        CartResponseDto cartResponseDto = cartService.removeItem(customerId, cartRequestDeleteDto);
        return ResponseEntity.ok(cartResponseDto);
    }
}
