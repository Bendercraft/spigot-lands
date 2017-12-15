package world.avatarhorizon.spigot.lands.commands.implementations;

import com.mysql.jdbc.Messages;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Chunk;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.avatarhorizon.spigot.lands.commands.LandSubCommand;
import world.avatarhorizon.spigot.lands.controllers.LandsManager;
import world.avatarhorizon.spigot.lands.exceptions.LandCommandException;
import world.avatarhorizon.spigot.lands.models.ChunkLocation;
import world.avatarhorizon.spigot.lands.models.Land;

import java.util.*;
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
        validatePermission(sender, "lands.admin.removechunks");
        validatePlayer(sender);

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

        removeChunks((Player) sender, matcher);
    }

    private void removeChunks(Player player, Matcher matcher) throws LandCommandException
    {
        String name = matcher.group(1).toLowerCase();

        Land land = landsManager.getLand(player.getWorld(), name);
        if (land == null)
        {
            throw new LandCommandException(messages.getString("error.no_land_in_world"));
        }

        Set<ChunkLocation> chunks = getChunkLocations(player, matcher);

        landsManager.removeChunksFromLand(player.getWorld(), land, chunks);
        player.sendMessage(messages.getString("success.removechunks"));
        logger.info("Chunks removed from land " + name);
    }

    private Set<ChunkLocation> getChunkLocations(Player player, Matcher matcher) throws LandCommandException
    {
        Set<ChunkLocation> chunks = new HashSet<>();
        String mode = matcher.group(2).toLowerCase();
        if (WORLDEDIT.equals(mode))
        {
            handleWorldEditMode(player, chunks);
        }
        else if (ONE.equals(mode))
        {
            handleOneMode(player, chunks);
        }
        else if (POSITION.equals(mode))
        {
            handlePositionMode(player, matcher, chunks);
        }
        else
        {
            throw new LandCommandException(messages.getString("error.removechunks.invalid_mode"));
        }
        return chunks;
    }

    private void handleWorldEditMode(Player player, Set<ChunkLocation> chunks) throws LandCommandException
    {
        try
        {
            Selection selection = worldEditPlugin.getSelection(player);
            if (selection == null)
            {
                throw new LandCommandException(messages.getString("error.removechunks.null_selection"));
            }
            Region region = selection.getRegionSelector().getRegion();
            for (Vector2D vec : region.getChunks())
            {
                ChunkLocation cl = new ChunkLocation();
                cl.setX(vec.getBlockX());
                cl.setY(vec.getBlockZ());
                chunks.add(cl);
            }
        }
        catch (IncompleteRegionException e)
        {
            throw new LandCommandException(messages.getString("error.removechunks.incomplete_selection"));
        }
    }

    private void handleOneMode(Player p, Set<ChunkLocation> chunks)
    {
        Chunk chunk = p.getLocation().getChunk();

        ChunkLocation cl = new ChunkLocation();
        cl.setX(chunk.getX());
        cl.setY(chunk.getZ());
        chunks.add(cl);
    }

    private void handlePositionMode(Player p, Matcher matcher, Set<ChunkLocation> chunks) throws LandCommandException
    {
        try
        {
            String temp = matcher.group(3).trim();
            String[] pos = temp.split(" ");
            if (pos.length < 2)
            {
                throw new LandCommandException(messages.getString("error.removechunks.positions"));
            }

            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);

            ChunkLocation cl = new ChunkLocation();
            cl.setX(x);
            cl.setY(y);

            chunks.add(cl);
        }
        catch (NumberFormatException ex)
        {
            throw new LandCommandException(messages.getString("error.removechunks.numeric_positions"));
        }
    }

    @Override
    protected String getHelpKey()
    {
        return "help.removechunk";
    }
}
