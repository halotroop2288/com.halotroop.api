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

import javax.xml.bind.*;
import java.io.*;

/**
 * Utils for loading XML files.<br>
 * This file can be safely deleted if you do not require XML file utilities.
 * @apiNote requires <a href="https://javaee.github.io/jaxb-v2">JAXB</a>.
 * @see FileUtils
 * @author halotroop2288
 */
public final class XmlUtils {
	/**
	 * Loads an XML file from the classpath.
	 * @see FileUtils#getResource(String)
	 */
	public static <T> T loadXmlResource(Class<T> type, String path) {
		try {
			return loadXml(type, FileUtils.getResource(filePathToXml(path)));
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	/**
	 * Loads an XML file from the working directory.
	 * @see FileUtils#fromWorkingDirectory(String)
	 */
	public static <T> T loadXmlFile(Class<T> type, String path) {
		try {
			var file = FileUtils.fromWorkingDirectory(filePathToXml(path));
			if (file != null) return loadXml(type, file);
			return FileUtils.save(type.getDeclaredConstructor().newInstance());
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	/**
	 * Makes sure a valid XML file extension is attached to the end of the path string.
	 *
	 * @param path the path to check
	 * @return the converted string
	 */
	private static String filePathToXml(String path) {
		return StringUtils.hasAnyExtension(path, "xml", "html", "svg") ? path : path + ".xml";
	}

	@SuppressWarnings("unchecked")
	private static <T> T loadXml(Class<T> type, File file) throws JAXBException, FileNotFoundException {
		return (T) JAXBContext.newInstance(type).createUnmarshaller().unmarshal(new FileReader(file));
	}
}
