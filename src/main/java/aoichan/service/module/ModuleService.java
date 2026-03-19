package aoichan.service.module;

import aoichan.module.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleService {

    private final List<Module> modules = new ArrayList<>();

    public void register(Module module) {
        modules.add(module);
    }

    public void enableAll() {
        for (Module m : modules) {
            m.load();
            m.enable();
        }
    }

    public void disableAll() {
        for (Module m : modules) {
            m.disable();
        }
    }
} 
