package me.lucyn.realmaltars.effects;

import me.lucyn.realmaltars.RealmAltars;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.ZombieHorse;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class EnchantedSteed extends BaseBlessing {
    private final RealmAltars plugin;
    public EnchantedSteed(RealmAltars plugin) {
        super(1, "Enchanted Steed", 3);
        this.plugin = plugin;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent event) {

        if(plugin.getBlessing(event.getPlayer()) == this.id) {
            if(!event.hasItem()) return;
            if(event.getItem().getType() != Material.SADDLE) return;
            ZombieHorse horse = (ZombieHorse) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation(), EntityType.ZOMBIE_HORSE);
            horse.setAdult();
            horse.setTamed(true);
            horse.setOwner(event.getPlayer());
            horse.getInventory().setSaddle(event.getItem());
            horse.addPassenger(event.getPlayer());
            event.getPlayer().getInventory().removeItem(event.getItem());
        }
    }
    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        if(event.getDismounted() instanceof ZombieHorse) {
            event.getDismounted().remove();
            ((Player) event.getEntity()).getInventory().addItem(new ItemStack(Material.SADDLE));

        }
    }
}
