package com.passpnu.passwordmanager.util;

import org.springframework.stereotype.Component;

@Component
public class StringSimilarityUtil {
    public int hammingDistance(String smallerString, String longerString){
        int count = 0;
        for(int i = 0; i < smallerString.length(); i++){
            if(smallerString.charAt(i) != longerString.charAt(i)){
                count++;
            }
        }
        return count;
    }

    public int reverseHammingDistance(String smallerString, String longerString){
        int count = 0;

        String reversedSmallerString = new StringBuilder(smallerString).reverse().toString();
        String reversedLongerString = new StringBuilder(longerString).reverse().toString();

        for(int i = 0; i < smallerString.length(); i++){
            if(reversedSmallerString.charAt(i) != reversedLongerString.charAt(i)){
                count++;
            }
        }
        return count;
    }



    public double calculateSimilarity(String a, String b){
        String longerString = a.length() > b.length() ? a : b;
        String smallerString = a.length() > b.length() ? b : a;

        int consistentDistance = hammingDistance(smallerString, longerString);
        int reverseDistance = reverseHammingDistance(smallerString, longerString);
        int totalDistance = (consistentDistance + reverseDistance ) / 2;

        return (double) (smallerString.length() - totalDistance) / smallerString.length();
    }
}
