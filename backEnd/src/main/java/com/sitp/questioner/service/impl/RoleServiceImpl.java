package com.sitp.questioner.service.impl;

import com.sitp.questioner.entity.Role;
import com.sitp.questioner.repository.RoleRepository;
import com.sitp.questioner.service.abs.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by qi on 2017/7/23.
 */
@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;
    // Field to help get User and Admin role
    @Value("${role.userID}")
    private Long userID;

    @Value("${role.adminID}")
    private Long adminID;

    @Override
    public Role getUserRole() {
        return roleRepository.findOne(userID);
    }

    @Override
    public Role getAdminRole() {
        return roleRepository.findOne(adminID);
    }
}
