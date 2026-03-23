package vn.aoi.onii.classsystem;

public interface PlayerClass {

    String getId();

    String getName();

    void onSelect(ClassContext ctx);

    void onRemove(ClassContext ctx);
}
