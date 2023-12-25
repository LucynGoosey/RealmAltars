package me.lucyn.realmaltars;

import me.lucyn.realmaltars.data.CauldronListener;
import me.lucyn.realmaltars.effects.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;


public final class RealmAltars extends JavaPlugin implements Listener {

    public Map<Location, Integer> cauldronList; //this list is used to store the locations of each blessings cauldron
    public Map<Player, Integer> effectList; //this list is used to keep track of which player has which blessing
    private Material[] tiers;
    public BaseBlessing[] index = new BaseBlessing[12];


    @Override
    public void onEnable() {
        this.cauldronList = new HashMap<>();

        Material tier1 = Material.GOLD_INGOT;
        Material tier2 = Material.DIAMOND;
        Material tier3 = Material.DIAMOND_BLOCK;


        setTiers(new Material[]{tier1, tier2, tier3}); //these are arrays of items that can be sacrificed for each tier of blessing

        Glide glide = new Glide(this);
        EnchantedSteed enchantedSteed = new EnchantedSteed(this);
        SecondWind secondWind = new SecondWind(this);


        getServer().getPluginManager().registerEvents(glide, this);
        getServer().getPluginManager().registerEvents(enchantedSteed, this);
        getServer().getPluginManager().registerEvents(secondWind, this);

        getServer().getPluginManager().registerEvents(new CauldronListener(this), this);

        effectList = new HashMap<>();

        index[0] = glide;
        index[1] = enchantedSteed;
        index[2] = secondWind;


        index[11] = new WallJump();

        try{
            loadFiles();
        }catch (Exception e) {
            getServer().getLogger().info("Data files not found. New ones will be created.");
            cauldronList = new HashMap<>();
            effectList  = new HashMap<>();

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

    public void saveID(Player player, int id) throws IOException {
    YamlConfiguration y = new YamlConfiguration();
    y.set("id", id);
    y.save(new File(getDataFolder(), player.getUniqueId() + ".yml"));
}


public int loadID(Player player) throws IOException, InvalidConfigurationException {
        YamlConfiguration y = new YamlConfiguration();
        y.load(new File(getDataFolder(), player.getUniqueId() + ".yml"));
            return y.getInt("id");

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

        if (args[0].equals("setcauldron")) {



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
            effectList.put((Player) sender, Integer.parseInt(args[1]));

        }
        return false;
    }

    public Material[] getTiers() {
        return tiers;
    }

    public void setTiers(Material[] tiers) {
        this.tiers = tiers;
    }
}


