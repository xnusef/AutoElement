package me.TEXAPlayer.AutoElement;

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
    public void GivePlayerElement(BendingPlayer bPlayer, Element element)
    {
        if (!bPlayer.hasElement(element))
            bPlayer.addElement(element);
        for (final SubElement sub : Element.getAllSubElements())
            if (bPlayer.hasElement(sub.getParentElement()) && !bPlayer.hasSubElement(sub) && !sub.equals(Element.SubElement.BLUE_FIRE))
                bPlayer.addSubElement(sub);
        GeneralMethods.saveElements(bPlayer);
        GeneralMethods.saveSubElements(bPlayer);
    }

    public boolean AddToGroup(Player player, String group)
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
}
