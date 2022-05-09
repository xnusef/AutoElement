package me.TEXAPlayer.AutoElement;

import java.util.List;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.Element.SubElement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;

public class Methods
{
    private Main main;

    public Methods(Main plugin)
    {
        main = plugin;
    }


    private boolean GivePlayerElement(BendingPlayer bPlayer, Element element)
    {
        if (!bPlayer.hasElement(element))
            bPlayer.addElement(element);
        for (final SubElement sub : Element.getAllSubElements())
            if (bPlayer.hasElement(sub.getParentElement()) && !bPlayer.hasSubElement(sub) && !sub.equals(Element.SubElement.BLUE_FIRE))
                bPlayer.addSubElement(sub);
        GeneralMethods.saveElements(bPlayer);
        GeneralMethods.saveSubElements(bPlayer);
        return true;
    }

    private boolean AddToGroup(Player player, String group)
    {
        LuckPerms luckPerms = Bukkit.getServer().getServicesManager().load(LuckPerms.class);

        Group newGroup = luckPerms.getGroupManager().getGroup(group.toLowerCase());
        String playerName = player.getName();
        if (newGroup == null || playerName == null)
            return false;    
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) 
            return false;
        InheritanceNode node = InheritanceNode.builder(group.toLowerCase()).build();
        DataMutateResult result = user.data().add(node);
        if (result == DataMutateResult.FAIL)
            return false;
        luckPerms.getUserManager().saveUser(user);
        return true;
    }

    public boolean RemoveFromGroup(Player player, String group)
    {
        LuckPerms luckPerms = Bukkit.getServer().getServicesManager().load(LuckPerms.class);

        Group newGroup = luckPerms.getGroupManager().getGroup(group.toLowerCase());
        String playerName = player.getName();
        if (newGroup == null || playerName == null)
            return false;    
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) 
            return false;
        InheritanceNode node = InheritanceNode.builder(group.toLowerCase()).build();
        DataMutateResult result = user.data().remove(node);
        if (result == DataMutateResult.FAIL)
            return false;
        luckPerms.getUserManager().saveUser(user);
        return true;
    }

    public boolean RemoveGroup(Player player, BendingPlayer bPlayer)
    {
        if (bPlayer.hasElement(Element.getElement("water")))
            return RemoveFromGroup(player, "agua");

        if (bPlayer.hasElement(Element.getElement("earth")))
            return RemoveFromGroup(player, "tierra");

        if (bPlayer.hasElement(Element.getElement("fire")))
            return RemoveFromGroup(player, "fuego");

        if (bPlayer.hasElement(Element.getElement("air")))
            return RemoveFromGroup(player, "aire");

        if (bPlayer.hasElement(Element.getElement("chi")))
            return RemoveFromGroup(player, "chi");
        return false;
    }

    public boolean ClearElement(BendingPlayer bPlayer)
    {
        bPlayer.getElements().clear();
		GeneralMethods.saveElements(bPlayer);
		bPlayer.getSubElements().clear();
        GeneralMethods.saveSubElements(bPlayer);
        GeneralMethods.removeUnusableAbilities(bPlayer.getName());
        return true;
    }

    public boolean SetPlayerElement(Player player, String element)
    {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        if (bPlayer == null)
        {
            GeneralMethods.createBendingPlayer(player.getUniqueId(), player.getName());
            bPlayer = BendingPlayer.getBendingPlayer(player);
        }
        Element e = Element.getElement(element);
        boolean elementGiven = GivePlayerElement(bPlayer, e);
        boolean addedToGroup = AddToGroup(player, ElemetToString(bPlayer));

        return elementGiven && addedToGroup;
    }

    public String ElemetToString(BendingPlayer bPlayer)
    {
        if (bPlayer.hasElement(Element.getElement("water")))
            return "agua";

        if (bPlayer.hasElement(Element.getElement("earth")))
            return "tierra";

        if (bPlayer.hasElement(Element.getElement("fire")))
            return "fuego";

        if (bPlayer.hasElement(Element.getElement("air")))
            return "aire";

        if (bPlayer.hasElement(Element.getElement("chi")))
            return "chi";
        return null;
    }

    public boolean isAvatar(Player player)
    {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
        int elementCount = 0;
        for (Element e : Element.getAllElements())
            if (bPlayer.hasElement(e))
                elementCount++;

        if (elementCount <= 1)
            return false;
        return true;
    }

    public void ThrowError(String err)
    {
        Bukkit.getLogger().warning(err);
    }

    public void SendMessages(Player player, List<String> configMsgs)
    {
        if (!configMsgs.isEmpty())
            for (String parameterizedMsg : configMsgs) 
            {
                String msg = parameterizedMsg.replace("&", "§");
                String prefix = main.config.getString("prefix").replace("&", "§") + " ";
                player.sendMessage(prefix + msg);
            }
    }

    public void SendConsoleMessages(List<String> configMsgs)
    {
        if (!configMsgs.isEmpty())
            for (String parameterizedMsg : configMsgs) 
            {
                String msg = parameterizedMsg.replace("&", "");
                Bukkit.getServer().getLogger().warning
                (msg);
            }
    }
}
