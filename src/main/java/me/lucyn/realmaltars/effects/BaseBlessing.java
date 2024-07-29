package me.lucyn.realmaltars.effects;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BaseBlessing implements Listener {
    public final int id;
    public final String displayName;
    public Location location;



    public BaseBlessing(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public void setLocation(Location location) {
        this.location = location;
    }



}
