package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DocumentParser {

    private final String text;
    static final private Map<String, WordFrequency> wordFrequencies;

    static {
        wordFrequencies = new HashMap<>();
    }

    public DocumentParser(String text) {
        this.text = text;
    }

    static public void printWordOccurences() {
        wordFrequencies.forEach((k, v) -> System.out.println(v + ":" + k));
    }

    @Override
    public String toString() {
        return "Total distinct words: " + wordFrequencies.size();
    }

    public void start() {
        int newWordsCount = 0;
        int distinctWordsCount = 0;

        String words = this.text.replaceAll("[^А-Яа-я ]", " ");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(words.split("\\s+")));

        for (String word : list) {
            if (word != null && !word.isEmpty()) {
                WordFrequency frequency = getWord(word);
                if (frequency == null) {
                    distinctWordsCount++;
                    frequency = new WordFrequency(word);
                    putWord(word, frequency);
//                    newWordFound(frequency);
                } else {
                    newWordsCount++;
                    frequency.increaseFrequency();
//                    wordOccurrenceFound(frequency);
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + ":done, new distinct words found " + distinctWordsCount
                + " out of a total of " + (newWordsCount + distinctWordsCount));
    }

    private WordFrequency getWord(String word) {
        synchronized (wordFrequencies) {
            WordFrequency frequency = wordFrequencies.get(word);
            return frequency;
        }
    }

    private void putWord(String word, WordFrequency frequency) {
        synchronized (wordFrequencies) {
            wordFrequencies.put(word, frequency);
        }
    }

    public void wordOccurrenceFound(WordFrequency frequency) {
        System.out.println(Thread.currentThread().getName() + "  found occurrence of" + frequency);
    }

    public void newWordFound(WordFrequency frequency) {
        System.out.println(Thread.currentThread().getName() + "       new word found" + frequency);
    }
}
