package pe.edu.unfv.mscart.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.unfv.mscart.model.entity.CartEntity;

import java.util.Optional;

public interface CartRepository extends CrudRepository<CartEntity, Long> {

    Optional<CartEntity> findByCustomerId(long customerId);
}
