package io.github.shaksternano.entranced.commonside.fabricasm.fabric;

import com.chocohead.mm.api.ClassTinkerers;

/**
 * Represents an {@link Enum} added to an existing enum class.
 */
public interface CustomEnum {

    /**
     * Gets the name of the enum
     * @return The name of the enum.
     */
    String getName();

    /**
     * Gets a custom {@link Enum} from a {@link CustomEnum}.
     * @param type The class the enum belongs to.
     * @param customEnum The CustomEnum instance representing the enum.
     * @return The enum.
     *
     * @throws NullPointerException If {@code type} is {@code null}.
     * @throws IllegalArgumentException If no entry with the given CustomEnum can be found in type.
     */
    static <E extends Enum<E>> E getEnum(Class<E> type, CustomEnum customEnum) {
        return ClassTinkerers.getEnum(type, customEnum.getName());
    }
}
