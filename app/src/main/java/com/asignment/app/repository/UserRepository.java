package com.asignment.app.repository;

import com.asignment.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {


   User findByuName(String uName);
}
