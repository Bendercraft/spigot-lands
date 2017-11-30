package world.avatarhorizon.spigot.lands.exceptions;

public class LandCommandException extends LandException
{
    private String message;

    public LandCommandException(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return this.message;
    }
}
