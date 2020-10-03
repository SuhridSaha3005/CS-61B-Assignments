import org.junit.Test;
import org.junit.Assert;

public class TestUnionFind {
    @Test
    public void TestDS() {
        UnionFind DS = new UnionFind(6);
        Assert.assertFalse(DS.isConnected(1, 2));
        Assert.assertTrue(DS.isConnected(1, 1));
        Assert.assertEquals(DS.sizeOf(0), 1);
        Assert.assertEquals(DS.sizeOf(1), 1);
        DS.connect(0, 1);
        DS.connect(2, 3);
        DS.connect(3, 4);
        Assert.assertEquals(DS.sizeOf(0), 2);
        Assert.assertEquals(DS.sizeOf(4), 3);
        Assert.assertTrue(DS.isConnected(0, 1));
        Assert.assertTrue(DS.isConnected(2, 4));
        Assert.assertFalse(DS.isConnected(0, 2));
        DS.connect(1, 5);
        Assert.assertEquals(DS.sizeOf(0), 3);
        Assert.assertTrue(DS.isConnected(0, 5));
        DS.connect(1,3);
        Assert.assertEquals(DS.sizeOf(1), 6);
        Assert.assertEquals(DS.sizeOf(5), 6);
        Assert.assertTrue(DS.isConnected(0, 2));
        Assert.assertTrue(DS.isConnected(1, 3));
        Assert.assertTrue(DS.isConnected(4, 5));
    }
}
