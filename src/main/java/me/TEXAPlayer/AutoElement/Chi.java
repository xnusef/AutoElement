package me.TEXAPlayer.AutoElement;

import com.projectkorra.projectkorra.BendingPlayer;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chi implements CommandExecutor
{
    private Methods m;
    private Main main;

    public Chi(Methods methods, Main plugin)
    {
        m = methods;
        main = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) 
    {
        if (!(sender instanceof Player))
        {
            m.SendConsoleMessages(main.config.getStringList("not-player-messages"));
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0 || !args[0].toLowerCase().equals("confirm"))
            m.SendMessages(player, main.config.getStringList("must-confirm-messages"));
        if (args.length > 1)
            m.SendMessages(player, main.config.getStringList("too-many-arguments-messages"));
        if (m.isAvatar(player))
            m.SendMessages(player, main.config.getStringList("sender-is-avatar-messages"));
        if (args.length > 1 || args.length == 0 || m.isAvatar(player) || !args[0].toLowerCase().equals("confirm"))
            return true;
        boolean success = false;
        if (args[0].toLowerCase().equals("confirm"))
            success = ExecuteChange(player);
        if (success != true)
            m.ThrowError("Something went wrong");
        else
            m.SendMessages(player, main.config.getStringList("chi-element"));
        return true;
    }

    private boolean ExecuteChange(Player player)
    {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        boolean removed = m.RemoveGroup(player, bPlayer);
        boolean cleared = m.ClearElement(bPlayer);
        boolean newElement = m.SetPlayerElement(player, "Chi");
        return (cleared && removed && newElement);
    }
}
