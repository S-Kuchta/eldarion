package kuchtastefan.utility.annotationStrategy;

import com.google.gson.FieldAttributes;

public class AnnotationSerializationExclusionStrategy implements com.google.gson.ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(ExcludeSerialization.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
