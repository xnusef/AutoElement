package me.TEXAPlayer.AutoElement;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private File configFile;
    public FileConfiguration config;

    private Server server;
    private PluginManager pm;
    private Methods m;
    private BPlayerCreation bpCreation;

    @Override
    public void onEnable() 
    {
        LoadConfig();
        server = Bukkit.getServer();
        pm = server.getPluginManager();

        m = new Methods(this);
        bpCreation = new BPlayerCreation(m, this);
        pm.registerEvents(bpCreation, this);
        this.getCommand("chi").setExecutor(new Chi(m, this));
    }

    private void LoadConfig()
    {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists())
        {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        try { config.load(configFile); }
        catch (IOException | InvalidConfigurationException e) { e.printStackTrace(); }
    }
}