package service.impl;

import bean.Module;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit tests for the {@link ModuleServicelmpl} class.
 */
class ModuleServicelmplTest {

    /**
     * Test case for the {@link ModuleServicelmpl#save(Module)} method.
     */
    @org.junit.jupiter.api.Test
    void save() {
        ModuleServicelmpl moduleServicelmpl = new ModuleServicelmpl();
        moduleServicelmpl.save(new Module("1", "College Students' Innovation and Entrepreneurship", "2022.6.10", "BUPT", "2020213301"));
        Module module = moduleServicelmpl.get(1, "2020213301");
        assertEquals("College Students' Innovation and Entrepreneurship", module.getMoName());
    }

    /**
     * Test case for the {@link ModuleServicelmpl#delete(String, String)} method.
     */
    @org.junit.jupiter.api.Test
    void delete() {
        ModuleServicelmpl moduleServicelmpl = new ModuleServicelmpl();
        moduleServicelmpl.delete("2020213301", "1");
        assertNull(moduleServicelmpl.get(1, "2020213301"));
    }

    /**
     * Test case for the {@link ModuleServicelmpl#update(Module)} method.
     */
    @org.junit.jupiter.api.Test
    void update() {
        // Add test case for the update method
    }

    /**
     * Test case for the {@link ModuleServicelmpl#get(int, String)} method.
     */
    @org.junit.jupiter.api.Test
    void get() {
        Module module = new ModuleServicelmpl().get(1, String.valueOf(2020213301));
        String objStr = String.join(",", module.getMoID() + "," + module.getMoName()
                + "," + module.getMoTime() + "," + module.getMoPosition() + "," + module.getUsername());
        String str = "1,College Students' Innovation and Entrepreneurship,2022.6.10,BUPT,2020213301";
        assertEquals(str, objStr);
    }

    /**
     * Test case for the {@link ModuleServicelmpl#getAll(String)} method.
     */
    @org.junit.jupiter.api.Test
    void getAll() {
        List<Module> modules = new ModuleServicelmpl().getAll(String.valueOf(2020213301));
        Module module1 = new ModuleServicelmpl().get(1, String.valueOf(2020213301));
        Module module2 = new ModuleServicelmpl().get(2, String.valueOf(2020213301));
        assertEquals(String.valueOf(module1), String.valueOf(modules.get(1)));
        //assertEquals(String.valueOf(module2), String.valueOf(modules.get(1)));
    }
}
