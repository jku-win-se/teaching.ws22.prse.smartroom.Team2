package at.jku.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class WindowRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Windo window;

    private LocalDateTime timestamp;

    private boolean state;

    public WindowRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Windo getWindow() {
        return this.window;
    }

    public void setWindow(Windo window) {
        this.window = window;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

}