package world.avatarhorizon.spigot.lands.exceptions;

public class LandDescriptionException extends LandManagementException
{
    public static final String CAUSE_NULL_WORLD = "error.description.null_world";
    public static final String CAUSE_NULL_NAME = "error.description.null_name";
    public static final String CAUSE_NO_LAND = "error.description.no_land";

    public LandDescriptionException(String causeKey)
    {
        super(causeKey);
    }
}
