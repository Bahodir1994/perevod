package uz.perevods.perevod.service.helperClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationError {
    private Map<String, List<String>> errors = new HashMap<>();

    public void addError(String field, String errorMessage) {
        errors.computeIfAbsent(field, key -> new ArrayList<>()).add(errorMessage);
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}