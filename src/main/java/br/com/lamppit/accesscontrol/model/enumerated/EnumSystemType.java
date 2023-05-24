package br.com.lamppit.accesscontrol.model.enumerated;

import br.com.lamppit.accesscontrol.serializer.EnumSystemTypeDeserializer;
import br.com.lamppit.accesscontrol.serializer.EnumSystemTypeSerializer;
import br.com.lamppit.core.exception.EntityValidationException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

@Getter
@JsonSerialize(using = EnumSystemTypeSerializer.class)
@JsonDeserialize(using = EnumSystemTypeDeserializer.class)
public enum EnumSystemType {

    LOCAL(0, "Local"),
    CLOUD(1, "Cloud");

    private Integer id;
    private String description;



    private EnumSystemType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static EnumSystemType getById(Integer id) {
        for (EnumSystemType enumSystemType : EnumSystemType.values()) {
            if (enumSystemType.getId().equals(id)) {
                return enumSystemType;
            }
        }
        String msg = "except.error.notexist.message.type";
        throw new EntityValidationException(msg);
    }

}
