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

public final class RenameCommand extends LandSubCommand
{
    private static final Pattern RENAME_PATTERN = Pattern.compile("\"([\\w ]+)\" \"([\\w ]+)\"");

    public RenameCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("rename", resourceBundle, logger, landsManager);

        aliases = new ArrayList<>(1);
        aliases.add("name");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        validatePermission(sender, "lands.admin.rename");
        validatePlayer(sender);

        String temp = String.join(" ", args).trim();
        if (temp.equals(""))
        {
            throw new LandCommandException(messages.getString("error.empty_params"));
        }

        Matcher matcher = RENAME_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.rename.invalid_name"));
        }

        String oldName = matcher.group(1);
        String newName = matcher.group(2);

        try
        {
            Player p = (Player) sender;
            landsManager.renameLandInWorld(p.getWorld(), oldName, newName);
            sender.sendMessage(messages.getString("success.rename"));
            logger.info("Lands \"" + oldName + "\" has been renamed as \"" + newName + "\"");
        }
        catch (LandManagementException e)
        {
            throw new LandCommandException(messages.getString(e.getCauseKey().getKey()));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.rename";
    }

}
