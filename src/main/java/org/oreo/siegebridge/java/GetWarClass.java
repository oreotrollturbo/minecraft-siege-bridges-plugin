package org.oreo.siegebridge.java;

import phonon.nodes.war.FlagWar;

public class GetWarClass {

    public static boolean isWarOn(){
        return FlagWar.INSTANCE.getEnabled$nodes();
    }
}
