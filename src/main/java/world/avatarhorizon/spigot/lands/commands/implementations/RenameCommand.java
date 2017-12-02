package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public final class RenameCommand extends LandSubCommand
{
    public RenameCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("rename", resourceBundle, logger, landsManager);
        aliases = new ArrayList<>(1);
        aliases.add("name");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        if (!sender.hasPermission("lands.admin.rename"))
        {
            throw new LandCommandException(messages.getString("error.no_permission"));
        }
    }
}
