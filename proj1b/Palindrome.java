public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new ArrayDeque<>();
        int i = 0;
        while (i < word.length()) {
            wordDeque.addLast(word.charAt(i));
            i ++;
        }
        return wordDeque;
    }

    private boolean isPalindromeDeque(Deque<Character> wordDeque) {
        if (wordDeque.isEmpty() || wordDeque.size() == 1) {
            return true;
        }
        char firstItem = wordDeque.removeFirst();
        char lastItem = wordDeque.removeLast();
        return firstItem == lastItem && isPalindromeDeque(wordDeque);
    }

    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindromeDeque(wordDeque);
    }

    private boolean isPalindromeDequeCC(Deque<Character> wordDeque, CharacterComparator cc) {
        if (wordDeque.isEmpty() || wordDeque.size() == 1) {
            return true;
        }
        char firstItem = wordDeque.removeFirst();
        char lastItem = wordDeque.removeLast();
        return cc.equalChars(firstItem, lastItem) && isPalindromeDequeCC(wordDeque, cc);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        Deque<Character> wordDeque = wordToDeque(word);
        if (cc == null) {
            return isPalindromeDeque(wordDeque);
        }
        return isPalindromeDequeCC(wordDeque, cc);
    }
}
