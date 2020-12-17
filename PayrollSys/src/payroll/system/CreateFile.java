package payroll.system;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
/**
 * 
 * @author Claudia Rivera
 *
 */
public class CreateFile {

	private String fileName; // txt file
	private String ctlFile; // ctl file
	private BufferedWriter stream;

	public CreateFile(String fileName, String ctlFile) {
		this.fileName = fileName;
		this.ctlFile = ctlFile;
	}

	public void loadCTLFile() {

		try {
			File file = new File(ctlFile);
			FileWriter f = new FileWriter(file);
			stream = new BufferedWriter(f);
			String path = fileName.replace('/', '\\');
			System.out.println("Path:" + path);
			stream.write("LOAD DATA");
			stream.newLine();
			stream.write("INFILE '" + path + "' ");
			stream.newLine();
			stream.write("REPLACE");
			stream.newLine();
			stream.write("INTO TABLE payroll_load");
			stream.newLine();
			stream.write("FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '\"'");
			stream.newLine();
			stream.write("TRAILING NULLCOLS");
			stream.newLine();
			stream.write("(payroll_date DATE \"Month dd, yyyy\",");
			stream.newLine();
			stream.write("employee_id,");
			stream.newLine();
			stream.write("amount,");
			stream.newLine();
			stream.write("status)");

			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int setFile(String username, String password) {
		System.out.println(username + " " + password);
		String path = ctlFile.replace('/', '\\');
		String cmd = "sqlldr userid=" + username + "/" + password + " control=" + path; // + "
																						// log=C:\\\\CPRG307\\payroll.log
																						// ";
		System.out.println(cmd);

		int exitValue = 10;
		try {
			System.out.println("Test 1");
			Runtime rt = Runtime.getRuntime();
			
			System.out.println("Test 2");
			Process proc = rt.exec(cmd);
			
			System.out.println("Test 3");
			exitValue = proc.waitFor();
			
			System.out.println("Test 4");
			System.out.println("executed sqll");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exitValue;

	}

}
