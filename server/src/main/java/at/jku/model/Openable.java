package at.jku.model;

/**
 * The `Openable` interface represents the doors that can be opened and closed.
 * */

public interface Openable {
    public boolean isOpen();

    public void open();

    public void close();

    public void toggle();
}
