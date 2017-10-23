package ca.ualberta.cmput301f17t08.habitrabbit;

import android.location.Location;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Exchanger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HabitEventUnitTest {

    File picture1, picture2;

    Location location1, location2;

    String comment1 = "Comment one";
    String comment2 = "Comment two";

    @Before
    public void setUp() {

        try{
            picture1 = File.createTempFile("picture1", "jpg");
            picture2 = File.createTempFile("picture2", "jpg");
        } catch(Exception e){
            fail("Failed to create temporary picture files!");
        }

        location1 = new Location("");
        location2 = new Location("");

        location1.setLatitude(10);
        location1.setLongitude(10);

        location2.setLatitude(20);
        location2.setLongitude(20);
    }

    @Test
    public void testHabitEventGetters() throws Exception {

        HabitEvent habitEvent = new HabitEvent(comment1, location1, picture1);

        // Test getters:
        assertEquals(comment1, habitEvent.getComment());
        assertTrue(location1.distanceTo(habitEvent.getLocation()) < 10);
        assertTrue(picture1.getCanonicalPath().equals(habitEvent.getPicture().getCanonicalPath()));
    }

    @Test
    public void testHabitEventSetters() throws Exception {
        HabitEvent habitEvent = new HabitEvent(comment2, location2, picture2);

        habitEvent.setComment(comment1);
        habitEvent.setLocation(location1);
        habitEvent.setPicture(picture1);

        // Test getters:
        assertEquals(comment1, habitEvent.getComment());
        assertTrue(location1.distanceTo(habitEvent.getLocation()) < 10);
        assertTrue(picture1.getCanonicalPath().equals(habitEvent.getPicture().getCanonicalPath()));

    }
}