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
package org.carracoo.maven.script;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.carracoo.maven.assist.AssistExecutor;
import org.carracoo.maven.assist.AssistProcessor;
import org.carracoo.maven.assist.AssistTransformer;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import static java.lang.Thread.currentThread;

/**
 * Maven plugin that will apply <a
 * href="http://www.csg.ci.i.u-tokyo.ac.jp/~chiba/javassist/">Javassist</a>
 * class transformations on compiled classes (bytecode instrumentation).
 * 
 * @author Daniel Rochetti
 */
@Mojo(
	name = "script",
	defaultPhase = LifecyclePhase.PROCESS_CLASSES,
	requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME
)
public class ScriptMojo extends AbstractMojo {

	@Parameter(
		defaultValue = "${project}",
		property = "project",
		required = true,
		readonly = true
	)
	private MavenProject project;

	@Parameter(
		defaultValue = "build.js",
		property = "script",
		required = true,
		readonly = true
	)
	private String script;

	public void execute() throws MojoExecutionException {
		getLog().info("executing carracoo script plugin for : "+script);
		Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();
			String scriptSource = this.script;
			String scriptName = "Script";
            File file = new File(this.script);
            if(file.exists()){
				scriptSource = readFile(file);
				scriptName   = file.getAbsolutePath();
	        }

            ScriptableObject.putProperty(scope, "project", Context.javaToJS(project, scope));
			ScriptableObject.putProperty(scope, "logger", Context.javaToJS(getLog(), scope));
            cx.evaluateString( scope, scriptSource, scriptName, 1, null );
        }catch( Exception e ){
            getLog().error(e);
        }finally{
            Context.exit();
        }
	}

	public String readFile(File file) {
		String content = null;
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
