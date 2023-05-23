package service;

import bean.Role;

import java.util.List;

/**
 * The RoleService interface provides operations to manage roles.
 */
public interface RoleService {
    /**
     * Get all roles associated with the provided admin ID.
     *
     * @param adminID the admin ID to search for
     * @return a list of Role objects associated with the admin ID, or null if not found
     */
    List<Role> getAll(String adminID);

    /**
     * Saves a role.
     *
     * @param role the role to save
     */
    void save(Role role);

    /**
     * Deletes a role based on the provided role ID and username.
     *
     * @param rId      the ID of the role to delete
     * @param username the username associated with the role
     */
    void delete(String rId, String username);

    /**
     * Updates a role based on the provided role ID and role information.
     *
     * @param rId   the ID of the role to update
     * @param role  the updated role information
     */
    void update(String rId, Role role);

    /**
     * Get a role based on the provided role ID and admin username.
     *
     * @param rID    the ID of the role to retrieve
     * @param radmin the admin username associated with the role
     * @return the Role object matching the ID and admin username, or null if not found
     */
    Role get(String rID, String radmin);
}

