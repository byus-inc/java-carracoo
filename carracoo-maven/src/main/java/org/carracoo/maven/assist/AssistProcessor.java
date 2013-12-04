/*
 * Copyright 2012 http://github.com/drochetti/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.carracoo.maven.assist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.io.FilenameUtils.removeExtension;

/**
 * Base class for class transformation logic.
 * @author Daniel Rochetti
 */
public abstract class AssistProcessor {

	private static Logger logger = Logger.getLogger(AssistProcessor.class.getName());

	/**
	 * <p>Concrete implementations must implement all transformations on this method.
	 * You can use Javassist API to add/remove/replace methods, attributes and more.
	 * Only classes approved by {@link #filter(CtClass)} are considered.</p>
	 *
	 * @param classToTransform The class to transform.
	 * @see #filter(CtClass)
	 * @throws Exception if any error occur during the transformation.
	 */
	protected abstract void applyTransformations(ClassPool pool,CtClass classToTransform) throws Exception;

	/**
	 * <p>Test if the given class is suitable for applying transformations or not.</p>
	 * <p>For example, if the class is a specific type:</p>
	 * <code><pre>
	 * CtClass myInterface = ClassPool.getDefault().get(MyInterface.class.getName());
	 * return candidateClass.subtypeOf(myInterface);
	 * </pre></code>
	 *
	 * @param candidateClass
	 * @return {@code true} if the Class should be transformed; {@code false} otherwise.
	 * @throws Exception
	 */
	protected boolean filter(final CtClass candidateClass) throws Exception {
		return true;
	}

	/**
	 * <p>Configure this instance by passing {@link Properties}.</p>
	 * @param properties maybe <code>null</code> or empty
	 */
	protected void configure(final Properties properties) {
		return;
	}

	/**
	 * <p>
	 * Search for class files on a directory, load each one as {@link CtClass}, filter
	 * the valid candidates (using {@link #filter(CtClass)}) and apply transformation to each one
	 * ({@link #applyTransformations(ClassPool,CtClass)}).
	 * </p>
	 * <p>
	 * <strong>Limitation:</strong> do not search inside .jar files yet.
	 * </p>
	 * @param dir root directory.
	 * @see #applyTransformations(ClassPool,CtClass)
	 */
	public final void transform(final String dir) {
		if( null == dir || dir.trim().isEmpty() || !new File(dir).isDirectory()) {
			return;
		}
		try {
			// create new classpool for transform; don't blow up the default
			final ClassPool classPool = new ClassPool(ClassPool.getDefault());
			//classPool.childFirstLookup = true;
			classPool.appendClassPath(dir);
			classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
			classPool.appendSystemPath();
			debugClassLoader(classPool);
			final Iterator<String> classNames = iterateClassnames(dir);
			while (classNames.hasNext()) {
				final String className = classNames.next();
				try {
					classPool.importPackage(className);
					final CtClass candidateClass = classPool.get(className);

					if (filter(candidateClass)) {
						applyTransformations(classPool,candidateClass);
						candidateClass.writeFile(dir);
						logger.log(Level.INFO,String.format(
							"Class %s instrumented by %s", className, getClass().getName()
						));
					}
				} catch (final NotFoundException e) {
					logger.log(Level.WARNING,String.format(
						"Class %s could not not be resolved due to dependencies not found on " +
						"current classpath (usually your class depends on \"provided\" scoped dependencies).",
						className
					));
					continue;
				} catch ( final Exception ex) { // EOFException ...
					logger.log(Level.SEVERE,String.format(
						"Class %s could not not be resolved due to dependencies not found on " +
						"current classpath (usually your class depends on \"provided\" scoped dependencies).",
						className
					),ex);
					continue;
				}
			}
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected Iterator<String> iterateClassnames(final String dir) {
		
		return new Iterator<String>() {
			final String[] extensions = { "class" };
			final Iterator<File> classFiles = FileUtils.iterateFiles(new File(dir), extensions, true);

			@Override
			public boolean hasNext() {
				return classFiles.hasNext();
			}

			@Override
			public String next() {
				final File classFile = classFiles.next();
				try {
					final String qualifiedFileName = classFile.getCanonicalPath().substring(dir.length() + 1);
					return removeExtension(qualifiedFileName.replace(File.separator, "."));
				} catch (final IOException e) {
					throw new RuntimeException(e.getMessage());
				}
			}

			@Override
			public void remove() {
				classFiles.remove();
			}
		};
	}


	private void debugClassLoader(final ClassPool classPool) {
		if (!logger.isLoggable(Level.INFO)) {
			return;
		}

		logger.log(Level.FINE,String.format(
			" - classPool: %s", classPool.toString()
		));
		ClassLoader classLoader = classPool.getClassLoader();
		while (classLoader != null) {
			logger.log(Level.INFO,String.format(
				" -- %s : %s",
				classLoader.getClass().getName(),
				classLoader.toString()
			));
			if (classLoader instanceof URLClassLoader) {
				logger.log(Level.INFO, String.format(
						" --- urls: %s ",
						Arrays.deepToString(
								((URLClassLoader) classLoader).getURLs()
						)
				));
			}
			classLoader = classLoader.getParent();
		}
	}

}
