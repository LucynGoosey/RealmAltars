package me.lucyn.realmaltars.effects;

import me.lucyn.realmaltars.RealmAltars;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EnchantedSteed extends BaseBlessing {

    RealmAltars plugin;
    public EnchantedSteed(RealmAltars plugin) {
        super("steed", "Enchanted Steed", 3);
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {
        if(!plugin.effectList.containsKey(event.getPlayer().getName())) return;
        if(plugin.effectList.get(event.getPlayer().getName()) == this.name) {
            if(!event.hasItem()) return;
            if(event.getItem().getType() != Material.SADDLE) return;
            ZombieHorse horse = (ZombieHorse) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.ZOMBIE_HORSE);
            horse.setAdult();
            horse.setTamed(true);
            horse.setOwner(event.getPlayer());
            horse.getInventory().setSaddle(event.getItem());
            horse.addPassenger(event.getPlayer());
            event.getPlayer().getInventory().removeItem(event.getItem());
            event.getPlayer().updateInventory();
        }
    }
    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        if(event.getDismounted() instanceof ZombieHorse) {
            event.getDismounted().remove();
            ((Player) event.getEntity()).getInventory().addItem(new ItemStack(Material.SADDLE));
            ((Player) event.getEntity()).updateInventory();

        }
    }
}
