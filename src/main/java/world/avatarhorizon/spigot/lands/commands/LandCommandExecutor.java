package world.avatarhorizon.spigot.lands.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class LandCommandExecutor implements CommandExecutor
{
    private ResourceBundle messages;
    private List<LandSubCommand> subCommands;

    private LandCommandExecutor(LandsManager landsManager)
    {
        messages = ResourceBundle.getBundle("messages/commands");
        subCommands = new LinkedList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        List<String> argsList = Arrays.asList(args);
        String sub = argsList.remove(0);

        for (LandSubCommand subCommand : subCommands)
        {
            if (subCommand.isCommand(sub))
            {
                subCommand.execute(sender, argsList);
                return true;
            }
        }
        return false;
    }
}
