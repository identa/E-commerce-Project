package com.example.boot_demo.repository;

import com.example.boot_demo.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    RoleEntity findById(int id);
}
