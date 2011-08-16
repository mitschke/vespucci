/*
 *  License (BSD Style License):
 *   Copyright (c) 2010
 *   Author Patrick Jahnke
 *   Software Engineering
 *   Department of Computer Science
 *   Technische Universitiät Darmstadt
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
 *     Universität Darmstadt nor the names of its contributors may be used to
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
// TODO move to the correct package: de.tud.cs.st.vespucci.diagram.converter.DiagramConverter
// FIXME move all test code to a source folder called "test" (we do not want to deliver this code as part of the plugin!)
package Unittests;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import de.tud.cs.st.vespucci.diagram.converter.DiagramConverter;


public class DiagramConverterTests {

	private DiagramConverter dc = null;

	public DiagramConverterTests()
	{
		dc = new DiagramConverter();
	}

	@Test
	public void CheckStandardDiagramFile() {
		compareFiles("DiagramTest.sad", "DiagramTest.sad.pl");
	}

	@Test
	public void CheckCollapsedDiagramFile() {
		compareFiles("RedLine.sad", "RedLine.sad.pl");
	}

	@Test
	public void AbstractEnsembleDiagramFile() {
		compareFiles("AbstractEnsemble.sad", "AbstractEnsemble.sad.pl");
	}

	/**
	 * Let the DocumentConverter create a *.pl File and compare it with a given *.pl file.
	 * @param sadFile Diagram File.
	 * @param plFile Prolog File to check.
	 */
	private void compareFiles (String sadFile, String plFile)
	{
		try {
			// create temp files and put the content of the given Resources into it.
			File tempSadFile = File.createTempFile("DiagramFile", ".sad");
			File tempPlFile = File.createTempFile("PrologFileCompare", ".p");
			InputStream sadInputStream = this.getClass().getResourceAsStream(sadFile);
			copy (sadInputStream, tempSadFile);
			InputStream plInputStream = this.getClass().getResourceAsStream(plFile);
			copy (plInputStream, tempPlFile);

			// change the filename of the sad File in the prolog file
			tempPlFile = changeDiagramFileName(tempPlFile, sadFile, tempSadFile.getName());

			assertTrue(dc.isDiagramFile(tempSadFile));
			dc.convertDiagramToProlog(tempSadFile);
			File generatedPlFile = new File(tempSadFile.getAbsoluteFile()+".pl");
			generatedPlFile = deleteComment(generatedPlFile);

			assertTrue(generatedPlFile.exists());
			assertTrue(compareFileContent(tempPlFile, generatedPlFile));
			assertTrue(generatedPlFile.delete());
			assertTrue(tempPlFile.delete());
			assertTrue(tempSadFile.delete());

		}  catch (Exception e) {
			Assert.fail(e.toString());
		}

	}

	/**
	 * Change the filename of the sad File in the prolog file and delete also the comments in the prolog file.
	 */
	private File changeDiagramFileName(File plFile, String search, String replace) throws IOException {
		// delete also the comments form pl File
		boolean comment = false;
		String buf = null;
		String writeBack = null ;

		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(plFile)), 1000);

		File generatedPlFile = new File(plFile.getAbsoluteFile()+"l");
		generatedPlFile.createNewFile();
		FileOutputStream Fileoutput = new FileOutputStream(generatedPlFile) ;
		DataOutput outFile = new DataOutputStream(Fileoutput) ;

		while (true)
		{
			buf = input.readLine();
			if (buf == null)
				break;
			if ((buf.indexOf("%------")>=0) &&(comment == false))
			{
				comment=true;
				continue;
			}
			else if ((buf.indexOf("%------")>=0) &&(comment == true))
			{
				comment=false;
				continue;
			}

			if (comment==false)
			{
				writeBack = buf.replaceAll(search, replace)+"\n";
				outFile.writeBytes(writeBack) ;
			}
		}
		input.close() ;
		Fileoutput.close();
		plFile.delete();

		return generatedPlFile;
	}

	// TODO public or private
	public File deleteComment(File plFile) throws IOException
	{
		boolean comment = false;

		String buf = null;

		BufferedReader input = new BufferedReader(
				new InputStreamReader(
						new FileInputStream(plFile)), 1000);

		File withoutComments = new File(plFile.getAbsoluteFile()+"2");
		withoutComments.createNewFile();
		FileOutputStream Fileoutput = new FileOutputStream(withoutComments) ;
		DataOutput outFile = new DataOutputStream(Fileoutput) ;

		while (true)
		{
			buf = input.readLine();
			if (buf == null)
				break;
			if ((buf.indexOf("%------")>=0) &&(comment == false))
			{
				comment=true;
				continue;
			}
			else if ((buf.indexOf("%------")>=0) &&(comment == true))
			{
				comment=false;
				continue;
			}

			if (comment==false)
				outFile.writeBytes(buf+"\n") ;
		}
		input.close() ;
		Fileoutput.close();
		plFile.delete();
		withoutComments.renameTo(plFile);

		return new File(plFile.getAbsolutePath());
	}

	private boolean compareFileContent(File one, File two) throws IOException
	{
		String buf1 = null;
		String buf2 = null;

		BufferedReader input1 = new BufferedReader(new InputStreamReader(new FileInputStream(one)), 1000);
		BufferedReader input2 = new BufferedReader(new InputStreamReader(new FileInputStream(two)), 1000);

		while (true)
		{
			buf1 = input1.readLine();
			buf2 = input2.readLine();
			if ((buf1 == null) && (buf2==null))
				break;
			else if ((buf1 == null) || (buf2==null))
				return false;

			if (!buf1.equals(buf2))
				return false;
		}
		input1.close();
		input2.close();

		return true;

	}

	private void copy(InputStream from_inputStream, File to){
		try
		{
		    OutputStream out=new FileOutputStream(to);
		    byte buf[]=new byte[1024];
		    int len;
		    while((len=from_inputStream.read(buf))>0)
		    	out.write(buf,0,len);
		    out.close();
		    from_inputStream.close();
		}
		catch (IOException e){}
	}
}
