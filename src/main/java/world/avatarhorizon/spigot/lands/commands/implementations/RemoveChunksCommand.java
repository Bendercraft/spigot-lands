package world.avatarhorizon.spigot.lands.commands.implementations;

import com.mysql.jdbc.Messages;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;
import world.avatarhorizon.spigot.lands.models.ChunkLocation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveChunksCommand extends LandSubCommand
{
    private static final Pattern COMMAND_PATTERN = Pattern.compile("\"([\\w ]+)\" ([a-zA-Z]+)(.*)");
    private static final String WORLDEDIT = "worldedit";
    private static final String ONE = "one";
    private static final String POSITION = "pos";

    private WorldEditPlugin worldEditPlugin;

    public RemoveChunksCommand(ResourceBundle resourceBundle, Logger logger, LandsManager landsManager, WorldEditPlugin wePlugin)
    {
        super("removechunk", resourceBundle, logger, landsManager);
        this.aliases = new ArrayList<>(2);
        this.aliases.add("remove");
        this.aliases.add("rem");

        this.worldEditPlugin = wePlugin;
    }

    @Override
    public void execute(CommandSender sender, List<String> args) throws LandCommandException
    {
        if (!sender.hasPermission("lands.admin.removechunks"))
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
            throw new LandCommandException(messages.getString("error.removechunks.empty_params"));
        }

        Matcher matcher = COMMAND_PATTERN.matcher(temp);
        if (!matcher.matches())
        {
            throw new LandCommandException(messages.getString("error.removechunks.invalid_pattern"));
        }

        String name = matcher.group(1).toLowerCase();
        String mode = matcher.group(2).toLowerCase();

        Player p = (Player) sender;
        List<ChunkLocation> chunks = new LinkedList<>();
        if (WORLDEDIT.equals(mode))
        {

        }
        else if (ONE.equals(mode))
        {

        }
        else if (POSITION.equals(mode))
        {

        }
        else
        {
            throw new LandCommandException(Messages.getString("error.removechunks.invalid_mode"));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.removechunk";
    }
}
