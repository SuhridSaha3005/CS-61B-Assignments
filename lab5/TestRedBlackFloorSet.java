import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestRedBlackFloorSet {
    @Test
    public void randomizedTest() {
        Lab5FloorSet ALFSet = new AListFloorSet();
        Lab5FloorSet RBFSet = new RedBlackFloorSet();
        double numRandom;
        for (int i = 0; i < 1e6; i += 1) {
           numRandom = StdRandom.uniform(-5000, 5000);
           ALFSet.add(numRandom);
           RBFSet.add(numRandom);
        }
        for (int i = 0; i < 1e5; i += 1) {
           numRandom = StdRandom.uniform(-5000, 5000);
           assertEquals(ALFSet.floor(numRandom), RBFSet.floor(numRandom), 0.000001);
        }
    }
}
