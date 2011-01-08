/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author MalteV
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universit�t Darmstadt
 *   All rights reserved.
 * 
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 * 
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the Software Engineering Group or Technische 
 *     Universit�t Darmstadt nor the names of its contributors may be used to 
 *     endorse or promote products derived from this software without specific 
 *     prior written permission.
 * 
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *   AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *   IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 *   ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 *   LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *   CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *   SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *   INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 *   CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 *   ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package de.tud.cs.st.vespucci.diagram.dnd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IResource;
/**
 * A Class witch provide static tools for supporting of DnD
 * @author MalteV
 *
 */
public class StaticToolsForDnD {
	
	
	/**
	 * creates a new Query from the data of a drop event
	 * @param map data of the drop event
	 * @return new query
	 */
	public static String createQueryForAMapOfIResource(Map<String,IResource> map){
		return createQueryForAMapOfIResource(map, "");
	}
	
	/**
	 *  creates a new Query from the Data of a drop event
	 *  under consideration of the old Query
	 * @param map data of the drop event
	 * @param oldQuery oldQuery of the model element
	 * @return new query
	 */
	public static String createQueryForAMapOfIResource(Map<String,IResource> map, String oldQuery){
		
		if(oldQuery == null)
			oldQuery = "";
		if(oldQuery.equals("empty") && map.size()>0)
			oldQuery = "";
		if(oldQuery.length() > 0)
			oldQuery += ",";
		
		String res = oldQuery;
		//find all Java files 
		List<String> classes = getJavasSourceCodeFiels(map);
		//find all Packages
		List<String> pack = getJavaPackages(map);
		//extends the old Query
		for(String s : classes){
			res = res + s +",";
		}
		for(String s : pack){
			res = res + s + ",";
		}
		res = res.substring(0, res.length()-1);
		return res;
	}
	/**
	 * Creates a List that contains for all Java Fils in map 
	 * an entry:
	 * class('PACKAGE OF JAVA FILE','JAVA FILE NAME')
	 * @param map 
	 * @return
	 */
	private static List<String> getJavasSourceCodeFiels(Map<String,IResource> map){
		List<String> res = new ArrayList<String>();
		String pack = null;
		String fileName = null;
		for(String key : map.keySet()){
			IResource entry = map.get(key);
			if(entry.getType() == IResource.FILE){
				if(entry.getFileExtension().equals("java")){
					String[] ordner = entry.getProjectRelativePath().toString().split("/");
					//ordner[0] == src
					pack = null;
					fileName = null;
					for(int i = 1; i < ordner.length -1 ;i++){
						if(pack == null){
							pack = ordner[i];
						}else{
							pack = pack + "." + ordner[i];
						}
					}
					fileName = ordner[ordner.length-1].substring(0, ordner[ordner.length-1].length()-5);
					res.add("class('" + pack + "','" + fileName +"')");
				}
				
			}
					//[class('de.tud.cs.se.flashcards.model', 'AllTests')
					//,class('de.tud.cs.se.flashcards.model','FlashcardSeriesFilterTest'),
					//class('de.tud.cs.se.flashcards.model','FlashcardTest'
		}
		
		return res;
	}
	/**
	 * Creates a List that contains for all Java Packages in map 
	 * as an entry:
	 * package('PACKAGE OF JAVA FILE')
	 * @param map
	 * @return
	 */
	private static List<String> getJavaPackages(Map<String,IResource> map){
		List<String> res = new ArrayList<String>();
		for(String key : map.keySet()){
			IResource entry = map.get(key);
			if(entry.getType() == IResource.FOLDER){
				String[] ordner = entry.getProjectRelativePath().toString().split("/");
				String pack = null;
				for(int i = 1; i < ordner.length;i++){
					if(pack == null){
						pack = ordner[i];
					}else{
						pack = pack + "." + ordner[i];
					}
				}
				res.add("package('" + pack + "')");
			}
		}
		return res;
	}
}
