package de.uks.se.scoreproject.dice.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import de.uks.se.scoreproject.dice.loc.LocCounter;

public class LOCTest {

	@Test
	public void testLocCounting(){
		createTestFiles();
		assertEquals("Ergebnis sollte 4 sein",4, LocCounter.CountAllLines("test"));
	}

	private void createTestFiles() {
		File file = new File("test/src/");
		
		File file2 = new File("test/src/testsourcefile.java");
		if(!file.exists() && !file2.exists()){
			FileWriter f;
			file.mkdirs();
			try {
				f = new FileWriter("test/src/testsourcefile.java");
				BufferedWriter bf = new BufferedWriter(f);
				bf.write("das ist text\n"
						+ "das ist nochmal text\n"+
						" Das ist eine LOC; mit nochmehr LOC\n"+
						"das ist eine LOC mit kommentar; // kommentar\n"
						+ "//das ist auskommentierter LOC\n"+
						"/*das auch;*/\n"
						+ "code code code;/* kommentarkommentar\n"+
						" code code code\n"
						+ "code code code;*/code code code;\n" );
				bf.close();
				f.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("test failed. couldnt create test files. Cause: "+e.getCause());
			}
			
		}
		
	}
}
