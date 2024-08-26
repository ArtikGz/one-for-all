package com.koralix.oneforall.settings;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * A class to store a config value, with a nominal value, a default value, and a current value.
 * The nominal value is the value set by default.
 * The default value is the value that the config value will be set to if the user resets it.
 * The value is the current value of the config value.
 * @param <T> the type of the config value
 */
public class ConfigValueWrapper<T> implements ConfigValue<T> {
    private final Class<T> clazz;
    private final T nominalValue;
    private final ConfigValidator<T> validator;
    private final Predicate<ServerCommandSource> permission;

    SettingEntry<T> entry = null;

    private T defaultValue;
    private T value;

    ConfigValueWrapper(
            @NotNull Class<T> clazz,
            T nominalValue,
            @NotNull ConfigValidator<T> validator,
            @NotNull Predicate<ServerCommandSource> permission
    ) {
        Text err = validator.test(nominalValue);
        if (err != null) throw new IllegalArgumentException(err.getString());

        this.clazz = clazz;
        this.nominalValue = nominalValue;
        this.validator = validator;
        this.permission = permission;

        this.defaultValue = nominalValue;
        this.value = nominalValue;
    }

    @Override
    public SettingsRegistry registry() {
        return this.entry.registry();
    }

    @Override
    public SettingEntry<T> entry() {
        return this.entry;
    }

    @Override
    public boolean permission(ServerCommandSource source) {
        return this.permission.test(source);
    }

    @Override
    public T nominalValue() {
        return this.nominalValue;
    }

    @Override
    public T defaultValue() {
        return this.defaultValue;
    }

    @Override
    public Text defaultValue(T value) {
        return this.tested(value, () -> this.defaultValue = value);
    }

    @Override
    public T value() {
        return this.value;
    }

    @Override
    public Text value(T value) {
        return this.tested(value, () -> this.value = value);
    }

    @Override
    public Class<T> clazz() {
        return this.clazz;
    }

    private @Nullable Text tested(T value, Runnable action) {
        Text err = this.validator.test(value);
        if (err != null) return err;
        action.run();
        return null;
    }
}
