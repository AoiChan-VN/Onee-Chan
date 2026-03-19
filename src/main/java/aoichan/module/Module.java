package aoichan.module;

public abstract class Module {

    private ModuleState state;

    public void load() {}
    public void enable() {}
    public void disable() {}

    public void setState(ModuleState state) {
        this.state = state;
    }

    public ModuleState getState() {
        return state;
    }
} 
