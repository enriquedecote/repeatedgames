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
import java.awt.List;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import org.xml.sax.SAXParseException;


public class xmlread {
	String Filename;
		
	//public static String[] ReadFile (String FileName) {
	public static void main(String[] args) {
		int TotalActions;
		String[] Actions = new String [10];
		
		try{
				
		DocumentBuilderFactory DocBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder DocBuilder = DocBuilderFactory.newDocumentBuilder();
		Document Doc = DocBuilder.parse(new File ("test.xml"));
		
		Doc.getDocumentElement().normalize();
		NodeList nodeList = Doc.getElementsByTagName("environment");
		Node fstNode = nodeList.item(0);
		short ret = fstNode.getNodeType();
		System.out.println("Now " +ret);
		NodeList List = Doc.getDocumentElement().getChildNodes();
		
		
		///////////////////String man = Doc.getDocumentElement().getTextContent();
		Node man = Doc.getFirstChild();
		Node dan = Doc.getLastChild();
		//Node tagname = List.item(1);
		System.out.println("Now" +dan);
		
		NodeList Main = Doc.getDocumentElement().getElementsByTagName("environment");
		Node Next = Main.item(0);
		NodeList Final = Next.getChildNodes();
		//String main = Next.getNodeValue();
		String fin = Final.item(0).getNodeName();
		
		NodeList dwn = Doc.getElementsByTagName("test");
		
		
		
		Element fix = Doc.getElementById("environment");
		
		
		
		
		//String Dwn = Doc.getDocumentElement().getChildNodes();
		String ban = dwn.item(0).getNodeName();
		int SAn = dwn.getLength();
		//String  ban = ((String)dwn.item(1).getNodeValue());
		System.out.println(fix);
	 	if (ban == "environment"){
	 		System.out.println("Yes");
	 	}
	 	else
	 	{
	 		System.out.println("No");
	 	}
		
		//String[] Actions = new String [TotalActions];
		
		NodeList Type = Doc.getElementsByTagName("type");
		Node TypeNode = Type.item(0);
		Element TypeElement = (Element)TypeNode;
		NodeList TypeList = TypeElement.getChildNodes();
		String type = ((String)TypeList.item(0).getNodeValue().trim());
		System.out.println(type);
		Actions[0]= type;
		
		NodeList Row = Doc.getElementsByTagName("rows");
		Node RowNode = Row.item(0);
		Element RowElement = (Element)RowNode;
		NodeList RowList = RowElement.getChildNodes();
		String row = ((String)RowList.item(0).getNodeValue().trim());
		System.out.println(row);
		Actions[1]= row;
		
		NodeList Column = Doc.getElementsByTagName("columns");
		Node ColumnNode = Column.item(0);
		Element ColumnElement = (Element)ColumnNode;
		NodeList ColumnList = ColumnElement.getChildNodes();
		String column = ((String)ColumnList.item(0).getNodeValue().trim());
		Actions[2]= column;
        System.out.println(column); 
		
        NodeList ListofActions = Doc.getElementsByTagName("action");
		TotalActions = ListofActions.getLength();
		
		
		for(int i=0; i<TotalActions; i++){
			
			for(int j=3; j<6; j++){
		
			Node ActionNode = ListofActions.item(i);
			if (ActionNode.getNodeType()==Node.ELEMENT_NODE){
				
				Element ActionElement = (Element)ActionNode;
				
				

                NodeList ActionList = ActionElement.getChildNodes();
                Actions[j]= ((String)ActionList.item(0).getNodeValue().trim());
                i++;
                System.out.println(":"+Actions[j]); 
			}           
			
		}
		}
		}catch (SAXParseException err) {
	        System.out.println ("** Parsing error" + ", line " 
	                + err.getLineNumber () + ", uri " + err.getSystemId ());
	           System.out.println(" " + err.getMessage ());

	           }catch (Throwable t) {
	           t.printStackTrace ();
	           }
	          
	          System.out.println("uff");
	          
	          for (int r = 0; r<6; r++){
	        	  System.out.println(Actions[r]);
	          }
		
		//return Actions;
		

	}
	
	

}
