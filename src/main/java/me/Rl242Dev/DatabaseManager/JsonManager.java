package me.Rl242Dev.DatabaseManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonManager {
    private final ObjectMapper objectMapper;
    private final JsonNode rootNode;

    public JsonManager(File configFile) {
        this.objectMapper = new ObjectMapper();
        this.rootNode = parseJson(configFile);
    }

    private JsonNode parseJson(File configFile) {
        try {
            return objectMapper.readTree(configFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse JSON file: " + e.getMessage());
        }
    }

    public String getString(String fieldName) {
        return getField(fieldName).asText();
    }

    public boolean getBoolean(String fieldName) {
        return getField(fieldName).asBoolean();
    }

    public int getInt(String fieldName) {
        return getField(fieldName).asInt();
    }

    public List<String> getStringList(String fieldName) {
        JsonNode field = getField(fieldName);
        if (field.isArray()) {
            List<String> list = new ArrayList<>();
            for (JsonNode element : field) {
                if (element.isTextual()) {
                    list.add(element.asText());
                }
            }
            return list;
        } else {
            return Collections.emptyList();
        }
    }

    private JsonNode getField(String fieldName) {
        String[] fieldParts = fieldName.split("\\.");
        JsonNode currentNode = rootNode;
        for (String part : fieldParts) {
            if (currentNode != null && currentNode.has(part)) {
                currentNode = currentNode.get(part);
            } else {
                throw new RuntimeException("Field not found: " + fieldName);
            }
        }
        return currentNode;
    }
}
