package com.taohuahua.apt_processor;

import com.google.auto.service.AutoService;
import com.taohuahua.apt_anno.Builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * @Desc: created by taohuahua on 2019-01-28
 */

@AutoService(Processor.class)
public class BuilderProcessor extends AbstractProcessor {
    private Elements mElementUtils;
    private Types mTypeUtils;
    private Filer mFiler;
    private BuilderCodeGenerator mCodeGenerator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEvn) {
        super.init(processingEvn);
        mElementUtils = processingEvn.getElementUtils();
        mTypeUtils = processingEnv.getTypeUtils();
        mFiler = processingEvn.getFiler();
        mCodeGenerator = new BuilderCodeGenerator(mElementUtils, mFiler);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annoTypes = new HashSet<>();
        annoTypes.add(Builder.class.getCanonicalName());
        return annoTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Builder
                    .class);
            for (Element element : elements) {
                if (!ValidCheckUtil.isValidClass(element)) {
                    continue;
                }

                List<? extends Element> memberFields = mElementUtils.getAllMembers((TypeElement)
                        element);
                List<VariableElement> annoFields = new ArrayList<>();

                if (memberFields == null) {
                    return false;
                }

                for (Element member : memberFields) {
                    if (ValidCheckUtil.isValidField(member)) {
                        annoFields.add((VariableElement) member);
                    }
                }

                mCodeGenerator.generatorCode((TypeElement) element, annoFields);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }
}
