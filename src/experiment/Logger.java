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
package experiment;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import reward.Reward;

import agent.Agent;

import util.Action;
import util.State_JointAction;
import util.StateDomain_JointAction;
import util.State;

/**
 * @author Enrique Munoz de Cote
 * 
 */
public class Logger {

	private int timeStep;
	private File file;
	private File tempFile;
	StringBuffer content;
	StateDomain_JointAction sDomain;
	private int[] stateCount;
	private boolean log; 
	
	public Logger(StateDomain_JointAction s, boolean loggerOn){
		log = loggerOn;
		sDomain = s;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmm");
        Date date = new Date();
        File tmpPath = new File(System.getProperty("user.home")+"/experiments/repeatedgames/tmp/");
        String tempPath = System.getProperty("user.home")+"/experiments/repeatedgames/tmp/tmp.txt";
        String logPath = System.getProperty("user.home")+"/experiments/repeatedgames/tmp/log.txt";

        if(!tmpPath.exists()){
        	tmpPath.mkdirs();
        }
        file = new File(logPath);
		tempFile = new File(tempPath);
		getFileStateExperience(tempFile);
		content = new StringBuffer();
	}
	
	private void getFileStateExperience(File aFile){

		
	    if(aFile.exists()){
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(aFile));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        
	        /* for the lemonade game
	        int i=0;
	        int k=0;
	        while (( line = input.readLine()) != null){
	        	stateCount[i] += Integer.parseInt(line);
	        	k += stateCount[i];
	        	i++;
	        }
	        assert(k == 100);
	        */
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    }

	}
	
	public void flushtoFile() throws FileNotFoundException, IOException {
		String ret =	System.getProperty("line.separator");
		int i = 0;
		content.setLength(0);
		for (State_JointAction state : sDomain.getStateSet()) {
			content.append(state.toString() + ",");
			content.append("\t" + stateCount[i] + ret);
			i++;
		}
	}
	
	private void flushtoTempFile() throws FileNotFoundException, IOException {
		String ret =	System.getProperty("line.separator");
		for (Integer c : stateCount) 
			content.append(c.toString()+ret);
		  Writer output = new BufferedWriter(new FileWriter(tempFile));
		    try {
		      //FileWriter always assumes default encoding is OK!
		      output.write( content.toString() );
		    }
		    finally {
		      output.close();
		    }
	}
	
	public void flush(){
	    if(log){
		    try {
				flushtoTempFile();
				flushtoFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}


}
