package doc.backendapi.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER,
    ADMIN,
    MANAGER;

    @JsonCreator
    public static Role forValue(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role name: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
