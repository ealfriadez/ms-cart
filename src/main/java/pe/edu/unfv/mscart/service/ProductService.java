package pe.edu.unfv.mscart.service;

import pe.edu.unfv.mscart.model.dto.ProductResponseDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ProductService {

    public List<ProductResponseDto> findAll();
    public ProductResponseDto findById(Long id);
}
