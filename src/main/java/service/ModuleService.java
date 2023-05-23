package service;

import bean.Achievement;
import bean.Module;

import java.util.List;

/**
 * The ModuleService interface provides operations to manage modules.
 */
public interface ModuleService {
    /**
     * Saves the module.
     *
     * @param mod the module to save
     */
    void save(Module mod);

    /**
     * Deletes the module with the specified ID, associated with the provided admin ID.
     *
     * @param adminID the ID of the admin
     * @param modID   the ID of the module to delete
     */
    void delete(String adminID, String modID);

    /**
     * Updates the module.
     *
     * @param mod the module to update
     */
    void update(Module mod);

    /**
     * Get the module with the specified ID, associated with the provided admin ID.
     *
     * @param modID the ID of the module
     * @param admin the admin associated with the module
     * @return the Module object with the given ID and admin, or null if not found
     */
    Module get(int modID, String admin);

    /**
     * Get all modules associated with the provided admin ID.
     *
     * @param adminID the ID of the admin
     * @return a list of Module objects associated with the admin ID
     */
    List<Module> getAll(String adminID);
}

