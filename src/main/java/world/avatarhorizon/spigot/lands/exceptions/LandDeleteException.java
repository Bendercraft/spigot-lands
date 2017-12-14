package world.avatarhorizon.spigot.lands.exceptions;

public class LandDeleteException extends LandManagementException
{
    public static final String CAUSE_NULL_WORLD = "error.delete.null_world";
    public static final String CAUSE_NULL_NAME = "error.delete.null_name";
    public static final String CAUSE_NO_LAND = "error.delete.no_land";

    public LandDeleteException(String causeKey)
    {
        super(causeKey);
    }
}
