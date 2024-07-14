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

    public List<String> findAnagram(String rackStr, int turnNumber) {
        List<String> anagrams = new ArrayList<>();
        Pattern pattern = Pattern.compile(choosePattern(rackStr, turnNumber));
        for (String word : wordList) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.find() && word.length() > 1) {
                anagrams.add(word);
            }
        }
        return anagrams;
    }

    private String choosePattern(String rackStr, int turnNumber) {
        if (turnNumber == 0) {
            return "^" + makePatternBase(rackStr) + "[" + rackStr + "]*$";
        }
        return "^" + makePatternBase(rackStr) + "[" + rackStr + "]*$";
    }

    private String makePattern(String rackStr) {
        String makePattern = "^{2,4}[habit]";


//        "([" + rackStr + "])*[habit]$";
        return makePattern;
    }

    private String makePatternBase(String rackStr) {
        String patternBase = "";
        for (int i = 0; i < rackStr.length(); i++) {
            char c = rackStr.charAt(i);
            int occurences = 0;
            for (int j = 0; j < rackStr.length(); j++) {
                if (rackStr.charAt(j) == c) {
                    occurences++;
                }
            }
            if (occurences == 1) {
                patternBase += "(?!.*" + c + ".*" + c + ")";
            } else {
                patternBase += "(" + c + "{0," + String.valueOf(occurences) + "})";
            }
        }
        return patternBase;
    }

    public Set<String> getWordList() {
        return wordList;
    }

    public void setWordList(Set<String> wordList) {
        this.wordList = wordList;
    }
}
