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

/**
 * A POJO class that's capable of saving itself without any extra context.<br>
 * The default implementation is to save the POJO as a prettified JSON5 file, using {@link JsonUtils#JANKSON Jankson}.
 */
public interface SelfSaver {
	void save();

	/**
	 * Save the POJO as the given file
	 * @param file the file to save as
	 */
	default void save(File file) {
		var result = JsonUtils.JANKSON.toJson(this).toJson(true, true);
		try {
			var out = new FileOutputStream(file);

			out.write(result.getBytes());

			out.flush();
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
