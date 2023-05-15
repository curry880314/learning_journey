package service.impl;

import bean.Role;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleServiceimplTest {

    @Test
    void get() {
        Role role=new RoleServiceimpl().get(String.valueOf(1),String.valueOf(2020213301));
        String objStr= String.join(",",role.getRoID()+","+role.getRoName()
                +","+role.getRoTimeStart(),role.getRoTimeEnd());
        String str="1,Captain of the National Basketball Team,2020.6.24,2023.6.24";
        assertEquals(str,objStr);
    }

    @Test
    void getAll() {
        List<Role> roles=new RoleServiceimpl().getAll(String.valueOf(2020213301));
        Role role1=new RoleServiceimpl().get("1","2020213301");
        Role role2=new RoleServiceimpl().get("3","2020213301");
        assertEquals(String.valueOf(role1),String.valueOf(roles.get(2)));
        assertEquals(String.valueOf(role2),String.valueOf(roles.get(1)));
    }
}