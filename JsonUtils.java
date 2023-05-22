package com.halotroop.api;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;

import java.io.*;

/**
 * Utilities for loading JSON5 files.<br>
 * This file can be safely deleted if you do not require JSON file utilities.
 * @apiNote requires <a href="https://falkreon.github.io/Jankson">Jankson</a>.
 * @see FileUtils
 * @author halotroop2288
 */
public final class JsonUtils {
	public static final Jankson JANKSON = Jankson.builder().build();

	/**
	 * Loads a JSON5 file from the classpath.
	 * @see FileUtils#getResource(String)
	 */
	public static <T> T loadJsonResource(Class<T> type, String path) {
		path = filePathToJson5(path);

		try {
			return loadJson(type, FileUtils.getResource(path));
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	/**
	 * Loads a JSON5 file from the working directory.
	 * @see FileUtils#fromWorkingDirectory(String)
	 */
	public static <T> T loadJsonFile(Class<T> type, String path) {
		try {
			var file = FileUtils.fromWorkingDirectory(filePathToJson5(path));
			if (file != null) return loadJson(type, file);
			return FileUtils.save(type.getDeclaredConstructor().newInstance());
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	/**
	 * Makes sure a valid JSON5 file extension is attached to the end of the path string.
	 *
	 * @param path the path to check
	 * @return the converted string
	 */
	private static String filePathToJson5(String path) {
		return StringUtils.hasAnyExtension(path, "json", "json5") ? path : path + ".json5";
	}

	/**
	 * Parses the given file into the given type.
	 * @param type the type of POJO to return
	 * @param file the file to load
	 * @return the POJO loaded
	 * @param <T> the POJO type
	 * @throws SyntaxError if the given file has invalid syntax
	 * @throws IOException if there was an error loading the file
	 */
	public static <T> T loadJson(Class<T> type, File file) throws SyntaxError, IOException {
		return JANKSON.fromJson(JANKSON.load(file), type);
	}
}
