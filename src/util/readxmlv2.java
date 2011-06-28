/*******************************************************************************
 * Copyright (c) 2011 Enrique Munoz de Cote.
 * repeatedgames is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * repeatedgames is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with repeatedgames.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Please send an email to: jemc@inaoep.mx for comments or to become part of this project.
 * Contributors:
 *     Enrique Munoz de Cote - initial API and implementation
 ******************************************************************************/
package util;



import java.io.IOException;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

// class to read values from xml file
public class readxmlv2 {
	
	private static String type;
	private static int rows;
	private static int columns;
	private static String puds;
	private static String walls;
	private static String stocwalls;
	private static int collision;
	private static int steps;
	private static int players;
	
	private static int iterations;
	private static String filename;
	private static String cCoordinates;
	private static String pCoordinates;
	private static int coinValue;
	
	// method that reads data from xml
	public static void getData (String files) throws SAXException, IOException{
		
		
		
		DOMParser parser = new DOMParser();
		
		//DocumentBuilderFactory DocBuilderFactory = DocumentBuilderFactory.newInstance();
	
	
		
		//TODO: change to file name at the end.
		parser.parse(files);
		Document file = parser.getDocument();
		Element docEle = file.getDocumentElement();
		
		NodeList nl = docEle.getElementsByTagName("environment");
		Element el = (Element)nl.item(0);
		 type = getTextValue (el, "type");
		
		if("grid".equalsIgnoreCase(type)) 
			{
			
			rows = getIntValue (el,"rows");
			
			columns = getIntValue (el, "columns");
			//puds = getTextValue (el, "pods");
			walls = getTextValue (el, "walls");
			stocwalls = getTextValue (el, "stocwalls");
			
		}
		
		
		if ("lemonade".equalsIgnoreCase(type)){
			steps = getIntValue (el, "steps");
	
		}
		players = getIntValue (el, "players");
		NodeList podList = file.getElementsByTagName("pods");
		Element podElement = (Element)podList.item(0);
		pCoordinates = getTextValue (podElement, "coordinate");
		
		NodeList cList = file.getElementsByTagName("collision");
		Element cElement = (Element)cList.item(0);
		collision = getIntValue (cElement, "penalty");
		
		NodeList CoinList = file.getElementsByTagName("coins");
		Element CoinElement = (Element)CoinList.item(0);
	
		cCoordinates = getTextValue (CoinElement, "coordinate");
		coinValue = getIntValue (CoinElement, "value");
		
		
		
		
		
		
		//NodeList 
		NodeList expList = docEle.getElementsByTagName("experiment");
		Element expElement = (Element)expList.item(0);
		
		iterations = getIntValue (expElement, "iterations");
		
		filename = getTextValue (expElement, "resultfilename");

	
		
		
	}

	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}
	
	private static int getIntValue(Element ele, String tagName) {
		
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
	
	// return methods for all the private fields
	public static String getType (){
		return type;
	}
	public static int getRows (){
		return rows;
	}
	public static int getColumn (){
		return columns;
	}
	public static String getPuds (){
		return puds;
	}
	public static  String getWalls (){
		return walls;
	}
	public static String getStocWalls (){
		return stocwalls;
	}
	public static int getsteps(){
		return steps;
	}
	public static int getIterations (){
		return iterations;
	}
	public static String getFilename (){
		return filename;
	}
	public static int getplayers(){
		return players;
	}
	public static int getcoins(){
		return coinValue;
	}
	public static String getcCoordinate(){
		return cCoordinates;
	}
	public static String getpCoordinate(){
		return pCoordinates;
	}
	
	
}


