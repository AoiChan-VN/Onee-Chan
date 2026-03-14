package aoichan.crystal.gameplay.gem;

import java.util.Map;

public class Gem {

    private final String id;
    private final String name;
    private final Map<GemStat, Double> stats;

    public Gem(String id, String name, Map<GemStat, Double> stats) {

        // 【!】Code: id của gem
        this.id = id;

        // 【!】Code: tên hiển thị
        this.name = name;

        // 【!】Code: stat của gem
        this.stats = stats;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<GemStat, Double> getStats() {
        return stats;
    }

}
