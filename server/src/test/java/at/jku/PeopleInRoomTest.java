package at.jku;

import at.jku.model.PeopleInRoom;
import at.jku.model.Room;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

public class PeopleInRoomTest {

    @Test
    public void testPeopleInRoom() {
        PeopleInRoom peopleInRoom = new PeopleInRoom();
        Room room = new Room();

        peopleInRoom.setId(1L);
        peopleInRoom.setRoom(room);
        peopleInRoom.setTimestamp(LocalDateTime.now());
        peopleInRoom.setNumPeopleInRoom(5);

        Assert.assertEquals(Long.valueOf(1), peopleInRoom.getId());
        Assert.assertEquals(room, peopleInRoom.getRoom());
        Assert.assertNotNull(peopleInRoom.getTimestamp());
        Assert.assertEquals(5, peopleInRoom.getNumPeopleInRoom());
    }
}
