package util.plugin;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import lombok.NoArgsConstructor;
import util.mapconst.MapClassFieldsDef;

/**
 * Mybatis generator plugin to add annotations at the fields of generated Model classes
 */
@NoArgsConstructor
public class AddModelFieldsAnnotationPlugin extends PluginAdapter {
	
	public static final String IMPORT_CLASS = "import-class";
	public static final String ANNOTATION_STRING = "annotation-string";
	
	private String importClass;
	private String annotationString;

	@Override
	public boolean validate(List<String> warnings) {
		importClass = properties.getProperty(IMPORT_CLASS);
		annotationString = properties.getProperty(ANNOTATION_STRING);
		
		String warning = "Property %s not set for plugin %s";
		if (!stringHasValue(importClass)) {
			warnings.add(String.format(warning, IMPORT_CLASS, this.getClass().getSimpleName()));
		}
		if (!stringHasValue(annotationString)) {
			warnings.add(String.format(warning, ANNOTATION_STRING, this.getClass().getSimpleName()));
		}
		
		return stringHasValue(importClass) && stringHasValue(annotationString);
	}

	@Override
	public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
		// set import class
		topLevelClass.addImportedType(new FullyQualifiedJavaType(importClass));
		
		// set fields annotation
		String annotationToSet = annotationString;
		
		MapClassFieldsDef mapClass = MapClassFieldsDef.convert(topLevelClass.getType().getShortName());

		String note = mapClass.getNote(field.getName());
		if (note != null) {
			annotationToSet = annotationString.replaceAll("\"\"", "\"" + note + "\"");
		}
		field.addAnnotation(annotationToSet);

		return true;
    }
}
