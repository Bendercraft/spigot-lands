package world.avatarhorizon.spigot.lands.commands.implementations;

import com.mysql.jdbc.Messages;
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
        validatePermission(sender, "lands.commands.help");
        validatePlayer(sender);

        if (args.isEmpty())
        {
            for (LandSubCommand command : commands)
            {
                command.sendHelp(sender);
            }
        }
        else
        {
            boolean found = false;
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
            if (!found)
            {
                sender.sendMessage(Messages.getString("error.help.not_found"));
            }
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.help";
    }
}
