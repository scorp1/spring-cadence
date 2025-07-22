package org.example.orderservice.repository;

import java.util.Optional;
import java.util.UUID;
import org.example.orderservice.entity.Compensate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompensateRepository extends JpaRepository<Compensate, UUID> {

  Optional<Compensate> findCompensateByProductId(Long id);

}
