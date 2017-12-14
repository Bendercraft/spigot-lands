package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;
import world.avatarhorizon.spigot.lands.models.Land;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Logger;

public class ListCommand extends LandSubCommand
{
    public ListCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("list", resourceBundle, logger, landsManager);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        if (!sender.hasPermission("lands.admin.list"))
        {
            throw new LandCommandException(messages.getString("error.no_permission"));
        }

        World world = null;

        if (args.size() > 0)
        {
            String name = args.remove(0);
            world = Bukkit.getWorld(name);
        }

        if (!(sender instanceof Player) && world == null)
        {
            throw new LandCommandException(messages.getString("error.player_requirement"));
        }

        if (world == null)
        {
            world = ((Player)sender).getWorld();
        }
        Map<UUID, Land> lands = landsManager.getLandsForWorld(world);
        if (lands == null || lands.isEmpty())
        {
            sender.sendMessage(messages.getString("success.list.no_land"));
        }
        else
        {
            StringBuilder b = new StringBuilder();
            for (Land land : lands.values())
            {
                b.append(land.getName());
                b.append(", ");
            }
            int i = b.lastIndexOf(", ");
            b.delete(i, i+2);

            sender.sendMessage(b.toString());
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.list";
    }
}
