package pe.edu.unfv.mscart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pe.edu.unfv.mscart.model.dto.ProductResponseDto;

import java.util.List;

@FeignClient(name = "ms-product", url = "http://localhost:8001", path = "/v1")
public interface ProductClientRest {

    @GetMapping
    public List<ProductResponseDto> findAllItems();

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id);
}
