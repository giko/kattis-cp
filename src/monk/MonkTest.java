package monk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonkTest {

    @Test
    public void computeHeightTest() {
        Travel[] ascends = new Travel[3];
        ascends[0] = new Travel(1, 1);
        ascends[1] = new Travel(2, 2);
        ascends[2] = new Travel(3, 3);
        assertEquals(3, Monk.computeHeight(3, ascends));
        assertEquals(4, Monk.computeHeight(4, ascends));
        assertEquals(4.5, Monk.computeHeight(4.5, ascends));
    }

    @Test
    public void compareHeightsTest() {
        Travel[] ascends = new Travel[3];
        ascends[0] = new Travel(1, 1);
        ascends[1] = new Travel(2, 2);
        ascends[2] = new Travel(3, 3);
        Travel[] descends = new Travel[3];
        descends[0] = new Travel(1, 1);
        descends[1] = new Travel(2, 2);
        descends[2] = new Travel(3, 3);
        assertEquals(0, Monk.compareHeights(3, ascends, descends, 6));
        assertEquals(0, Monk.compareHeights(3.000001, ascends, descends, 6));
        assertEquals(-1, Monk.compareHeights(2, ascends, descends, 6));
        assertEquals(1, Monk.compareHeights(4, ascends, descends, 6));
    }

}