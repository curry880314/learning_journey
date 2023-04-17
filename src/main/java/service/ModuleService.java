package service;

import bean.Achievement;
import bean.Module;

import java.util.List;

public interface ModuleService {

    public void save(Module mod);

    public void delete(String adminID, String modID);

    public void update(Module mod);

    public Module get(int modID, String admin);

    public List<Module> getAll(String adminID);
}
