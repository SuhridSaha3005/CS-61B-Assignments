public class OffByN implements CharacterComparator{

    private final int numberN;

    public OffByN(int N) {
        numberN = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == numberN;
    }
}
