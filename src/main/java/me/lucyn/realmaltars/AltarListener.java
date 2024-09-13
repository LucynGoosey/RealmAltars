package me.lucyn.realmaltars;

import me.lucyn.fourthrealm.RealmPlayer;
import me.lucyn.realmaltars.effects.BaseBlessing;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;


public class AltarListener implements Listener {



    RealmAltars realmAltars;

    public AltarListener(RealmAltars plugin) {
        this.realmAltars = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if(!Tag.BUTTONS.isTagged(event.getMaterial())) return;

        boolean match = false;
        BaseBlessing baseBlessing = null;
        for(BaseBlessing b : realmAltars.index) {
            if(event.getClickedBlock() == b.block) {
                baseBlessing = b;
                match = true;

                //play tutorial animation
            }

        }
        if(!match) return;

        RealmPlayer player = realmAltars.fourthRealmCore.getPlayerData(event.getPlayer());
        player.blessingID = baseBlessing.id;



    }

}
