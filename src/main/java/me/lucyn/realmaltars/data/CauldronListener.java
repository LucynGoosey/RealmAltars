package me.lucyn.realmaltars.data;

import me.lucyn.realmaltars.RealmAltars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.Objects;

public class CauldronListener implements Listener {

    RealmAltars plugin;

    public CauldronListener(RealmAltars plugin) {
        this.plugin = plugin;
    }
    //hopper goes under the cauldron
    @EventHandler
    public void onPickup(InventoryPickupItemEvent event) {
        Player player = plugin.getServer().getPlayer(Objects.requireNonNull(event.getItem().getThrower())); //get the player object of the thrower
        if(plugin.cauldronList.containsKey(event.getInventory().getLocation())) { // checks if the location matches one on the cauldron list
            int id = plugin.cauldronList.get(event.getInventory().getLocation()); //gets the blessing id from the location

            if(plugin.getBlessing(player) == id) { //checks if the player has the effect and cancels if they do
                event.setCancelled(true);
                return;
            }

            if(plugin.getTiers()[plugin.index[id].tier - 1] == event.getItem().getItemStack().getType()) {





                assert player != null;
                player.sendTitle(ChatColor.GOLD + plugin.index[id].displayName + ChatColor.BOLD, "You gained a blessing.", 10, 100, 10);
                event.getItem().getItemStack().setAmount(event.getItem().getItemStack().getAmount() - 1);
                event.setCancelled(true);

                try {
                    plugin.saveID(player, id);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                event.setCancelled(true);
            }




        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        try{

            int id = plugin.loadID(event.getPlayer());

            plugin.effectList.put(event.getPlayer(), id);

            event.getPlayer().sendMessage(ChatColor.YELLOW + "Current Blessing: " + plugin.index[id].displayName);
        }
        catch(Exception e) {
            e.printStackTrace();
        }




    }

}
