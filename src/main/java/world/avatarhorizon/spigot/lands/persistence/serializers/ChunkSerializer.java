package world.avatarhorizon.spigot.lands.persistence.serializers;

import com.google.gson.*;
import world.avatarhorizon.spigot.lands.models.ChunkLocation;

import java.lang.reflect.Type;

public class ChunkSerializer implements JsonSerializer<ChunkLocation>, JsonDeserializer<ChunkLocation>
{
    /*

     */
    @Override
    public ChunkLocation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = jsonElement.getAsJsonObject();
        ChunkLocation chunk = new ChunkLocation();
        chunk.setX(root.get("x").getAsInt());
        chunk.setY(root.get("y").getAsInt());
        return chunk;
    }

    @Override
    public JsonElement serialize(ChunkLocation chunkLocation, Type type, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();
        root.addProperty("x", chunkLocation.getX());
        root.addProperty("y", chunkLocation.getY());
        return root;
    }
}
