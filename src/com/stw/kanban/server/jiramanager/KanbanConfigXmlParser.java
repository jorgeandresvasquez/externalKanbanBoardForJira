package com.stw.kanban.server.jiramanager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.google.inject.BindingAnnotation;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KanbanConfigXmlParser {

	private static final int DEFAULT_MAX_ELEMENTS_PER_STEP = 100;
	private Map<String, String> globalFiltersMap;
	private String fullyQualifiedXmlFileName;
	private boolean parseRequired;
	private Map<String, KanbanConfig> kanbanConfigMap = new HashMap<String, KanbanConfig>();
	
	@Inject
	public KanbanConfigXmlParser(@XmlKanbanConfigPath String xmlFileToParse) {
		this.fullyQualifiedXmlFileName = xmlFileToParse;
		this.parseRequired = true;
	}
	
	@SuppressWarnings("unchecked")
	public void parse() throws KanbanConfigParsingException {
		
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(false);
			factory.setNamespaceAware(true);

			SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");

			//This path is hard-coded here as it is tightly bound with the parser therefore no need to inject it from
			//outside, the only condition is that it is present in the deployed application at the root level (under "classes")
			URL url = Thread.currentThread().getContextClassLoader().getResource("KanbanConfig.xsd");
			Document document = null;
			try {
				Schema schema = schemaFactory.newSchema(new StreamSource(url.openStream(), url.toString()));
				factory.setSchema(schema);
				SAXParser parser = factory.newSAXParser();
				SAXReader reader = new SAXReader( parser.getXMLReader());
				document = reader.read(fullyQualifiedXmlFileName);
			} catch (Exception e) {
				throw new KanbanConfigParsingException(e);
			}
			List<StepConfig> stepQueryList = new ArrayList<StepConfig>();
			Element root = document.getRootElement();
			String kanbanId = root.attributeValue("id");
			String description = root.attributeValue("description");
			String maxElementsPerStepStr = root.attributeValue("maxElementsPerStep");
			int maxElementsPerStepInt = DEFAULT_MAX_ELEMENTS_PER_STEP;
			if (maxElementsPerStepStr != null) {
				maxElementsPerStepInt = Integer.parseInt(maxElementsPerStepStr);
			}
			globalFiltersMap = new HashMap<String, String>();
			List<Element> globalJQLList = root.selectNodes("//globalFilters/jql");
			for (Element element : globalJQLList) {
				String id = element.attributeValue("id");
				globalFiltersMap.put(id, element.getTextTrim());
			}
			// iterate through child elements of root with element name "stepConfig"
			for (Iterator<Element> i = root.elementIterator("stepConfig"); i.hasNext();) {
				Element stepConfigElement = i.next();
				StepConfig stepConfig = new StepConfig();
				stepConfig.setName(stepConfigElement.attributeValue("name"));
				stepConfig.setQuery(replaceGlobalFilters(stepConfigElement.element("jql").getTextTrim()));
				stepQueryList.add(stepConfig);
			}
			KanbanConfig kanbanConfig = new KanbanConfig(kanbanId, description, maxElementsPerStepInt, stepQueryList);
			kanbanConfigMap.put(kanbanId, kanbanConfig);

		parseRequired = false;
	}
	
	private String replaceGlobalFilters(String sql) {
		//Iterate over the keys in the map and look them up in the String
		Set<String> keys = globalFiltersMap.keySet();
		String output = sql;
		for (String key : keys) {
			String keyWithPreffix = "\\$" + key;
			output = output.replaceAll(keyWithPreffix, globalFiltersMap.get(key));
		}
		return output;
	}
	
	private void verifyParsing() throws KanbanConfigParsingException {
		if (parseRequired) {
			parse();
		}
	}
	
	public KanbanConfig getKanbanConfig(String kanbanConfigId) throws Exception {
		verifyParsing();
		if (!kanbanConfigMap.containsKey(kanbanConfigId)) {
			throw new KanbanConfigNotFoundException("No configuration defined for kanbanConfig with id: " + kanbanConfigId);
		}
		return kanbanConfigMap.get(kanbanConfigId);
	}

	
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD, ElementType.PARAMETER})
	@BindingAnnotation
	public @interface XmlKanbanConfigPath {}
}
