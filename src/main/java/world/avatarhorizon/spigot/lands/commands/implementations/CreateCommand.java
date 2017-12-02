package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;
import world.avatarhorizon.spigot.lands.exceptions.LandCreationException;
import world.avatarhorizon.spigot.lands.models.Land;

import java.util.List;
import java.util.ResourceBundle;


public class CreateCommand extends LandSubCommand
{
    public CreateCommand(ResourceBundle resourceBundle, LandsManager landsManager)
    {
        super("create", resourceBundle, landsManager);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        if (!sender.hasPermission("lands.admin.create"))
        {
            throw new LandCommandException(messages.getString("error.no_permission"));
        }

        if (!(sender instanceof Player))
        {
            throw new LandCommandException(messages.getString("error.player_requirement"));
        }

        String name = String.join(" ", args).replace('"',' ').trim();
        if (name == null || name.equals(""))
        {
            throw new LandCommandException(messages.getString("error.creation.invalid_name"));
        }

        try
        {
            Player p = (Player) sender;
            Land land = new Land();
            land.setName(name);
            landsManager.addLandToWorld(p.getWorld(), land);
            sender.sendMessage(ChatColor.GREEN + messages.getString("success.creation"));
        }
        catch (LandCreationException e)
        {
            short number = e.getCauseNumber();
            switch(number)
            {
                case LandCreationException.CAUSE_NULL_WORLD:
                    throw new LandCommandException(messages.getString("error.creation.null_world"));
                case LandCreationException.CAUSE_NULL_LAND:
                    throw new LandCommandException(messages.getString("error.creation.null_land"));
                case LandCreationException.CAUSE_LAND_NO_NAME:
                    throw new LandCommandException(messages.getString("error.creation.land_no_name"));
                case LandCreationException.CAUSE_LAND_NAME_USED:
                    throw new LandCommandException(messages.getString("error.creation.land_name_used"));
                default:
                    throw new LandCommandException(messages.getString("error.invalid_cause_number"));
            }
        }
    }
}
