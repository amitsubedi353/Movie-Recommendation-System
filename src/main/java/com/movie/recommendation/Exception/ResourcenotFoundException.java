package com.movie.recommendation.Exception;

import lombok.Data;

@Data
public class ResourcenotFoundException extends RuntimeException {
    private String resourceName;
    private String resourceFieldName;

    private Long resourceFieldValue;

    public ResourcenotFoundException(String resourceName, String resourceFieldName, Long resourceFieldValue) {
        super(String.format("%s not found with %s:%s",resourceName,resourceFieldName,resourceFieldValue));
        this.resourceName = resourceName;
        this.resourceFieldName = resourceFieldName;
        this.resourceFieldValue = resourceFieldValue;
    }
}
