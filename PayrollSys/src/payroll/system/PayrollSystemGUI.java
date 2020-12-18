package payroll.system;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * GUI to test the operations
 * @author Claudia Rivera
 *
 */
public class PayrollSystemGUI extends JFrame {

	private JPanel contentPane;
	private String username;
	private String password;
	private static JDBCconnection conn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PayrollSystemGUI frame = new PayrollSystemGUI();

					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the frame.
	 */
	
	JButton btnCheck;
	JButton btnProcess;
	JButton btnPerform;
	JButton btnExport;

	public PayrollSystemGUI() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Payroll System");
		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		// Buttons and actions listeners
		 btnCheck = new JButton("Check");
		btnCheck.setBounds(272, 33, 112, 23);
		contentPane.add(btnCheck);

		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				username = JOptionPane.showInputDialog("Enter Username");
				password = JOptionPane.showInputDialog("Enter Password");
				if (!username.equals("") && !password.equals("")) {
					conn = new JDBCconnection(username, password);

					String permission = conn.checkUser(username);
					if (permission.equalsIgnoreCase("Y")) {

						JOptionPane.showMessageDialog(null, "User has permissions");
					} else
						JOptionPane.showMessageDialog(null, "User does not have permission", "Failure",
								JOptionPane.ERROR_MESSAGE);

					btnCheck.setEnabled(false);
					btnProcess.setEnabled(true);
				}

				else {
					JOptionPane.showMessageDialog(null, "Please enter username and passsword", "Failure",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		 btnProcess = new JButton("Process");
		btnProcess.setBounds(272, 77, 112, 23);
		contentPane.add(btnProcess);
		btnProcess.setEnabled(false);
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String fileName = JOptionPane.showInputDialog("Enter location and name of your txt file ");
				File inputFile= new File(fileName);
				String ctlFile = JOptionPane.showInputDialog("Enter location and name of your control file ");
				
				if (inputFile.exists()) {
				CreateFile cF = new CreateFile(fileName, ctlFile);
				cF.setFile(username, password);
				cF.loadCTLFile();
				JOptionPane.showMessageDialog(null, "Text file has been loaded");
				
				btnProcess.setEnabled(false);
				btnPerform.setEnabled(true);
				}
			}
		});

		btnPerform = new JButton("Perform");
		btnPerform.setBounds(272, 125, 112, 23);
		contentPane.add(btnPerform);
		btnPerform.setEnabled(false);
		btnPerform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				conn.perform();
				btnPerform.setEnabled(false);
				btnExport.setEnabled(true);
				JOptionPane.showMessageDialog(null, "End of month has been performed");
			}
		});

		btnExport = new JButton("Export");
		btnExport.setBounds(272, 169, 112, 23);
		btnExport.setEnabled(false);
		contentPane.add(btnExport);

		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String directoryPath = JOptionPane.showInputDialog("Enter directory path to the export file will go to ");
				File directory= new File (directoryPath);
				if (directory.exists()) {
				String filename = JOptionPane.showInputDialog("Enter file name ");
				String alias = JOptionPane.showInputDialog("Enter alias for directory path ");
				conn.createAlias(directoryPath, alias);
				conn.export(alias, filename);
				JOptionPane.showMessageDialog(null, "Your database has been Export");
				btnExport.setEnabled(false);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "Directory does not exits, try again!", "Failure",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});

		JButton btnClose = new JButton("Close");
		btnClose.setBounds(335, 227, 89, 23);
		contentPane.add(btnClose);
		// Close window
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
               if(conn != null)
				conn.closeConnection();
				System.exit(0);

			}
		});

		// Labels
		JLabel lblStepDatabase = new JLabel("Step 1: Database Login and Check");
		lblStepDatabase.setBounds(10, 33, 202, 14);
		contentPane.add(lblStepDatabase);

		JLabel lblStepProcess = new JLabel("Step 2: Process delimitted text file ");
		lblStepProcess.setBounds(10, 81, 202, 14);
		contentPane.add(lblStepProcess);

		JLabel lblStepPerform = new JLabel("Step 3: Perform Month End");
		lblStepPerform.setBounds(10, 129, 202, 14);
		contentPane.add(lblStepPerform);

		JLabel lblStepExport = new JLabel("Step 4: Export Data");
		lblStepExport.setBounds(10, 173, 202, 14);
		contentPane.add(lblStepExport);

	}

}
