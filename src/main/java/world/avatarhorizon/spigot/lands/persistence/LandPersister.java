package world.avatarhorizon.spigot.lands.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import world.avatarhorizon.spigot.lands.models.ChunkLocation;
import world.avatarhorizon.spigot.lands.models.Land;
import world.avatarhorizon.spigot.lands.persistence.serializers.ChunkSerializer;
import world.avatarhorizon.spigot.lands.persistence.serializers.LandSerializer;
import world.avatarhorizon.spigot.lands.persistence.serializers.LocationSerializer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class LandPersister implements ILandPersister
{
    private static final String DATA_FOLDER_NAME = "data";

    private final Logger logger;

    private final File dataFolder;

    private Gson gson;

    public LandPersister(File pluginFolder, Logger logger)
    {
        this.logger = logger;
        this.dataFolder = new File(pluginFolder, DATA_FOLDER_NAME);
        if (!dataFolder.exists())
        {
            dataFolder.mkdirs();
        }

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Land.class, new LandSerializer());
        builder.registerTypeAdapter(ChunkLocation.class, new ChunkSerializer());
        builder.registerTypeAdapter(Location.class, new LocationSerializer());
        //builder.setPrettyPrinting();

        this.gson = builder.create();
    }
    @Override
    public boolean save(World world, Land land)
    {
        File worldFolder = new File(dataFolder, world.getName());
        if (!worldFolder.exists())
        {
            worldFolder.mkdir();
        }

        File landFile = new File(worldFolder, land.getId().toString()+".json");
        if (!landFile.exists())
        {
            try
            {
                landFile.createNewFile();
            }
            catch (IOException e)
            {
                logger.severe("Unable to create save file for " + land.getName());
                logger.severe(e.getMessage());
                return false;
            }
        }

        try (OutputStream fos = new FileOutputStream(landFile))
        {
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            String json = gson.toJson(land);
            writer.write(json);
            writer.close();
        }
        catch (IOException e)
        {
            logger.severe("IOException while writing file");
            logger.severe(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Map<World, Map<UUID, Land>> loadAll()
    {
        Map<World, Map<UUID, Land>> map = new HashMap<>();
        File[] folders = dataFolder.listFiles();
        if (folders != null)
        {
            for (File worldDir : folders)
            {
                if (worldDir.isDirectory())
                {
                    handleWorld(map, worldDir);
                }
            }
        }

        return map;
    }

    private void handleWorld(Map<World, Map<UUID, Land>> map, File worldDir)
    {
        String worldName = worldDir.getName();
        World world = Bukkit.getWorld(worldName);
        if (world == null)
        {
            logger.warning(worldName + " not found while loading lands.");
        }
        else
        {
            Map<UUID, Land> landsMap = new HashMap<>();

            File[] files = worldDir.listFiles();
            if (files != null)
            {
                for (File landFile : files)
                {
                    handleLand(landsMap, landFile);
                }
            }
            if (!landsMap.isEmpty())
            {
                map.put(world, landsMap);
            }
        }
    }

    private void handleLand(Map<UUID, Land> landsMap, File landFile)
    {
        if (landFile.isFile())
        {
            try (InputStream is = new FileInputStream(landFile))
            {
                InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
                Land land = gson.fromJson(reader, Land.class);
                landsMap.put(land.getId(), land);
            }
            catch (IOException e)
            {
                logger.warning(e.getMessage());
            }
        }
    }

    @Override
    public void delete(World world, Land land)
    {
        File worldDir = new File(this.dataFolder, world.getName());
        if (worldDir.exists() && worldDir.isDirectory())
        {
            File landFile = new File(worldDir, land.getId().toString() + ".json");
            if (landFile.exists())
            {
                if (!landFile.delete())
                {
                    logger.warning("Was not able to delete land file");
                }
            }
        }
    }
}
