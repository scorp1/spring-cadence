package org.example.customerservice.repository;

import java.util.Optional;
import java.util.UUID;
import org.example.customerservice.entity.Compensate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompensateRepository extends JpaRepository<Compensate, UUID> {

  Optional<Compensate> findCompensateByCustomerId(Long id);

}
