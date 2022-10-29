package com.rmn.toolkit.cards.query.testUtil.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GsonLocalDate implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    @Override
    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String ldString = jsonElement.getAsString();
        return LocalDate.parse(ldString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public JsonElement serialize(LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
