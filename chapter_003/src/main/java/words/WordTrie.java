package words;

import java.util.HashMap;
import java.util.Map;

public class WordTrie {

    private class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;

        public TrieNode() {
            this.children = new HashMap<>();
            this.isEndOfWord = false;
        }
    }

    private TrieNode root;

    public WordTrie() {
        this.root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode();
                current.children.put(ch, node);
            }
            current = node;
        }
        current.isEndOfWord = true;
    }
}
