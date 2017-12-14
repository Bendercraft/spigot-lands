package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SetTeleportCommand extends LandSubCommand
{
    public SetTeleportCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("setteleport", resourceBundle, logger, landsManager);
        this.aliases = new ArrayList<>(2);
        this.aliases.add("settp");
        this.aliases.add("stp");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {

    }

    @Override
    protected String getHelpKey()
    {
        return null;
    }
}
