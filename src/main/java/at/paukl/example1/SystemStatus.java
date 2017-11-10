package at.paukl.example1;

/**
 * @author Paul Klingelhuber
 */
public final class SystemStatus {

    private final String message;
    private final Status status;

    private SystemStatus(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public static SystemStatus ok(String message) {
        return new SystemStatus(message, Status.OK);
    }

    public static SystemStatus warning(String message) {
        return new SystemStatus(message, Status.WARNING);
    }

    public static SystemStatus error(String message) {
        return new SystemStatus(message, Status.ERROR);
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        OK,
        WARNING,
        ERROR;
    }

}
