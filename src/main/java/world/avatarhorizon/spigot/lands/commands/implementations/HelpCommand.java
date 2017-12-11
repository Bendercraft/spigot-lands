package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HelpCommand extends LandSubCommand
{
    private List<LandSubCommand> commands;

    public HelpCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager, List<LandSubCommand> commands)
    {
        super("help", resourceBundle, logger, landsManager);
        this.aliases = new ArrayList<>(2);
        aliases.add("h");
        aliases.add("?");

        this.commands = commands;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        boolean found = false;
        if (args.isEmpty())
        {
            for (LandSubCommand command : commands)
            {
                command.sendHelp(sender);
                found = true;
            }
        }
        else
        {
            String action = args.remove(0);
            for (LandSubCommand command : commands)
            {
                if (command.isCommand(action))
                {
                    command.sendHelp(sender);
                    found = true;
                    break;
                }
            }
        }
        if (! found)
        {
            sender.sendMessage(ChatColor.RED + "");
        }
    }

    @Override
    public void sendHelp(CommandSender sender)
    {
        sender.sendMessage(ChatColor.DARK_AQUA + "/lands help [subcommand]");
    }
}
