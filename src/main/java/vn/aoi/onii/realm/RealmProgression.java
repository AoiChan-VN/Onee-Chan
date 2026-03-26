package vn.aoi.onii.realm;

import vn.aoi.onii.enums.Realm;
import vn.aoi.onii.enums.Stage;

public class RealmProgression {

    public static int getRequiredExp(Realm realm, Stage stage) {

        return switch (stage) {
            case SO_KY -> 100;
            case TRUNG_KY -> 300;
            case HAU_KY -> 600;
            case DINH_PHONG -> 1000;
        };
    }

    public static Stage nextStage(Stage stage) {
        return switch (stage) {
            case SO_KY -> Stage.TRUNG_KY;
            case TRUNG_KY -> Stage.HAU_KY;
            case HAU_KY -> Stage.DINH_PHONG;
            default -> null;
        };
    }

    public static Realm nextRealm(Realm realm) {
        int next = realm.ordinal() + 1;
        if (next >= Realm.values().length) return null;
        return Realm.values()[next];
    }
} 
