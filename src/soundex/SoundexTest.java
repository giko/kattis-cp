package soundex;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundexTest {

    @Test
    void translate() {
        assertEquals("25", Soundex.translate("KHAWN"));
        assertEquals("1236", Soundex.translate("PFISTER"));
        assertEquals("11", Soundex.translate("BOBBY"));
        assertEquals("1", Soundex.translate("BBBBB"));
        assertEquals("", Soundex.translate("AEIOUHWY"));
    }
}