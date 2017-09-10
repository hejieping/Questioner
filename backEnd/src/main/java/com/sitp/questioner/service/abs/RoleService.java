package com.sitp.questioner.service.abs;

import com.sitp.questioner.entity.Role;

/**
 * Created by qi on 2017/7/23.
 */
public interface RoleService {
    Role getUserRole();
    Role getAdminRole();
}
