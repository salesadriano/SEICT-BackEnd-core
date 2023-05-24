package br.com.lamppit.accesscontrol.serializer;

import br.com.lamppit.accesscontrol.model.enumerated.EnumSystemType;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class EnumSystemTypeDeserializer extends JsonDeserializer<EnumSystemType> {

	@Override
	public EnumSystemType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException, JacksonException {
		JsonNode node = jsonParser.getCodec().readTree(jsonParser);
		EnumSystemType type = null;

		if (node != null) {
			type = EnumSystemType.getById(node.get("id").asInt());
			if (type != null) {
				return type;
			}
		}

		return type;
	}

}