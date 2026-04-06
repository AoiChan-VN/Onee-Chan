package vn.aoi.onii.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Realm {

    private String name;
    private String nextRank;

    private int maxLevel;
    private boolean tribulation;

    private int duration; // dùng cho tribulation

    // level -> exp required
    private Map<Integer, LevelData> levels;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LevelData {
        private double expRequired;
        private String phase; // Sơ, Trung, Hậu, Viên mãn
    }
} 
