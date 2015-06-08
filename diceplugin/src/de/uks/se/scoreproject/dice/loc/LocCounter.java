package de.uks.se.scoreproject.dice.loc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LocCounter {

	/**
	 * 
	 * @param rootProjectPath Full String path to project root directory
	 * @return the count of the lines that are probably code
	 * @return -1 if its not a directory
	 */
	public static int CountAllLines(String rootProjectPath){
		int totalLOC = 0;
		File f = new File(rootProjectPath);
		if(f.exists() && f.isDirectory()){
			File [] dir = f.listFiles();
			for(File subfiles : dir){
				if(subfiles.getName().equals("src")){
					totalLOC = countFolderLines(subfiles.getAbsolutePath());
				}
			}
		}
		return totalLOC;
	}
	
	/**
	 * 
	 * @param folderPath
	 * @returns number of loc in given folder
	 */
	public static int countFolderLines(String folderPath){
		int folderLOC=0;
		File root = new File(folderPath);
		if(root.exists() && root.isDirectory()){
			File [] subdirs = root.listFiles();
			for(File sub : subdirs){
				if(sub.isDirectory()){
					folderLOC += countFolderLines(sub.getAbsolutePath());
				}else if(sub.isFile()){
					folderLOC += countFileLines(sub.getAbsolutePath());
				}
			}
		}
		return folderLOC;
	}
	
	/**
	 * 
	 * @param filePath
	 * @returns  number of LOC in given File
	 */
	public static int countFileLines (String filePath){
		int loc = 0;
		try {
			FileReader read = new FileReader(filePath);
			BufferedReader in = new BufferedReader(read);
			String readLine = in.readLine(); 
			while(readLine != null ){
				if(checkLine(readLine)){
					loc++;
				}
				readLine = in.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		return loc;
	}
	
	/**
	 * 
	 * @param line, the contents of the line that has to be checked 
	 * @return true if its a valid Line of code
	 * @return false otherwise
	 */
	private static boolean status_comment=false;
	private static boolean checkLine(String line){
		line = line.trim();
		if(line == null || line.equals("") ){
			return false;
		}else{// weitere checks?
			String realContents= line;
			if(!status_comment && realContents.contains("//") && !isSurroundedby(realContents, "//", '"')){
				realContents = line.split("//")[0];
			}
			if(!status_comment && realContents.contains("/*") && !isSurroundedby(realContents, "/*", '"') &&
					realContents.contains("*/") && !isSurroundedby(realContents, "*/", '"')){ // start und ende
				// auskommentierten teilbereich rauslöschen
				//TODO
				
			}
			if(!status_comment && realContents.contains("/*") && !isSurroundedby(realContents, "/*", '"'))
			{
				status_comment = true;
				// auskommentierten bereich rauslöschen
				//TODO
			}
			
			if(status_comment && realContents.contains("*/") && !isSurroundedby(realContents, "*/", '"')){
				status_comment = false;
				// auskommentierten bereich rauslöschen
				//TODO
			}
			// checke ob ein befehl in der zeile ist also ob vor ; etwas steht oder danach
			// dann ist es ein LOC
			
			return true;
		}
	}
	/*
	 * überlegung
	 * auskommentierte zeile zählt nicht
	 * auskommentierter bereich muss ausradiert werden
	 * auskommentierungszeichen in anführungszeichen, also strings ignorieren
	 * zeile die etwas nicht leeres vor dem ; hat ist eine loc
	 * evtl loc = lines of comments zählen?
	 */

	
	//bsp: System.out.println("abc") /**/ System.out.println("def");
	private static boolean isSurroundedby(String line, String what, char wrapper) {
		int rootPos = line.indexOf(what);
		int leftPos = rootPos;
		int rightPos = rootPos;
		boolean done = false;
		int leftWrap = 0;
		int rightWrap = 0;
		while(!done){
			if(leftPos == 0 && rightPos == line.length()){
				done = true;
			}
			
			if(line.charAt(leftPos) == wrapper){
				leftWrap++;
			}
			if(line.charAt(rightPos) == wrapper){
				rightWrap++;
			}
			
			if(leftPos != 0){
				leftPos --;
			}
			if(rightPos != line.length()){
				rightPos ++;	
			}
			
		}
		return (leftWrap % 2 == 1 && rightWrap % 2 == 1);
		
	}
}
