package world.avatarhorizon.spigot.lands.commands.implementations;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetTeleportCommand extends LandSubCommand
{
    private static final Pattern SETTELEPORT_PATTERN = Pattern.compile("\"([\\w ]+)\"");

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
            throw new LandCommandException(messages.getString("error.delete.empty_params"));
        }

        Matcher matcher = SETTELEPORT_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.delete.invalid_params"));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.setteleport";
    }
}
