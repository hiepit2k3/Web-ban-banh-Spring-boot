package com.tiembanhhoangtube.Repository;

import com.tiembanhhoangtube.entity.Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdertailRepository extends JpaRepository<Orderdetail,Long> {
}
