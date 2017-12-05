package world.avatarhorizon.spigot.lands.exceptions;

public class LandManagementException extends LandException
{
    private String cause;

    public LandManagementException(String causeKey)
    {
        this.cause = causeKey;
    }

    public String getCauseKey()
    {
        return cause;
    }
}
