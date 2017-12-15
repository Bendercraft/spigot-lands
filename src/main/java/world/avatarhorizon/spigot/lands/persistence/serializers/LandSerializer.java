package world.avatarhorizon.spigot.lands.persistence.serializers;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import world.avatarhorizon.spigot.lands.models.ChunkLocation;
import world.avatarhorizon.spigot.lands.models.Land;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.UUID;

public class LandSerializer implements JsonSerializer<Land>, JsonDeserializer<Land>
{
    @Override
    public Land deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = jsonElement.getAsJsonObject();
        UUID id = UUID.fromString(root.get("id").getAsString());
        Land land = new Land(id);
        land.setName(root.get("name").getAsString());

        World world = Bukkit.getWorld(root.get("world").getAsString());
        if (world != null)
        {
            land.setWorld(world);
        }

        JsonElement desc = root.get("description");
        if (desc != null)
        {
            land.setDescription(desc.getAsString());
        }

        JsonElement tel = root.get("teleport");
        if (tel != null)
        {
            Location location = context.deserialize(tel, Location.class);
            land.setTeleportLocation(location);
        }

        JsonArray array = root.get("chunks").getAsJsonArray();
        HashSet<ChunkLocation> chunks = new HashSet<>();
        for (JsonElement el : array)
        {
            ChunkLocation loc = context.deserialize(el, ChunkLocation.class);
            chunks.add(loc);
        }
        land.addChunks(chunks);

        return land;
    }

    @Override
    public JsonElement serialize(Land land, Type type, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();
        root.addProperty("id", land.getId().toString());
        root.addProperty("name", land.getName());
        root.addProperty("description", land.getDescription());

        JsonElement loc = context.serialize(land.getTeleportLocation());
        root.add("teleport", loc);

        root.addProperty("world", land.getWorld().getName());

        JsonArray chunks = new JsonArray();
        for (ChunkLocation cl : land.getChunks())
        {
            JsonElement jOb = context.serialize(cl);
            chunks.add(jOb);
        }
        root.add("chunks", chunks);

        return root;
    }
}
