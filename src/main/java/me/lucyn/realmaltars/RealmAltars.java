package me.lucyn.realmaltars;

import me.lucyn.fourthrealm.FourthRealmCore;
import me.lucyn.fourthrealm.RealmPlayer;
import me.lucyn.realmaltars.effects.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;


/*
TODO

Convert the following classes to use FourthRealmCore:

AltarListener

Animated tutorials

Change blessings to not need a sacrifice, they should be changeable at will.

Add the following blessings:
Sonic Boom - Warden Blast
Absorption - Dealing damage to entities grants health and saturation
Progressive Overload - The longer a player is alive, the more hearts they gain, up to 10 hearts extra. Resets on death
Dolphinborn - The player gains constant water breathing and dolphins grace.
Quick Toss - All projectiles are launched considerably further, item drops included.
Multi-Shot - All projectiles are multiplied
Magic Lantern - Summon a light source that follows you around. Make it nameable
Pioneer - Portable crafting and furnace
Blink - teleport to where the player is looking
Web Slinger - Launch cobwebs, swing from cobwebs
Undead Army - Summon an army of zombies and skeletons
Projectile Reflect - Spawn a shield that reflects projectiles

 */




public final class RealmAltars extends JavaPlugin {

    public Map<Location, Integer> cauldronList; //this list is used to store the locations of each blessings cauldron
    public BaseBlessing[] index = new BaseBlessing[12];
    public FourthRealmCore fourthRealmCore;


    @Override
    public void onEnable() {
        this.cauldronList = new HashMap<>();
        fourthRealmCore = (FourthRealmCore) this.getServer().getPluginManager().getPlugin("FourthRealmCore");


        Glide glide = new Glide(this);
        EnchantedSteed enchantedSteed = new EnchantedSteed(this);
        SecondWind secondWind = new SecondWind(this);

        getServer().getPluginManager().registerEvents(new AltarListener(this), this);

        getServer().getPluginManager().registerEvents(glide, this);
        getServer().getPluginManager().registerEvents(enchantedSteed, this);
        getServer().getPluginManager().registerEvents(secondWind, this);


        //initialize index
        index[glide.id] = glide;
        index[enchantedSteed.id] = enchantedSteed;
        index[secondWind.id] = secondWind;


        try{
            loadFiles();
        }catch (Exception e) {
            getServer().getLogger().info("Data files not found. New ones will be created.");
            cauldronList = new HashMap<>();

        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try{
            saveFiles();
        }catch (Exception e){
            e.printStackTrace();
        }
    }






    public void saveFiles() throws IOException {
        YamlConfiguration y = new YamlConfiguration();
        for (Map.Entry<Location, Integer> entry : cauldronList.entrySet()) {
            y.set(entry.getValue().toString(), entry.getKey());
        }
        y.save(new File(getDataFolder(), "cauldronlist.yml"));
    }

    public void loadFiles() throws IOException, InvalidConfigurationException {
        YamlConfiguration y = new YamlConfiguration();
        y.load(new File(getDataFolder(), "cauldronlist.yml"));
        cauldronList = new HashMap<>();
        for (String key : y.getKeys(false)) {
            cauldronList.put(y.getLocation(key), Integer.parseInt(key));
        }

    }

                       @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!label.equalsIgnoreCase("realmaltars")) return super.onCommand(sender, command, label, args);
        if (args.length < 2) return false;

        if (args[0].equals("setaltar")) {

            int name = Integer.parseInt(args[1]);
            if (((Player) sender).getTargetBlockExact(5).getType() == Material.HOPPER) { // checks for /realmaltars setcauldron [blessingName] and adds it to the cauldron list
                cauldronList.put(((Player) sender).getTargetBlockExact(5).getLocation(), name);
                try {
                    saveFiles();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                sender.sendMessage("You must be in a cauldron to set a cauldron.");
                return false;
            }
        } else if(args[0].equals("seteffect")){
            RealmPlayer rPlayer = fourthRealmCore.getPlayerData((Player) sender);
            rPlayer.blessingID = Integer.parseInt(args[1]);
            sender.sendMessage("Set effect to " + rPlayer.blessingID);
            fourthRealmCore.setPlayerData(rPlayer);
            return true;
        }
        return false;
    }




    public int getBlessing(Player player) {
        return FourthRealmCore.playerData.get(player).blessingID;
    }

}


