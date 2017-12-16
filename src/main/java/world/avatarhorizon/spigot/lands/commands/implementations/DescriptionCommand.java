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

public class DescriptionCommand extends LandSubCommand
{

    private static final Pattern DESCRIPTION_PATTERN = Pattern.compile("\"([\\w \\-]+)\" \"([\\w ]+)\"");

    public DescriptionCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("description", resourceBundle, logger, landsManager);
        aliases = new ArrayList<>(1);
        aliases.add("desc");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        validatePermission(sender, "lands.admin.description");
        validatePlayer(sender);

        String temp = String.join(" ", args).trim();
        if (temp.equals(""))
        {
            throw new LandCommandException(messages.getString("error.empty_params"));
        }

        Matcher matcher = DESCRIPTION_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.description.invalid_params"));
        }

        String name = matcher.group(1);
        String description = matcher.group(2);

        try
        {
            Player p = (Player) sender;
            landsManager.setLandDescription(p.getWorld(), name, description);
            sender.sendMessage(messages.getString("success.description"));
            logger.info("Lands \"" + name + "\" has been set a new description");
        }
        catch (LandManagementException e)
        {
            throw new LandCommandException(messages.getString(e.getCauseKey().getKey()));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.description";
    }

}
