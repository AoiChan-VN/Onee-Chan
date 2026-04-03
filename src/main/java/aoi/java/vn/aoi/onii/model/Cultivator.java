package vn.aoi.onii.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cultivator {

    private UUID uuid;

    private String realm;     // "Luyện khí", "Trúc cơ", ...
    private int level;        // Level hiện tại
    private double exp;       // EXP hiện tại

    private long lastUpdated; // dùng cho cache / autosave
}
