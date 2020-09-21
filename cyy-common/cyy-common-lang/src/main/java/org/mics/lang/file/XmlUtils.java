package org.mics.lang.file;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mics.lang.exception.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * xml工具
 * @author mics
 * @date 2020年7月9日
 * @version  1.0
 */
public class XmlUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtils.class);
	
	public static DocumentBuilder newDocumentBuilder()  {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
			documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
	        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
	        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
	        documentBuilderFactory.setXIncludeAware(false);
	        documentBuilderFactory.setExpandEntityReferences(false);
	        return documentBuilderFactory.newDocumentBuilder();
		} catch (Exception e) {
			throw new XmlException("创建xml-dom文档失败！",e);
		}
        
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }
    
    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML)  {
    	try {
			Map<String, String> data = new HashMap<String, String>();
			DocumentBuilder documentBuilder = newDocumentBuilder();
			InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
			org.w3c.dom.Document doc = documentBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int idx = 0; idx < nodeList.getLength(); ++idx) {
			    Node node = nodeList.item(idx);
			    if (node.getNodeType() == Node.ELEMENT_NODE) {
			        org.w3c.dom.Element element = (org.w3c.dom.Element) node;
			        data.put(element.getNodeName(), element.getTextContent());
			    }
			}
			stream.close();
		    return data;
	    } catch (Exception ex) {
	    	LOGGER.error("Invalid XML, can not convert to map. Error message: {}. XML content: {}", ex.getMessage(), strXML);
			throw new XmlException("xml转化 map失败！",ex);
	    }
	}

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXmlStr(Map<String, String> data)  {
    	try {
	        org.w3c.dom.Document document = newDocument();
	        org.w3c.dom.Element root = document.createElement("xml");
	        document.appendChild(root);
	        for (String key: data.keySet()) {
	            String value = data.get(key);
	            if (value == null) {
	                value = "";
	            }
	            value = value.trim();
	            org.w3c.dom.Element filed = document.createElement(key);
	            filed.appendChild(document.createTextNode(value));
	            root.appendChild(filed);
	        }
	        TransformerFactory tf = TransformerFactory.newInstance();
	        Transformer transformer = tf.newTransformer();
	        DOMSource source = new DOMSource(document);
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        transformer.transform(source, result);
	        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
            writer.close();
            return output;
    	} catch (Exception ex) {
			throw new XmlException("map转化 xml失败！",ex);
	    }
    }

}
