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
    private int level;        // Tu vi hiện tại ( sơ,trung,.. )
    private double exp;       // Linh khí hiện tại

    private long lastUpdated; // dùng cho cache / autosave
}
