package org.carracoo.stats.apt;

import javassist.*;
import org.carracoo.assist.AssistProcessor;
import org.carracoo.stats.ann.Track;
import org.carracoo.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Processor extends AssistProcessor {
	private static final String TRACK_STATS_PROCESSED = "TRACK_STATS_PROCESSED";
	@Override
	protected boolean filter(CtClass candidateClass) throws Exception {
		try{
			candidateClass.getField(TRACK_STATS_PROCESSED);
		} catch (NotFoundException ex){
			if(candidateClass.hasAnnotation(Track.class)){
				return true;
			}else{
				for(CtMethod method:candidateClass.getDeclaredMethods()){
					if(method.hasAnnotation(Track.class)){
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	protected void applyTransformations(ClassPool pool,CtClass cls) throws Exception {
		Boolean classTracked = false;
		String  groupName    = cls.getName();
		if(cls.hasAnnotation(Track.class)){
			Track track = (Track) cls.getAnnotation(Track.class);
			classTracked = track.enabled();
			if(!track.value().isEmpty()){
				groupName = track.value();
			}
		}
		for(CtMethod method:cls.getDeclaredMethods()){
			Boolean methodTracked = classTracked;
			String  trackName     = method.getName();
			if(method.hasAnnotation(Track.class)){
				Track track     = (Track) method.getAnnotation(Track.class);
				methodTracked   = classTracked || track.enabled();
				if(!track.value().isEmpty()){
					trackName = track.value();
				}
			}
			if(methodTracked){
				addTracking(pool,cls,method,groupName,trackName);
			}
		}
		CtField trackField = new CtField(CtClass.booleanType,TRACK_STATS_PROCESSED,cls);
		trackField.setModifiers(Modifier.STATIC|Modifier.PRIVATE);
		cls.addField(trackField,
			"org.carracoo.stats.Stats.register("+cls.getName()+".class);"
		);
	}

	private void addTracking(ClassPool pool, CtClass cls, CtMethod method, String groupName, String trackName) throws CannotCompileException, NotFoundException {

		String nName = method.getName();
		String oName = nName+"$exec";
		String type  = method.getReturnType().getName();

		method.setName(oName);

		CtMethod tracker = CtNewMethod.copy(method, nName, cls, null);

		method.setModifiers(Modifier.setPrivate(method.getModifiers()));
		CtClass[] exClasses = method.getExceptionTypes();
		String[]  exNames   = new String[exClasses.length];
		for(int i=0;i<exClasses.length;i++){
			exNames[i] =exClasses[i].getName()+".class";
		}

        StringBuffer body = new StringBuffer();
        body.append("{\n");
        if(exNames.length==0){
            body.append("Class[] errors = new Class[0];\n");
        }else {
            body.append("Class[] errors = new Class[]{").append(StringUtils.join(exNames,',')).append("};\n");
        }

        body.append("org.carracoo.stats.StatEvent event = org.carracoo.stats.Stats.track\n");
        body.append("    (\"" + groupName + "." + trackName + "\");\n");
        body.append("try{\n");
        if (!"void".equals(type)) {
            body.append("  ").append(type).append(" result = ");
        }
        body.append("  ").append(oName).append("($$);\n");
        body.append("  System.out.println(\"CALL " + groupName + "." + trackName + "\");\n");
        body.append("  event.success();\n");
        if (!"void".equals(type)) {
            body.append("  return result;\n");
        }
        body.append("}catch(RuntimeException ex){\n");
        body.append("  event.fail(ex,errors); throw ex;\n");
        body.append("}}");
        System.out.println(body.toString());
        tracker.setBody(body.toString());
        cls.addMethod(tracker);




	}
}
