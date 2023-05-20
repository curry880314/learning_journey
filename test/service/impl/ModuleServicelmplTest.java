package service.impl;

import bean.Module;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ModuleServicelmplTest {

    @org.junit.jupiter.api.Test
    void save() {
        ModuleServicelmpl moduleServicelmpl=new ModuleServicelmpl();
        moduleServicelmpl.save(new Module("1","College Students' Innovation and Entrepreneurship","2022.6.10","BUPT","2020213301"));
        Module module=moduleServicelmpl.get(1,"2020213301");
        assertEquals("College Students' Innovation and Entrepreneurship",module.getMoName());
    }

    @org.junit.jupiter.api.Test
    void delete() {
        ModuleServicelmpl moduleServicelmpl=new ModuleServicelmpl();
        moduleServicelmpl.delete("2020213301","1");
        assertNull(moduleServicelmpl.get(1,"2020213301"));
    }

    @org.junit.jupiter.api.Test
    void update() {
    }

    @org.junit.jupiter.api.Test
    void get() {
        Module module = new ModuleServicelmpl().get(1, String.valueOf(2020213301));
        String objStr= String.join(",",module.getMoID()+","+module.getMoName()
                +","+module.getMoTime()+"," +module.getMoPosition()+","+module.getUsername());
        String str="1,College Students' Innovation and Entrepreneurship,2022.6.10,BUPT,2020213301";
        assertEquals(str,objStr);
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        List<Module> modules=new ModuleServicelmpl().getAll(String.valueOf(2020213301));
        Module module1 = new ModuleServicelmpl().get(1, String.valueOf(2020213301));
        Module module2 = new ModuleServicelmpl().get(2, String.valueOf(2020213301));
        assertEquals(String.valueOf(module1),String.valueOf(modules.get(1)));
        //assertEquals(String.valueOf(module2),String.valueOf(modules.get(1)));

    }


}