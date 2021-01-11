package com.onlineordering.repository;

import com.onlineordering.domain.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemRepository extends JpaRepository<Orderitem,Long> {
}
