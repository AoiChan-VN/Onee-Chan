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

    private String realm;
    private int level;
    private double exp;

    private long lastUpdated;
}
