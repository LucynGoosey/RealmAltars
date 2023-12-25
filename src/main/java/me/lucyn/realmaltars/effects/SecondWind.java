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
        super(2, "Second Wind", 3);
        this.plugin = plugin;
    }



    @EventHandler
    public void onDeath(EntityDamageEvent event) {

        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if(plugin.effectList.containsKey(player) && plugin.effectList.get(player) == id && (!cooldowns.containsKey(player) || cooldowns.get(player) < System.currentTimeMillis()) && (player.getHealth() - event.getFinalDamage() <= 0)) {
                event.setCancelled();

                player.playEffect(EntityEffect.TOTEM_RESURRECT);

                player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2, 4));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 14, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 14, 1));

                int cooldown = 180000;
                cooldowns.put(player, System.currentTimeMillis() + cooldown);


            }


        }

    }











}
