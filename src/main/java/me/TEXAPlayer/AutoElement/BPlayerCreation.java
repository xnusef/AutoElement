package me.TEXAPlayer.AutoElement;

import com.projectkorra.projectkorra.event.BendingPlayerCreationEvent;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BPlayerCreation implements Listener 
{
    private Methods m;
    private Main main;

    public BPlayerCreation(Methods methods, Main plugin)
    {
        m = methods;
        main = plugin;
    }

    @EventHandler
    public void OnBPlayerCreation(BendingPlayerCreationEvent event)
    {
        Player player = event.getBendingPlayer().getPlayer();
        if (player.hasPlayedBefore())
            return;
        RandomElement(player);
    }

    public void RandomElement(Player player)
    {
        int randomNum = (int) Math.floor(Math.random() * 4); 
        switch (randomNum)
        {
            case 0:
                m.SetPlayerElement(player, "Water");
                m.SendMessages(player, main.config.getStringList("water-element"));
                break;
            case 1:
                m.SetPlayerElement(player, "Fire");
                m.SendMessages(player, main.config.getStringList("fire-element"));
                break;
            case 2:
                m.SetPlayerElement(player, "Earth");
                m.SendMessages(player, main.config.getStringList("earth-element"));
                break;
            case 3:
                m.SetPlayerElement(player, "Air");
                m.SendMessages(player, main.config.getStringList("air-element"));
                break;
            default:
                m.ThrowError("El valor se fue del rango");
                break;
        }
    }
}
