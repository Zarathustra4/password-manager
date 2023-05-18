package com.passpnu.passwordmanager.util;

import com.passpnu.passwordmanager.dto.AnalysisDto;
import com.passpnu.passwordmanager.dto.SimilarPasswordDto;
import com.passpnu.passwordmanager.service.ServiceEntityService;
import org.springframework.stereotype.Component;

import javax.naming.NameNotFoundException;
import java.util.List;

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

    public void findSimilarities(List<AnalysisDto> analysisDtoList,
                                 ServiceEntityService serviceEntityService) throws NameNotFoundException {

        final int listSize = analysisDtoList.size();
        String firstPassword;
        String secondPassword;
        AnalysisDto firstAnalysisDto;
        AnalysisDto secondAnalysisDto;
        double similarity;
        SimilarPasswordDto firstToSecond;
        SimilarPasswordDto secondToFirst;

        for(int i = 0; i < listSize; i++){
            firstAnalysisDto = analysisDtoList.get(i);
            firstPassword = firstAnalysisDto.getPassword();

            for(int j = i + 1; j < listSize; j++){

                secondAnalysisDto = analysisDtoList.get(j);
                secondPassword = secondAnalysisDto.getPassword();

                similarity = calculateSimilarity(firstPassword, secondPassword);
                if(similarity > 0.5){

                    secondToFirst = SimilarPasswordDto.builder()
                            .password(secondPassword)
                            .serviceDomain(serviceEntityService.getDomainById(secondAnalysisDto.getServiceId()))
                            .similarity(similarity)
                            .serviceId(secondAnalysisDto.getServiceId())
                            .build();

                    firstToSecond = SimilarPasswordDto.builder()
                            .password(firstPassword)
                            .serviceDomain(serviceEntityService.getDomainById(firstAnalysisDto.getServiceId()))
                            .similarity(similarity)
                            .serviceId(firstAnalysisDto.getServiceId())
                            .build();

                    firstAnalysisDto.addSimilarPassword(secondToFirst);
                    secondAnalysisDto.addSimilarPassword(firstToSecond);
                }
            }
        }
    }
}
