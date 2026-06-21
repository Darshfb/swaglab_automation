package com.saucedemo.dataproviders;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonReader {

    public static Object[][] getJsonData(String jsonFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Map<String, String>> dataList = mapper.readValue(
                    new File("src/test/resources/testdata/" + jsonFilePath),
                    new TypeReference<List<Map<String, String>>>() {}
            );

            Object[][] dataArray = new Object[dataList.size()][1];
            for (int i = 0; i < dataList.size(); i++) {
                dataArray[i][0] = dataList.get(i);
            }
            return dataArray;

        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON test data from " + jsonFilePath, e);
        }
    }
}
