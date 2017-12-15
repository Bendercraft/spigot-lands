package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;
import world.avatarhorizon.spigot.lands.exceptions.LandManagementException;
import world.avatarhorizon.spigot.lands.models.Land;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class CreateCommand extends LandSubCommand
{
    private static final Pattern NAME_PATTERN = Pattern.compile("\"([\\w ]+)\"");

    public CreateCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("create", resourceBundle, logger, landsManager);
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        validatePermission(sender, "lands.admin.create");
        validatePlayer(sender);

        String temp = String.join(" ", args).trim();
        if (temp.equals(""))
        {
            throw new LandCommandException(messages.getString("error.empty_params"));
        }

        Matcher matcher = NAME_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.creation.invalid_name"));
        }
        String name = matcher.group(1);

        try
        {
            Player p = (Player) sender;
            Land land = new Land();
            land.setName(name);
            land.setWorld(p.getWorld());
            landsManager.addLandToWorld(p.getWorld(), land);
            sender.sendMessage(messages.getString("success.creation"));
            logger.info("Lands \"" + land.getName() + "\" created.");
        }
        catch (LandManagementException e)
        {
            throw new LandCommandException(messages.getString(e.getCauseKey().getKey()));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.create";
    }
}
