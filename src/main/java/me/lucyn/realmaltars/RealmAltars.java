package me.lucyn.realmaltars;

import me.lucyn.realmaltars.effects.BaseBlessing;
import me.lucyn.realmaltars.effects.EnchantedSteed;
import me.lucyn.realmaltars.effects.Glide;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;


public final class RealmAltars extends JavaPlugin implements Listener {

    public Map<Location, String> cauldronList;
    public Map<String, String> effectList;
    public Map<String, BaseBlessing> blessingList;
    ArrayList<ItemStack> tier1;
    ArrayList<ItemStack> tier2;
    ArrayList<ItemStack> tier3;
    ArrayList[] tiers;

    @Override
    public void onEnable() {
        this.cauldronList = new HashMap<>();
        // Plugin startup logic

        tier1 = new ArrayList<>(Arrays.asList(new ItemStack(Material.DIAMOND, 1), new ItemStack(Material.IRON_BLOCK, 10), new ItemStack(Material.GOLD_BLOCK, 10), new ItemStack(Material.EMERALD, 10), new ItemStack(Material.GOLDEN_APPLE, 1)));
        tier2 = new ArrayList<>(Arrays.asList(new ItemStack(Material.NETHERITE_INGOT, 1), new ItemStack(Material.DIAMOND_BLOCK, 1), new ItemStack(Material.DRAGON_HEAD, 8)));
        tier3 = new ArrayList<>(Arrays.asList(new ItemStack(Material.NETHERITE_BLOCK, 1), new ItemStack(Material.NETHER_STAR, 1), new ItemStack(Material.BEACON, 1), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE)));

        tiers = new ArrayList[]{tier1, tier2, tier3};

        effectList = new HashMap<>();

        Glide glide = new Glide(this);
        EnchantedSteed enchantedSteed = new EnchantedSteed(this);

        getServer().getPluginManager().registerEvents(glide, this);
        getServer().getPluginManager().registerEvents(enchantedSteed, this);

        this.blessingList = new HashMap<>();
        blessingList.put(glide.name , glide);
        blessingList.put(enchantedSteed.name, enchantedSteed);



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

    public void saveFiles() throws IOException {
        YamlConfiguration y = new YamlConfiguration();
        for (Map.Entry<Location, String> entry : cauldronList.entrySet()) {
            y.set(entry.getValue(), entry.getKey());
        }
        y.save(new File(getDataFolder(), "cauldronlist.yml"));
        YamlConfiguration y2 = new YamlConfiguration();
        for (Map.Entry<String, String> entry : effectList.entrySet()) {
            y2.set(entry.getKey(), entry.getValue());
        }
        y2.save(new File(getDataFolder(), "effectlist.yml"));
    }

    public void loadFiles() throws IOException, InvalidConfigurationException{
        YamlConfiguration y = new YamlConfiguration();
        y.load(new File(getDataFolder(), "cauldronlist.yml"));
        cauldronList = new HashMap<>();
        for (String key : y.getKeys(false)) {
            cauldronList.put(y.getLocation(key), key);
        }


        YamlConfiguration y2 = new YamlConfiguration();
        y2.load(new File(getDataFolder(), "effectlist.yml"));
        effectList = new HashMap<>();
        for (String key : y2.getKeys(false)) {
            effectList.put(key, y2.getString(key));
            }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(e.getItemDrop().getLocation().getBlock().getType() == Material.CAULDRON) {
            if(cauldronList.containsKey(e.getItemDrop().getLocation())){
                int tier = blessingList.get(cauldronList.get(e.getItemDrop().getLocation())).tier;
                if(tiers[tier - 1].contains(e.getItemDrop().getItemStack().getType())) {
                    e.getItemDrop().remove();
                    effectList.put(e.getPlayer().getName(), cauldronList.get(e.getItemDrop().getLocation()));
                }
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!label.equalsIgnoreCase("realmaltars")) return super.onCommand(sender, command, label, args);
        if(args.length > 0) return false;

        switch(args[0]) {
            case "setcauldron": {
                if(args.length < 2) return false;


                String name = args[1];
                if(((Player)sender).getLocation().getBlock().getType() == Material.CAULDRON) {
                    cauldronList.put(((Player) sender).getLocation(), name);
                    return true;
                }

                else{
                       sender.sendMessage("You must be in a cauldron to set a cauldron.");
                       return false;
                }

            }
            default: {}

        }
        return false;
    }
}


