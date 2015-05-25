package hu.unideb.inf.elementbound.celldweller.model;

import hu.unideb.inf.elementbound.celldweller.model.Cellverse.Point;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLAdapter implements IOAdapter {

	@Override
	public void Write(File fout, Cellverse cellverse) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		
		Element root = doc.createElement("cellverse");
		for(Point p : cellverse.getAliveCells()) {
			Element cellNode = doc.createElement("cell");
			
			Element xNode = doc.createElement("x");
				xNode.appendChild(doc.createTextNode(String.valueOf(p.x)));
				
			Element yNode = doc.createElement("y");
				yNode.appendChild(doc.createTextNode(String.valueOf(p.y)));
				
			cellNode.appendChild(xNode);
			cellNode.appendChild(yNode);
			
			root.appendChild(cellNode);
		}
		
		doc.appendChild(root);
		
		//
		
		Transformer optimus = TransformerFactory.newInstance().newTransformer(); 
			optimus.setOutputProperty(OutputKeys.INDENT, "yes");
			optimus.setOutputProperty(OutputKeys.METHOD, "xml");
			optimus.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			optimus.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			
		optimus.transform(new DOMSource(doc), new StreamResult(fout));
	}

	@Override
	public void Read(File fin, Cellverse cellverse) throws IOException, SAXException, ParserConfigurationException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(fin);
		
		cellverse.clear();
		
		NodeList list = doc.getDocumentElement().getElementsByTagName("cell");
		for(int i = 0; i < list.getLength(); i++) {
			Node cellNode = list.item(i);
			NodeList cellDataList = cellNode.getChildNodes();
			
			Point cell = new Point(0, 0);
			for(int j = 0; j < cellDataList.getLength(); j++) {
				Node cellData = cellDataList.item(j);
				if(cellData.getNodeName().equals("x"))
					cell.x = Integer.valueOf(cellData.getFirstChild().getNodeValue());
				
				if(cellData.getNodeName().equals("y"))
					cell.y = Integer.valueOf(cellData.getFirstChild().getNodeValue());
			}
			
			cellverse.setCell(cell, true);
		}
		
		cellverse.swapBuffers();
	}

}
