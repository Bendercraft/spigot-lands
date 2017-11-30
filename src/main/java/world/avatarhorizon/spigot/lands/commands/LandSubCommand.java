package world.avatarhorizon.spigot.lands.commands;

import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;

import java.util.List;
import java.util.ResourceBundle;

public abstract class LandSubCommand
{
    protected final ResourceBundle messages;
    protected final LandsManager landsManager;

    protected final String label;

    public LandSubCommand(String label, ResourceBundle resourceBundle, LandsManager landsManager)
    {
        this.label = label;
        this.messages = resourceBundle;
        this.landsManager = landsManager;
    }

    public boolean isCommand(String otherLabel)
    {
        return this.label.equals(otherLabel);
    }

    public abstract boolean execute(CommandSender sender, List<String> args);
}
