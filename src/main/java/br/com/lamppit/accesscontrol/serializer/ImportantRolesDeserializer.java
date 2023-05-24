package br.com.lamppit.accesscontrol.serializer;

import br.com.lamppit.accesscontrol.model.enumerated.ImportantRoles;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;

public class ImportantRolesDeserializer extends JsonDeserializer<ImportantRoles> {

    @Override
    public ImportantRoles deserialize(JsonParser p, DeserializationContext deserializationContext)
            throws IOException, JacksonException {

        JsonNode node = p.getCodec().readTree(p);

        int id = (Integer) ((IntNode) node.get("id")).numberValue();

        ImportantRoles type = null;

        if (node != null) {
            type = ImportantRoles.getById(id);
            if (type != null) {
                return type;
            }
        }

        return type;

    }

}