package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.Location;
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

public class SetTeleportCommand extends LandSubCommand
{
    private static final Pattern SETTELEPORT_PATTERN = Pattern.compile("\"([\\w \\-]+)\"(.*)");

    public SetTeleportCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager)
    {
        super("setteleport", resourceBundle, logger, landsManager);
        this.aliases = new ArrayList<>(2);
        this.aliases.add("settp");
        this.aliases.add("stp");
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        validatePermission(sender, "lands.admin.setteleport");
        validatePlayer(sender);

        String temp = String.join(" ", args).trim();
        if (temp == null || temp.equals(""))
        {
            throw new LandCommandException(messages.getString("error.empty_params"));
        }

        Matcher matcher = SETTELEPORT_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.setteleport.invalid_params"));
        }

        String name = matcher.group(1);
        String more = matcher.group(2).trim().toLowerCase();
        String[] ar = more.split(" ");

        if (ar.length > 0)
        {
            Player p = (Player) sender;

            Location loc = parseLocation(ar, p);
            try
            {
                landsManager.setLandTeleportLocation(p.getWorld(), name, loc);
                sender.sendMessage(messages.getString("success.setteleport"));
            }
            catch (LandManagementException e)
            {
                throw new LandCommandException(messages.getString(e.getCauseKey().getKey()));
            }
        }
        else
        {
            throw new LandCommandException(messages.getString("error.empty_params"));
        }
    }

    private Location parseLocation(String[] ar, Player p) throws LandCommandException
    {
        String temp = ar[0];
        if ("here".equals(temp))
        {
            return p.getLocation();
        }
        else
        {
            if (ar.length < 3)
            {
                throw new LandCommandException(messages.getString("error.setteleport.require_positions"));
            }
            try
            {
                return new Location(p.getWorld(),Integer.parseInt(ar[0]), Integer.parseInt(ar[1]), Integer.parseInt(ar[2]));
            }
            catch (NumberFormatException e)
            {
                throw new LandCommandException(messages.getString("error.setteleport.numeric_positions"));
            }
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.setteleport";
    }
}
