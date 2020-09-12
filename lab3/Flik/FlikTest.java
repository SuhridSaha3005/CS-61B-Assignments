import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FlikTest {
    private int m, n;
    @Test
    public void TestIsSameNumber() {
        for (int i = 0; i < 500; i += 1) {
            m = i;
            n = i;
            assertTrue("Flik fails for " + i, Flik.isSameNumber(m, n));
        }
    }
}
