package br.com.lamppit.accesscontrol.serializer;

import br.com.lamppit.accesscontrol.model.enumerated.EnumSystemType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class EnumSystemTypeSerializer extends JsonSerializer<EnumSystemType> {

    @Override
    public void serialize(EnumSystemType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("id", value.getId());
        gen.writeStringField("description", value.getDescription());
        gen.writeEndObject();
    }

}