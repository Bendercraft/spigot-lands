package world.avatarhorizon.spigot.lands.commands;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public abstract class LandSubCommand
{
    protected final ResourceBundle messages;
    protected final LandsManager landsManager;
    protected final Logger logger;

    private final String label;
    protected List<String> aliases = null;

    public LandSubCommand(String label, ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        this.label = label;
        this.messages = resourceBundle;
        this.logger = logger;
        this.landsManager = landsManager;
    }

    public boolean isCommand(String otherLabel)
    {
        if (this.label.equals(otherLabel))
        {
            return true;
        }
        if (aliases != null)
        {
            for (String alias : aliases)
            {
                if (alias.equals(otherLabel))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public abstract void execute(CommandSender sender, List<String> args) throws LandCommandException;

    public abstract void sendHelp(CommandSender sender);
}
