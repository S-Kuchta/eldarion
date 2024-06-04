package kuchtastefan.utility.annotationStrategy;

import com.google.gson.FieldAttributes;

public class AnnotationDeserializationExclusionStrategy implements com.google.gson.ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return fieldAttributes.getAnnotation(ExcludeDeserialization.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> aClass) {
        return false;
    }
}
