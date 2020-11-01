package util.annotation.processor;

import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;

import com.sap.cloud.sdk.service.annotationprocessor.AnnotationProcessor;

@SupportedAnnotationTypes("sdc.assets.annotations.Complexity")
public class Main {
	static class A extends AnnotationProcessor{
		@Override
		public synchronized void init(ProcessingEnvironment processingEnv) {
			System.out.println("run");
			super.init(processingEnv);
		}

		@Override
		public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment arg1) {
			System.out.println("run");
			return super.process(arg0, arg1);
		}
	}
	public static void main(String[] args) {
		
	}
}
