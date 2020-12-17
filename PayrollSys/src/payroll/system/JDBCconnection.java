package payroll.system;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * Class that handles the JDBC calls with a class level private variable of type
 * Connection.
 * 
 * @author ClaudiaRivera
 *
 */
public class JDBCconnection {

	/**
	 * private variable type Connection to make the connection
	 */
	private Connection conn;

	public JDBCconnection(String user, String password) { // inside the constructor call the connection

		setConnection(user, password);

	}

	private void setConnection(String username, String password) {

		try {
			// sets connection to oracle using OracleDriver
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error – no Oracle Driver found");
		}
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", username, password);

			System.out.println("connection");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Username or password are incorrect, please try again", "Failure",
					JOptionPane.ERROR_MESSAGE);

			System.out.println("user is enable to connect");
		}

	}

	public String checkUser(String username) {

		username = username.toUpperCase();

		String sql = "{?=call CHECK_USER_FUNC(?)}";
		String p = "";
		try {

			CallableStatement cstmt = conn.prepareCall(sql);

			cstmt.registerOutParameter(1, java.sql.Types.VARCHAR);
			cstmt.setString(2, username);
			cstmt.execute();
			System.out.println("RETURN STATUS: " + cstmt.getString(1));
			p = cstmt.getString(1);
			cstmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return p;
	}

	public void perform() {

		String sql = "{call ZERO_OUT_P()}";

		try {

			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.execute();
			System.out.println("RETURN STATUS: ");

			cstmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void createAlias(String dir, String alias) {
		// String path= dir.replace('/', '\\');
		String sql = "CREATE OR REPLACE DIRECTORY " + alias + " AS " + "'" + dir + "'";
		System.out.println(sql);
		try {

			Statement stmt = conn.createStatement();

			stmt.executeUpdate(sql);

			stmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void export(String dirAlias, String filename) {

		String sql = "{export_File_p()}";

		try {

			CallableStatement cstmt = conn.prepareCall("{call export_file_p(?, ?)} ");

			cstmt.setString(1, dirAlias);
			cstmt.setString(2, filename);
			cstmt.execute();

			cstmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
