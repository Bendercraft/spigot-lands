package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TeleportCommand extends LandSubCommand
{
    public TeleportCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("teleport", resourceBundle, logger, landsManager);
        this.aliases = new ArrayList<>(1);
        this.aliases.add("tp");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        validatePermission(sender,"lands.commands.teleport");
        validatePlayer(sender);
    }

    @Override
    protected String getHelpKey()
    {
        return null;
    }
}
