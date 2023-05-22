/*
	MIT License

	Copyright (c) 2023 Caroline Joy Bell

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
 */

package com.halotroop.api;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@SuppressWarnings("unused")
public class FileUtils {
	private FileUtils() {}

	/**
	 * Reads given resource file as a string.
	 *
	 * @param path path to the resource file
	 * @return the file's contents
	 */
	public static String loadTextResource(String path) {
		try {
			var file = getResource(path);
			return Files.readString(file.toPath(), StandardCharsets.UTF_8);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/**
	 * Attempts to save the provided object if it's an instance of {@link SelfSaver}.
	 * @param object the object to save
	 * @return the provided object
	 */
	public static <T> T save(T object) {
		if (object instanceof SelfSaver selfSaver) selfSaver.save();
		return object;
	}

	/**
	 * Gets a file from the classpath (the JAR file / resources folder)
	 * @param path the path to the file, relative to the root of the classpath
	 * @return the requested file
	 * @throws URISyntaxException if the path was invalid
	 */
	public static File getResource(String path) throws URISyntaxException {
		var url = FileUtils.class.getResource("/" + path);
		return new File(Objects.requireNonNull(url, "Couldn't load file: " + path).toURI());
	}

	/**
	 * Gets a file from the working directory (wherever the JVM was invoked)
	 * @param path file path of the desired file, relative to the working directory
	 * @return the requested file, or null if it can't be found or created.
	 */
	public static File fromWorkingDirectory(String path) {
		var file = new File(new File("").getAbsoluteFile(), path);
		try { if (file.exists() || file.createNewFile()) return file;
		} catch (Throwable t) { throw new RuntimeException(t); }
		return null;
	}
}
