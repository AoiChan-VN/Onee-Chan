package aoichan.service;

import aoichan.service.module.ModuleService;

import java.util.ArrayList;
import java.util.List;

public class ServiceManager {

    private final List<IService> services = new ArrayList<>();

    private final ModuleService moduleService = new ModuleService();

    public void init() {
        // future auto register
        moduleService.enableAll();
    }

    public void shutdown() {
        moduleService.disableAll();

        for (IService s : services) {
            s.shutdown();
        }
    }
}
