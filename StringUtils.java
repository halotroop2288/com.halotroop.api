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

import java.util.Arrays;

/**
 * Reduces repetition in string processing.
 * @author halotroop2288
 */
public final class StringUtils {
	private StringUtils() {}

	/**
	 * @param string the string to test
	 * @param substrings the substrings to check for
	 * @return whether any of the given substrings are found in the string
	 */
	public static boolean containsAny(String string, String... substrings) {
		return Arrays.stream(substrings).anyMatch(string::contains);
	}

	/**
	 * @param string the string to test
	 * @param endings the ending to check for
	 * @return whether any of the given endings are attached to the end of the given string
	 * @see #hasAnyExtension(String, String...) 
	 */
	public static boolean endsWithAny(String string, String... endings) {
		return Arrays.stream(endings).anyMatch(string::endsWith);
	}

	/**
	 * @param path the string to test
	 * @param extensions the extension to check for, not including a {@code .}
	 * @return whether any of the given extensions are attached to the end of the given path
	 * @see #endsWithAny(String, String...)
	 */
	public static boolean hasAnyExtension(String path, String... extensions) {
		return Arrays.stream(extensions).anyMatch(extension -> path.endsWith('.'+extension));
	}
}
