package pagelayout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageLayoutTest {

    @Test
    public void rectangleIntersection() {
        Rectangle r1 = new Rectangle(10, 10, 1, 0);
        Rectangle r2 = new Rectangle(10, 10, 10, 10);
        Rectangle r3 = new Rectangle(10, 10, 0, 10);
        Rectangle r4 = new Rectangle(10, 10, 0, 1);
        Rectangle r5 = new Rectangle(0, 0, 5, 5);

        assertTrue(r5.hasIntersections(r1));
        assertFalse(r1.hasIntersections(r2));
        assertFalse(r2.hasIntersections(r3));
        assertTrue(r4.hasIntersections(r3));
        assertTrue(r4.hasIntersections(r4));
    }
}