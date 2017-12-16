package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;
import world.avatarhorizon.spigot.lands.models.Land;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeleportCommand extends LandSubCommand
{
    private static final Pattern TELEPORT_PATTERN = Pattern.compile("\"([\\w \\-]+)\"");

    public TeleportCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("teleport", resourceBundle, logger, landsManager);
        this.aliases = new ArrayList<>(1);
        this.aliases.add("tp");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        validatePermission(sender,"lands.commands.teleport");
        validatePlayer(sender);

        String temp = String.join(" ", args).trim();
        if (temp.equals(""))
        {
            throw new LandCommandException(messages.getString("error.empty_params"));
        }

        Matcher matcher = TELEPORT_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.setteleport.invalid_params"));
        }

        String name = matcher.group(1);
        Player p = (Player) sender;
        Land land = landsManager.getLand(p.getWorld(), name);

        if (land == null)
        {
            throw new LandCommandException(messages.getString("error.no_land"));
        }
        Location loc = land.getTeleportLocation();
        if (loc == null)
        {
            throw new LandCommandException(messages.getString("error.null_teleport_location"));
        }

        p.teleport(loc);
        p.sendMessage(messages.getString("success.teleport"));
    }

    @Override
    protected String getHelpKey()
    {
        return null;
    }
}
