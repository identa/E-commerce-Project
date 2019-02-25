package com.dac.spring.repository;

import com.dac.spring.entity.RoleEntity;
import com.dac.spring.model.enums.RoleName;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Integer> {
    RoleEntity findById(int id);
    RoleEntity findByName(RoleName roleName);
}
