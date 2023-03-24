package me.lucyn.realmaltars.effects;

import me.lucyn.realmaltars.RealmAltars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Glide extends BaseBlessing {

    private final RealmAltars plugin;

    public Glide(RealmAltars plugin) {
        super("glide", "Glide", 2);
        this.plugin = plugin;

    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent event) {
        if (plugin.effectList.containsKey(event.getPlayer().getName())) {
            if (plugin.effectList.get(event.getPlayer().getName()).equals(this.name)) {
                if (event.isSneaking()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 5, 0, true, false, false));
                            if (!event.getPlayer().isSneaking()) {
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 1);
                }
            }

        }
    }


}
