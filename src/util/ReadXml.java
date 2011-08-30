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
/**
 * 
 */
package util;

import environment.Environment;
import experiment.ExperimentLogger;

import java.io.IOException;
import java.util.Vector;
// XML DOMparser
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
//SpringBeans
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import reward.Reward;

import agent.Agent;

/**
 * @author enrique
 *
 */
public class ReadXml {

	/**
	 * 
	 */
	Resource resource;
	BeanFactory factory;
	String xmlFile;
	Document dom;
	Element docEle;
	
	public ReadXml(String xmlFile, String beanFile) {
		this.xmlFile = xmlFile;
		
		//BeanFactory
		resource =  new FileSystemResource (beanFile);
		factory = new XmlBeanFactory(resource);
		
		//DOMparser factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			//parse using builder to get DOM representation of the XML file
			dom = db.parse(xmlFile);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(SAXException se) {
			se.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		//get the root elememt
		docEle = dom.getDocumentElement();

	}
	
	private void parseDocument(){
		
		//get a nodelist of <environment> elements
		NodeList nl = docEle.getElementsByTagName("Environment");
		if(nl != null && nl.getLength() > 0) {
			for(int i = 0 ; i < nl.getLength();i++) {
				
				//get the employee element
				Element el = (Element)nl.item(i);
				
				//get the Employee object
				//Environment env = getEmployee(el);
				
				//add it to list
				//myEmpls.add(e);
			}
		}
	}
	
	/**
	 * creates the appropriate environment according to the XML definition
	 * @return the created environment
	 */
	public Environment constructEnvironment(){
		NodeList nodes = docEle.getElementsByTagName("Type");
		Element e = (Element)nodes.item(0);
		String name = e.getAttribute("environment");
		System.out.println("Environment: " + name);
		Environment env = (Environment)factory.getBean(name);
		env.Init(this);
		return env;
	}
	
	/**
	 * creates the appropriate reward function according to the XML definition
	 * @return the created reward object
	 */
	public Reward constructReward(ExperimentLogger log){
		NodeList nodes = docEle.getElementsByTagName("Type");
		Element e = (Element)nodes.item(0);
		String name = e.getAttribute("rewardType");
		System.out.println("\nReward type: " + name);
		log.recordConfig("Reward Type: " + name);
		Reward r = (Reward)factory.getBean(name);
		name = e.getAttribute("game");
		System.out.println("Game: " + name);
		log.recordConfig("Game: " + name);
		r.Init(name);
		r.Init(this);
		return r;
	}

	/**
	 * searches through the XML file to gather agent types and creates them
	 * @param rewards 
	 * @return the type of agent_i
	 */
	public Vector<Agent> constructAgents(Reward rewards){
		NodeList players = docEle.getElementsByTagName("Player");
		//there is only one env definition so there is only one in the list
		//NodeList agentTypeList = nodes.item(0).getChildNodes();
		System.out.println("No. of agents:" + players.getLength());
		Vector<Agent> agents = new Vector<Agent>(players.getLength());
		
		for (int i = 0; i < players.getLength(); i++) {
			NodeList list = players.item(i).getChildNodes();
			Element e = (Element) list.item(1);
			String name = e.getAttribute("name");
			System.out.println(name);
			Agent agent = (Agent)factory.getBean(name);
			agent.init(rewards);
			agent.init(e, i);
			agents.add(agent);
		}

		return agents;
	}
	
	public String getExperimentIterations(){
		NodeList nodes = docEle.getElementsByTagName("Experiment");
		Element e = (Element) nodes.item(0);
		return e.getAttribute("iterations");
	}
	
	public String getExperimentRuns(){
		NodeList nodes = docEle.getElementsByTagName("Experiment");
		Element e = (Element) nodes.item(0);
		return e.getAttribute("runs");
	}
	
	public static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}
		return textVal;
	}
	
	public static int getIntValue(Element ele, String tagName) {
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
	public static float getFloatValue(Element ele, String tagName) {
		return Float.parseFloat(getTextValue(ele,tagName));
	}
	
	public NodeList getElementsByTagName(String s){
		return docEle.getElementsByTagName(s);
	}
	
	public String getTagAttribute(String tag, String attributeName){
		NodeList nodes = getElementsByTagName(tag);
		Element e = (Element)nodes.item(0);
		return e.getAttribute(attributeName);
	}

}
