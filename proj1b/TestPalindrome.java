import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome(null));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("xy"));
        assertTrue(palindrome.isPalindrome("aa"));
        assertTrue(palindrome.isPalindrome("bb"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertTrue(palindrome.isPalindrome("malayalam"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
    }

    static CharacterComparator obo = new OffByOne();

    @Test
    public void testIsPalindromeCC() {
        assertFalse(palindrome.isPalindrome(null, obo));
        assertFalse(palindrome.isPalindrome("horse", null));
        assertTrue(palindrome.isPalindrome("noon", null));
        assertTrue(palindrome.isPalindrome("malayalam", null));
        assertTrue(palindrome.isPalindrome("racecar", null));
        assertFalse(palindrome.isPalindrome("aaaaab", null));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome("%&%&", obo));
        assertTrue(palindrome.isPalindrome("aqgefhrb", obo));
        assertTrue(palindrome.isPalindrome("flake", obo));
        assertFalse(palindrome.isPalindrome("popeye", obo));
        assertFalse(palindrome.isPalindrome("chicken", obo));
        assertFalse(palindrome.isPalindrome("joshua", obo));
    }
}
