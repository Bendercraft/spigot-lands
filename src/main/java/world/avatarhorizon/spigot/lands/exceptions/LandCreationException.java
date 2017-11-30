package world.avatarhorizon.spigot.lands.exceptions;

public class LandCreationException extends LandException
{
    public static short CAUSE_NULL_WORLD = 1;
    public static short CAUSE_NULL_LAND = 2;
    public static short CAUSE_LAND_NO_NAME = 3;
    public static short CAUSE_LAND_NAME_USED = 4;

    private short cause;

    public LandCreationException(short causeNumber)
    {
        this.cause = causeNumber;
    }

    public short getCauseNumber()
    {
        return cause;
    }
}
