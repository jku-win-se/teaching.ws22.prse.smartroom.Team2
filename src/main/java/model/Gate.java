package model;

public abstract class Gate implements Openable {
    private final int id;
    private boolean state;

    public Gate(int id, boolean state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return this.id;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isOpen() {
        return this.state;
    }

    public void open() {
        if (!this.isOpen()) {
            this.toggleGate();
        }
    }

    public void close() {
        if (this.isOpen()) {
            this.toggleGate();
        }
    }

    public void toggleGate() {
        this.setState(!this.state);
    }
}
