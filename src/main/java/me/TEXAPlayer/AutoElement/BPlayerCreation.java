package me.TEXAPlayer.AutoElement;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.event.BendingPlayerCreationEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BPlayerCreation implements Listener 
{
    private Methods m;

    public BPlayerCreation(Methods methods)
    {
        m = methods;
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
                SetPlayerElement(player, "Water");
                break;
            case 1:
                SetPlayerElement(player, "Fire");
                break;
            case 2:
                SetPlayerElement(player, "Earth");
                break;
            case 3:
                SetPlayerElement(player, "Air");
                break;
            default:
                ThrowError("El valor se fue del rango");
                break;
        }
    }
    
    private void SetPlayerElement(Player player, String element)
    {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null)
        {
            GeneralMethods.createBendingPlayer(player.getUniqueId(), player.getName());
            bPlayer = BendingPlayer.getBendingPlayer(player);
        }
        Element e = Element.getElement(element);
        m.GivePlayerElement(bPlayer, e);
        SetPlayerGroup(player, element);

    }

    private void SetPlayerGroup(Player player, String element)
    {
        switch (element)
        {
            case "Water":
                m.AddToGroup(player, "agua");
                break;
            case "Fire":
                m.AddToGroup(player, "fuego");
                break;
            case "Air":
                m.AddToGroup(player, "aire");
                break;
            case "Earth":
                m.AddToGroup(player, "tierra");
                break;
            default:
                ThrowError("El elemento es erroneo");
                break;
        }
    }

    private void ThrowError(String err)
    {
        Bukkit.getLogger().warning(err);
    }
}
