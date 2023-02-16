package com.bernalvarela.customerservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    public static String asJsonString(final Object obj) {
        String retValue = null;
        try {
            retValue = new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("ERROR PARSING JSON. {}", e.getMessage());
        }
        return retValue;
    }
}
