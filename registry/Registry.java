package com.halotroop.api.registry;

import org.jetbrains.annotations.*;

import java.util.*;

public class Registry<T extends Registrable> {
	private static final String LOCKED;
	private static final String NO_ID_PROVIDED;
	private static final String EXISTING_ID;
	private static final String EXISTING_ENTRY;

	static {
		LOCKED = "Tried to register new entries to a locked registry! Registry: %s";
		NO_ID_PROVIDED = "No id provided for registry!";
		EXISTING_ID = "Can't register this %s with the ID %s. ID is already in use.";
		EXISTING_ENTRY = "Can't register this %s with the ID %s. Already registered this %s with the ID: %s";
	}

	private final Class<T> type;

	private final Map<String, T> ID_TO_ENTRY = new HashMap<>();
	private final Map<T, String> ENTRY_TO_ID = new HashMap<>();

	private boolean locked = false;

	public Registry(Class<T> type) {
		this.type = type;
	}

	public T register(@NotNull String id, @NotNull T entry) throws IllegalArgumentException {
		final var name = this.getName();
		if (locked) throw new RuntimeException(String.format(LOCKED, name));
		if (id.isBlank()) throw new IllegalArgumentException(NO_ID_PROVIDED);
		if (ENTRY_TO_ID.containsValue(id)) {
			throw new IllegalArgumentException(String.format(EXISTING_ID, name, id));
		}
		if (ID_TO_ENTRY.containsValue(entry)) {
			final var other_id = ENTRY_TO_ID.get(entry);
			throw new IllegalArgumentException(String.format(EXISTING_ENTRY, name, id, name, other_id));
		}

		// TODO: Illegal characters

		ENTRY_TO_ID.put(entry, id);
		ID_TO_ENTRY.put(id, entry);

		return entry;
	}

	public boolean registered(String id) {
		return ID_TO_ENTRY.containsKey(id) || ENTRY_TO_ID.containsValue(id);
	}

	public boolean registered(T entry) {
		return ENTRY_TO_ID.containsKey(entry) || ID_TO_ENTRY.containsValue(entry);
	}

	public @Nullable T get(String id) {
		return ID_TO_ENTRY.get(id);
	}

	public @Nullable String getID(T entry) {
		return ENTRY_TO_ID.get(entry);
	}

	public Class<T> getType() {
		return type;
	}

	public @NotNull String getName() {
		return type.getSimpleName();
	}

	@ApiStatus.Internal
	public void lock() {
		locked = true;
	}
}
