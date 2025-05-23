package com.adityagiri.ecommerce_app.repository;

import com.adityagiri.ecommerce_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByMobile(String mobile);
    User findByUsername(String username);
}
