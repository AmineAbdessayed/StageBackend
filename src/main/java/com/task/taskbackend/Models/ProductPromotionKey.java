package com.task.taskbackend.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ProductPromotionKey implements Serializable  {

    @Column(name = "product_id")
private Long productId;

@Column(name = "promotion_id")
private Long promotionId;

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductPromotionKey that = (ProductPromotionKey) o;
    return Objects.equals(productId, that.productId) && Objects.equals(promotionId, that.promotionId);
}

@Override
public int hashCode() {
    return Objects.hash(productId, promotionId);
}
}
