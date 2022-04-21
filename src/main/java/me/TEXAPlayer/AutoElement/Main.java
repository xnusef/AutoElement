package me.TEXAPlayer.AutoElement;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
    private Server server;
    private PluginManager pm;
    private Methods m;
    private Join join;

    @Override
    public void onEnable() 
    {
        server = Bukkit.getServer();
        pm = server.getPluginManager();

        m = new Methods();
        join = new Join(m);
        pm.registerEvents(join, this);
    }
}