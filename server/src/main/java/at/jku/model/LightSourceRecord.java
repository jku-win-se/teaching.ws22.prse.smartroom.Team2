package at.jku.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LightSourceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private LightSource lightSource;

    private LocalDateTime timestamp;

    private boolean state;


    public LightSourceRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LightSource getLightSource() {
        return this.lightSource;
    }

    public void setLightSource(LightSource lightSource) {
        this.lightSource = lightSource;
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