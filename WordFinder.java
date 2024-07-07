import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFinder {
    private Set<String> wordList;

    public WordFinder(Set<String> wordList) {
        this.wordList = wordList;
    }

    public List<String> findAnagram(String rackStr) {
        List<String> anagrams = new ArrayList<>();
        Pattern pattern = Pattern.compile(makePattern(rackStr));
        for (String word : wordList) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find() && word.length() > 1) {
                anagrams.add(word);
            }
        }
        return anagrams;
    }

    public String makePattern(String rackStr) {
        String makePattern = "^{2,4}[habit]";
        for (int i = 0; i < rackStr.length(); i++) {
            char c = rackStr.charAt(i);
            int occurences = 0;
            for (int j = 0; j < rackStr.length(); j++) {
                if (rackStr.charAt(j) == c) {
                    occurences++;
                }
            }
            if (occurences == 1) {
                makePattern += "(?!.*" + c + ".*" + c + ")";
            } else {
                makePattern += "(" + c + "{0," + String.valueOf(occurences) + "})";
            }
        }
        makePattern += "([" + rackStr + "])*[habit]$";
        return makePattern;
    }

    public Set<String> getWordList() {
        return wordList;
    }

    public void setWordList(Set<String> wordList) {
        this.wordList = wordList;
    }
}
