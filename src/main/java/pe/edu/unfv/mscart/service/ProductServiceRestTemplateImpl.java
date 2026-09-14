package pe.edu.unfv.mscart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pe.edu.unfv.mscart.model.dto.ProductResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceRestTemplateImpl implements ProductService{

    private final RestTemplate restTemplate;

    /*private String urlBase = "http://localhost:8001/v1";*/

    private String urlBase = "http://ms-product/v1";

    private String urlId = urlBase + "/{id}";

    @Override
    public List<ProductResponseDto> findAll() {
        log.info("findAll");
        return restTemplate.exchange(urlBase, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductResponseDto>>(){}).getBody();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        log.info("findById");
        Map<String,String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        try {
            var response = restTemplate.getForEntity(urlId, ProductResponseDto.class, pathVariables);
            return response.getBody();
        }catch (HttpClientErrorException e){
            if(e.getStatusCode()== HttpStatus.NOT_FOUND){
                return null;
            }else{
                throw e;
            }
        }
    }
}
