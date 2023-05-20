package service;

import bean.Role;

import java.util.List;

public interface RoleService {
    List<Role>getAll(String adminID);

    void save(Role role);

    void delete(String rId,String username);

    void update(String rId, Role role);

    Role get(String rID,String radmin);
}
