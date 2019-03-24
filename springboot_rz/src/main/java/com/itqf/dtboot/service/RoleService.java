package com.itqf.dtboot.service;

import java.util.List;

public interface RoleService {

    public List<String> findRoleByUserId(long userId);

}
