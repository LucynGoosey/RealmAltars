package me.lucyn.realmaltars.effects;

import me.lucyn.realmaltars.RealmAltars;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.HashMap;
import java.util.Map;

public class SecondWind extends BaseBlessing {

    private RealmAltars plugin;

    private Map<Player, Long> cooldowns = new HashMap<>();

    public SecondWind(RealmAltars plugin) {
        super(2, "Second Wind");
        this.plugin = plugin;
    }



    @EventHandler
    public void onDeath(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(plugin.getBlessing(player) == id && (!cooldowns.containsKey(player) || cooldowns.get(player) < System.currentTimeMillis()) && (player.getHealth() - event.getFinalDamage() <= 0)) {
                event.setCancelled(true);

                player.playEffect(EntityEffect.TOTEM_RESURRECT);

                player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_HEALTH, 20, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 50, 4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 280, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 280, 1));

                int cooldown = 180000;
                cooldowns.put(player, System.currentTimeMillis() + cooldown);


            }


        }

    }











}
