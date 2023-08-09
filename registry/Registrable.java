package com.halotroop.api.registry;

public interface Registrable {
	Registry<? extends Registrable> getRegistry();
}
