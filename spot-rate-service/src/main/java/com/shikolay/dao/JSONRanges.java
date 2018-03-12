package com.shikolay.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shikolay.dto.RangesData;
import com.shikolay.range.RangePool;

import java.io.File;
import java.io.IOException;

public class JSONRanges {
    private static JSONRanges instance = null;

    private ObjectMapper mapper;
    private RangesData rawData;
    private RangePool parsedPool;

    private JSONRanges() {
        mapper = new ObjectMapper();
    }

    public void loadDataFromFile(String filePath) throws IOException {
        rawData = mapper.readValue(new File(filePath), RangesData.class);
        parsedPool = new RangePool(rawData);
    }

    public RangePool getParsedPool() {
        return parsedPool;
    }

    public static JSONRanges getInstance() {
        if (instance == null) {
            instance = new JSONRanges();
        }

        return instance;
    }

}
