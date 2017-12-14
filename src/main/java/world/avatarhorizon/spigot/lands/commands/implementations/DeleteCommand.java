package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;
import world.avatarhorizon.spigot.lands.exceptions.LandManagementException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommand extends LandSubCommand
{
    private static final Pattern DELETE_PATTERN = Pattern.compile("\"([\\w ]+)\"");

    public DeleteCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("delete", resourceBundle, logger, landsManager);
        this.aliases = new ArrayList<>(1);
        this.aliases.add("del");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        if (!sender.hasPermission("lands.admin.description"))
        {
            throw new LandCommandException(messages.getString("error.no_permission"));
        }

        if (!(sender instanceof Player))
        {
            throw new LandCommandException(messages.getString("error.player_requirement"));
        }

        String temp = String.join(" ", args).trim();
        if (temp == null || temp.equals(""))
        {
            throw new LandCommandException(messages.getString("error.delete.empty_params"));
        }

        Matcher matcher = DELETE_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.delete.invalid_params"));
        }

        try
        {
            String name = matcher.group(1);
            Player p = (Player) sender;
            landsManager.deleteLand(p.getWorld(), name);
            sender.sendMessage(messages.getString("success.delete"));
            logger.info("Lands \"" + name + "\" has been deleted");
        }
        catch (LandManagementException e)
        {
            throw new LandCommandException(messages.getString(e.getCauseKey()));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.delete";
    }
}
