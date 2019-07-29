import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class TextChecker {
    private static boolean ASC = true;
    private static boolean DESC = false;
    private static final String FILENAME = "src/main/resources/Song.txt";
    private static String[] bannedWords = new String[]{"I", "a", "to", "So", "my",
            "in", "of", "me", "do", "it", "so", "Motherfucker", "Bitch",
            "up", "at", "By", "B", "O", "Y", "S", "be", "no", "I'm", "'em", "it,"};

    public TextChecker(){
        init();
    }
    private static void init() {
        try {
            List<String>list = countWordsNumber();

            List<String>withoutBannedWords = list.stream()
                    .filter(TextChecker::goodWord)
                    .collect(Collectors.toList());
            Map<String, Integer> occurrences = new HashMap<>();
            for ( String word : withoutBannedWords ) {
                Integer oldCount = occurrences.get(word);
                if ( oldCount == null ) {
                    oldCount = 0;
                }
                occurrences.put(word, oldCount + 1);
            }

            Map<String, Integer> mostPopularWords = new HashMap<>();
            for (Map.Entry<String, Integer> word:occurrences.entrySet()){
                if(word.getValue()>=5){
                    mostPopularWords.put(word.getKey(),word.getValue());
                }
            }

            Map<String, Integer> sortedMapDesc = sortByComparator(mostPopularWords, DESC);
            System.out.println("Most used words: "+ sortedMapDesc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (order){
                    return o1.getValue().compareTo(o2.getValue());
                }else {
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
    private static boolean goodWord(String str){
        for (String bannedWord : bannedWords) {
            if(str.equals(bannedWord)){
                return false;
            }
        }
        return true;
    }

    private static List<String> countWordsNumber() throws IOException {
        int countWord = 0;
        Scanner s = new Scanner(new File(TextChecker.FILENAME));
        List<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.next());
            countWord+=1;
        }
        System.out.println("Average amount of words is "+ countWord);
        return list;
    }
}
