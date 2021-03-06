package DataStructures.NonLinear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {

    private static class Node {
        private final char value;
        private boolean isEndOfWord;
        private final HashMap<Character, Node> children = new HashMap<>();

        public Node(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Value = " + value;
        }

        public void addChild(char ch) {
            children.put(ch, new Node(ch));
        }

        private boolean hasChild(char ch) {
            return children.containsKey(ch);
        }

        public Node getChild(char ch) {
            return children.get(ch);
        }

        public Node[] getChildren() {
            return children.values().toArray(new Node[0]);
        }

        private boolean hasChildren() {
            return !children.isEmpty();
        }

        public void removeChild(char ch) {
            children.remove(ch);
        }
    }

    private final Node root = new Node(' ');

    public void insert(String word) {
        var current = root;
        for (var ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                current.addChild(ch);
            current = current.getChild(ch);
        }
        current.isEndOfWord = true;
    }

    public boolean contains(String word) {
        if (word == null)
            return false;

        var current = root;
        for (var ch : word.toCharArray()) {
            if (!current.hasChild(ch))
                return false;

            current = current.getChild(ch);
        }
        return current.isEndOfWord;
    }

    public void preOrderTraverse() {
        preOrderTraverse(root);
    }

    private void preOrderTraverse(Node root) {
        System.out.println(root.value);

        for (var child : root.getChildren())
            preOrderTraverse(child);
    }

    public void postOrderTraverse() {
        postOrderTraverse(root);
    }

    private void postOrderTraverse(Node root) {
        for (var child : root.getChildren())
            postOrderTraverse(child);

        System.out.println(root.value);
    }

    public void remove(String word) {
        if (word == null)
            return;

        remove(root, word, 0);
    }

    private void remove(Node root, String word, int index) {
        if (index == word.length()) {
            root.isEndOfWord = false;
            return;
        }

        var ch = word.charAt(index);
        var child = root.getChild(ch);

        if (child == null)
            return;

        remove(child, word, index + 1);

        if (!child.hasChildren() && !child.isEndOfWord)
            root.removeChild(ch);
    }

    public List<String> findWords(String prefix) {
        List<String> words = new ArrayList<>();
        var lastNode = findLastNode(prefix);

        findWords(lastNode, prefix, words);

        return words;
    }

    private void findWords(Node root, String prefix, List<String> words) {
        if (root == null)
            return;

        if (root.isEndOfWord)
            words.add(prefix);

        for (var child : root.getChildren()) {
            findWords(child, prefix + child.value, words);
        }
    }

    private Node findLastNode(String prefix) {
        if (prefix == null)
            return null;

        var current = root;
        for (var ch : prefix.toCharArray()) {
            var child = current.getChild(ch);

            if (child == null)
                return null;
            current = child;
        }
        return current;
    }
}
