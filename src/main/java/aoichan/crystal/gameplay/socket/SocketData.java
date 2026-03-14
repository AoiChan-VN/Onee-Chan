package aoichan.crystal.gameplay.socket;

import java.util.ArrayList;
import java.util.List;

public class SocketData {

    private final List<String> gems;

    public SocketData(int size) {

        // 【!】Code: tạo list gem theo số slot
        this.gems = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            gems.add(null);
        }

    }

    public List<String> getGems() {
        return gems;
    }

    public void setGem(int slot, String gemId) {

        // 【!】Code: set gem vào slot
        if (slot < 0 || slot >= gems.size()) return;

        gems.set(slot, gemId);

    }

    public String getGem(int slot) {

        if (slot < 0 || slot >= gems.size()) return null;

        return gems.get(slot);

    }

    public int size() {
        return gems.size();
    }

}
