package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;

    private static int WORDLENGTH = DEFAULT_WORD_LENGTH;

    private Random random = new Random();


    HashSet<String> wordSet = new HashSet<>();
    ArrayList<String> wordList = new ArrayList<>();
    HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        ArrayList<String> wordMapList;

        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);

            if (sizeToWords.containsKey(word.length())) {
                wordMapList = sizeToWords.get(word.length());
                wordMapList.add(word);
                sizeToWords.put(word.length(), wordList);
            } else {
                ArrayList<String> newWordList = new ArrayList<>();
                newWordList.add(word);
                sizeToWords.put(word.length(), newWordList);

            }


            ArrayList<String> sortedList = new ArrayList<>();
            String sortedWord = sortLetters(word);

            if (!(lettersToWord.containsKey(sortedWord))) {
                sortedList.add(word);
                lettersToWord.put(sortedWord, sortedList);
            } else {
                sortedList = lettersToWord.get(sortedWord);
                sortedList.add(word);
                lettersToWord.put(sortedWord, sortedList);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        if (wordSet.contains(word) && !(base.contains(word))) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> resultList = new ArrayList<>();
        return resultList;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String Word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<>();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {
            String anagram = Word + alphabet;
            String sortedAnagram = sortLetters(anagram);

            if (lettersToWord.containsKey(sortedAnagram)) {
                tempList = new ArrayList<>();
                tempList = lettersToWord.get(sortedAnagram);

                for (int i = 0; i < tempList.size(); i++) {
                    if (!(tempList.get(i).contains(Word))) {
                        resultList.add(tempList.get(i));
                    }
                }
            }
        }

        return resultList;
    }

    public ArrayList<String> getAnagramsWithTwoMoreLetter(String Word) {
        ArrayList<String> tempList;
        ArrayList<String> resultList = new ArrayList<>();

        for (char firstalphabet = 'a'; firstalphabet <= 'z'; firstalphabet++) {
            for (char secondalphabet = 'a'; secondalphabet <= 'z'; secondalphabet++) {
                String anagram = Word + firstalphabet + secondalphabet;
                String sortedAnagram = sortLetters(anagram);

                if (lettersToWord.containsKey(sortedAnagram)) {
                    tempList = new ArrayList<>();
                    tempList = lettersToWord.get(sortedAnagram);

                    for (int i = 0; i < tempList.size(); i++) {
                        if (!(tempList.get(i).contains(Word))) {
                            resultList.add(tempList.get(i));
                        }
                    }
                }
            }
        }

        return resultList;
    }


    public String pickGoodStarterWord() {
        int randomNumber;
        String starterWord;

        do {
            randomNumber = random.nextInt(sizeToWords.get(WORDLENGTH).size());
            starterWord = sizeToWords.get(WORDLENGTH).get(randomNumber);

        } while (getAnagramsWithOneMoreLetter(starterWord).size() < MIN_NUM_ANAGRAMS);
        if (WORDLENGTH < MAX_WORD_LENGTH) {
            WORDLENGTH++;
        }


        return starterWord;
    }


    public String sortLetters(String Word) {

        char[] sortCharacters = Word.toCharArray();
        Arrays.sort(sortCharacters);
        String sortedWord = new String(sortCharacters);
        return sortedWord;


    }

}