package me.lucyn.realmaltars.effects;


import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BaseBlessing implements Listener {
    public final int id;
    public final String displayName;
    public Block block;



    public BaseBlessing(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public void setBlock(Block block) {
        this.block = block;
    }



}
