package pe.edu.unfv.mscart.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.edu.unfv.mscart.client.ProductClientRest;
import pe.edu.unfv.mscart.model.dto.ProductResponseDto;

import java.util.List;

@Slf4j
@Primary
@RequiredArgsConstructor
@Service
public class ProductServiceFeignImpl implements ProductService{

    private final ProductClientRest client;

    @Override
    public List<ProductResponseDto> findAll() {
        log.info("findAll ---");
        return client.findAllItems();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        log.info("findById ---");
        try {
            return client.findById(id);
        }catch (FeignException e){
            if(e.status() == HttpStatus.NOT_FOUND.value()){
                return null;
            }else {
                throw e;
            }
        }
    }
}
