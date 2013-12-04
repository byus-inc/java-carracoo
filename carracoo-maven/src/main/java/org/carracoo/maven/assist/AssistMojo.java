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

import static java.lang.Thread.currentThread;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

/**
 * Maven plugin that will apply <a
 * href="http://www.csg.ci.i.u-tokyo.ac.jp/~chiba/javassist/">Javassist</a>
 * class transformations on compiled classes (bytecode instrumentation).
 * 
 * @author Daniel Rochetti
 */
@Mojo(
	name = "assist",
	defaultPhase = LifecyclePhase.PROCESS_CLASSES,
	requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class AssistMojo extends AbstractMojo {

	private static final Class<AssistProcessor> TRANSFORMER_TYPE = AssistProcessor.class;

	@Parameter(defaultValue = "${project}", property = "project", required = true, readonly = true)
	private MavenProject project;

	@Parameter(defaultValue = "false", property = "includeTestClasses", required = true)
	private Boolean includeTestClasses;

	@Parameter(property = "transformers", required = true)
	private AssistTransformer[] transformers;

	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException {
		final AssistExecutor executor = new AssistExecutor();
		try {
			final List<URL> classPath = new ArrayList<URL>();
			final String outputDirectory = project.getBuild()
					.getOutputDirectory();
			final String testOutputDirectory = project.getBuild()
					.getTestOutputDirectory();
			final List<String> runtimeClasspathElements = project
					.getRuntimeClasspathElements();
			for (final String runtimeResource : runtimeClasspathElements) {
				classPath.add(resolveUrl(runtimeResource));
			}
			classPath.add(resolveUrl(outputDirectory));

			executor.setAdditionalClassPath(classPath.toArray(new URL[classPath
					.size()]));
			executor.setTransformerClasses(instantiateTransformerClasses(
					currentThread().getContextClassLoader(), transformers));

			executor.setOutputDirectory(outputDirectory);
			executor.execute();
			if (includeTestClasses) {
				classPath.add(resolveUrl(testOutputDirectory));
				executor.setOutputDirectory(testOutputDirectory);
				executor.execute();
			}

		} catch (final Exception e) {
			getLog().error(e.getMessage(), e);
			throw new MojoExecutionException(e.getMessage(), e);
		}
	}

	/**
	 * @param contextClassLoader
	 * @param transformerClasses
	 * @return array of passed transformer class name instances
	 * @throws ClassNotFoundException
	 * @throws NullPointerException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws MojoExecutionException
	 * @see #instantiateTransformerClass(ClassLoader, AssistTransformer)
	 */
	protected AssistProcessor[] instantiateTransformerClasses(
			final ClassLoader contextClassLoader,
			final AssistTransformer... transformerClasses)
			throws ClassNotFoundException, NullPointerException,
			InstantiationException, IllegalAccessException,
			MojoExecutionException {
		if (null == transformerClasses || transformerClasses.length <= 0) {
			throw new MojoExecutionException(
					"Invalid transformer classes passed");
		}
		final List<AssistProcessor> transformerInstances = new LinkedList<AssistProcessor>();
		for (AssistTransformer transformerClass : transformerClasses) {
			final AssistProcessor transformerInstance = instantiateTransformerClass(
					contextClassLoader, transformerClass);
			configureTransformerInstance(transformerInstance,
					transformerClass.getProperties());
			transformerInstances.add(transformerInstance);
		}
		return transformerInstances
				.toArray(new AssistProcessor[transformerInstances.size()]);
	}

	/**
	 * Instantiate the class passed by {@link AssistTransformer} configuration object.
	 * 
	 * @param contextClassLoader
	 * @param transformerClass
	 * @return new instance of passed transformer class name
	 * @throws ClassNotFoundException
	 * @throws NullPointerException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws MojoExecutionException
	 */
	protected AssistProcessor instantiateTransformerClass(
			final ClassLoader contextClassLoader,
			final AssistTransformer transformerClass
	)
	throws
	ClassNotFoundException, NullPointerException,
	InstantiationException, IllegalAccessException,
	MojoExecutionException {
		if (null == transformerClass
				|| transformerClass.getClassName().trim().isEmpty()) {
			throw new MojoExecutionException(
					"Invalid transformer class name passed");
		}
		final Class<?> transformerClassInstanz = Class.forName(transformerClass
				.getClassName().trim(), true, contextClassLoader);
		if (TRANSFORMER_TYPE.isAssignableFrom(transformerClassInstanz)) {
			return TRANSFORMER_TYPE.cast(transformerClassInstanz.newInstance());
		} else {
			throw new MojoExecutionException(
					"Transformer class must inherit from "
							+ TRANSFORMER_TYPE.getName());
		}
	}

	/**
	 * Configure the passed {@link AssistProcessor} instance using the passed {@link Properties}.
	 * 
	 * @param transformerInstance - maybe <code>null</code>
	 * @param properties - maybe <code>null</code> or empty
	 */
	protected void configureTransformerInstance(final AssistProcessor transformerInstance, final Properties properties) {
		if( null == transformerInstance ) {
			return;
		}
		transformerInstance.configure(properties);
	}

	private URL resolveUrl(final String resource) {
		try {
			return new File(resource).toURI().toURL();
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
