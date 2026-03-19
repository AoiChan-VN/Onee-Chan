package aoichan.service.data;

public class DirtyFlag {

    private boolean dirty;

    public void mark() {
        dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void clear() {
        dirty = false;
    }
} 
