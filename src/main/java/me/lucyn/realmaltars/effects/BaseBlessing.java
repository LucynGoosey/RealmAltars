package me.lucyn.realmaltars.effects;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BaseBlessing implements Listener {
    public final int id;
    public final String displayName;
    public final int tier;


    public BaseBlessing(int id, String displayName, int tier) {
        this.id = id;
        this.tier = tier;
        this.displayName = displayName;
    }



}
