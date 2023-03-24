package me.lucyn.realmaltars.effects;

import org.bukkit.event.Listener;

public class BaseBlessing implements Listener {
    public final String name;
    public final String displayName;
    public final int tier;


    public BaseBlessing(String name, String displayName, int tier) {
        this.name = name;
        this.tier = tier;
        this.displayName = displayName;
    }

}
