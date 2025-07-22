package org.example.customerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "compensate")
public class Compensate {
  @Id
  @Column(name = "requestId")
  private UUID requestId;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "product_count")
  private Integer productCount;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "customer_money")
  private Integer customerMoney;

}
