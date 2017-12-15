package world.avatarhorizon.spigot.lands.exceptions;

public class LandManagementException extends LandException
{
    private ExceptionCause cause;

    public LandManagementException(ExceptionCause causeKey)
    {
        this.cause = causeKey;
    }

    public ExceptionCause getCauseKey()
    {
        return cause;
    }
}
