package at.jku.model;

public interface Openable {
    public boolean isOpen();

    public void open();

    public void close();

    public void toggle();
}
