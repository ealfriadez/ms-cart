package pe.edu.unfv.mscart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.unfv.mscart.model.dto.CartRequestDeleteDto;
import pe.edu.unfv.mscart.model.dto.CartRequestDto;
import pe.edu.unfv.mscart.model.dto.CartResponseDto;
import pe.edu.unfv.mscart.service.CartService;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(path = "v1")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @Value("${configuration.texto}")
    private String text;

    private final Environment env;

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

    @GetMapping("/configuration")
    public ResponseEntity<?> getConfiguration(@Value("${server.port}") String port) {
        Map<String,String> json = new HashMap<>();
        json.put("texto", text);
        json.put("port", port);

        if(env.getActiveProfiles().length>0 && env.getActiveProfiles()[0].equals("dev")){
            json.put("autor.nombre", env.getProperty("configuration.autor.nombre"));
            json.put("autor.email", env.getProperty("configuration.autor.email"));
        }

        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }
}
