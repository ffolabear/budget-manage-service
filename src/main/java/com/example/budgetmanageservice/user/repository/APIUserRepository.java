package com.example.budgetmanageservice.user.repository;

import com.example.budgetmanageservice.user.entity.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIUserRepository extends JpaRepository<APIUser, String> {

}
