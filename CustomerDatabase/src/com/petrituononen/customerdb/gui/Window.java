package com.petrituononen.customerdb.gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.petrituononen.customerdb.CustomerListManager;
import com.petrituononen.customerdb.jaxb.Address;
import com.petrituononen.customerdb.jaxb.Customer;
import com.petrituononen.customerdb.jaxb.Email;
import com.petrituononen.customerdb.jaxb.Phone;


/**
 * Swing GUI for Customer database.
 * 
 * @author Petri Tuononen
 *
 */
public class Window {
	
	private JFrame frmCustomerDatabase;
	private JTextField txtName;
	private JTextField txtPhoneWork;
	private JTextField txtPhoneMobile;
	private JTextField txtEmail;
	private JTextField txtStreet1;
	private JTextField txtStreet2;
	private JTextField txtPostalCode;
	private JTextField txtTown;
	private JTextArea txtNotes;
	private final String PHONE_WORK = "WORK_PHONE";
	private final String PHONE_MOBILE = "MOBILE_PHONE";
	private final String EMAIL_WORK = "WORK_EMAIL";
	private final String ADDRESS_TYPE = "VISITING_ADDRESS";
	private int browseIdx;
	private Customer currentCustomer;
	private static CustomerListManager clm;
	private String xmlName = "Customers.xml"; //default
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clm =  new CustomerListManager();
					Window window = new Window();
					window.frmCustomerDatabase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
		initializeData();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCustomerDatabase = new JFrame();
		frmCustomerDatabase.setTitle("Customer database");
		frmCustomerDatabase.setSize(411, 485);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frmCustomerDatabase.getSize().width;
		int h = frmCustomerDatabase.getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		 
		frmCustomerDatabase.setLocation(x, y);
		
		frmCustomerDatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCustomerDatabase.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 395, 21);
		frmCustomerDatabase.getContentPane().add(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JMenuItem mntmSelectXmlFile = new JMenuItem("Select XML file");
		mntmSelectXmlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				xmlName = setXmlLocation();
				if(!xmlName.equalsIgnoreCase("nullnull")) {
					initializeData();
				}
			}
		});
		mnFile.add(mntmSelectXmlFile);
		mnFile.add(mntmExit);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(27, 40, 46, 14);
		frmCustomerDatabase.getContentPane().add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(122, 37, 236, 20);
		frmCustomerDatabase.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JLabel lblAddress1 = new JLabel("Address 1");
		lblAddress1.setBounds(27, 68, 75, 14);
		frmCustomerDatabase.getContentPane().add(lblAddress1);
		
		txtStreet1 = new JTextField();
		txtStreet1.setBounds(122, 65, 236, 20);
		frmCustomerDatabase.getContentPane().add(txtStreet1);
		txtStreet1.setColumns(10);
		
		JLabel lblAddress2 = new JLabel("Address 2");
		lblAddress2.setBounds(27, 93, 75, 14);
		frmCustomerDatabase.getContentPane().add(lblAddress2);
		
		txtStreet2 = new JTextField();
		txtStreet2.setBounds(122, 90, 236, 20);
		frmCustomerDatabase.getContentPane().add(txtStreet2);
		txtStreet2.setColumns(10);
		
		JLabel lblPostalCode = new JLabel("Postal code");
		lblPostalCode.setBounds(27, 121, 86, 14);
		frmCustomerDatabase.getContentPane().add(lblPostalCode);
		
		txtPostalCode = new JTextField();
		txtPostalCode.setBounds(122, 118, 87, 20);
		frmCustomerDatabase.getContentPane().add(txtPostalCode);
		txtPostalCode.setColumns(10);
		
		JLabel lblTown = new JLabel("Town");
		lblTown.setBounds(223, 121, 46, 14);
		frmCustomerDatabase.getContentPane().add(lblTown);
		
		txtTown = new JTextField();
		txtTown.setBounds(258, 118, 100, 20);
		frmCustomerDatabase.getContentPane().add(txtTown);
		txtTown.setColumns(10);
		
		JLabel lblPhoneWork = new JLabel("Phone (work)");
		lblPhoneWork.setBounds(27, 146, 86, 14);
		frmCustomerDatabase.getContentPane().add(lblPhoneWork);
		
		txtPhoneWork = new JTextField();
		txtPhoneWork.setBounds(122, 143, 236, 20);
		frmCustomerDatabase.getContentPane().add(txtPhoneWork);
		txtPhoneWork.setColumns(10);
		
		JLabel lblPhoneMobile = new JLabel("Phone (mobile)");
		lblPhoneMobile.setBounds(27, 174, 86, 14);
		frmCustomerDatabase.getContentPane().add(lblPhoneMobile);
		
		txtPhoneMobile = new JTextField();
		txtPhoneMobile.setBounds(122, 171, 236, 20);
		frmCustomerDatabase.getContentPane().add(txtPhoneMobile);
		txtPhoneMobile.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(27, 203, 46, 14);
		frmCustomerDatabase.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(122, 200, 236, 20);
		frmCustomerDatabase.getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblNotes = new JLabel("Notes:");
		lblNotes.setBounds(27, 236, 46, 14);
		frmCustomerDatabase.getContentPane().add(lblNotes);
		
		txtNotes = new JTextArea();
		txtNotes.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(txtNotes);
		scrollPane.setBounds(122, 231, 236, 80);
		frmCustomerDatabase.getContentPane().add(scrollPane);
		
		JButton btnCreateNew = new JButton("Create new");
		btnCreateNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eraseFields();
				currentCustomer = null;
				browseIdx = getLastBrowseIndex()+1;
				txtName.requestFocusInWindow();
			}
		});
		btnCreateNew.setBounds(27, 339, 100, 23);
		frmCustomerDatabase.getContentPane().add(btnCreateNew);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentCustomer != null) { //modify customer data
					clm.modifyCustomer(xmlName, browseIdx,
							formCustomerFromFields());	
				}
				else { //create a new customer
					Customer newCust = formCustomerFromFields();
					if(!newCust.getName().equalsIgnoreCase("")) {
						clm.addCustomer(xmlName, newCust);
						currentCustomer = newCust;						
					}
				}
			}
		});
		btnSave.setBounds(175, 339, 89, 23);
		frmCustomerDatabase.getContentPane().add(btnSave);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentCustomer != null) {
					eraseFields();
					clm.removeCustomer(xmlName, currentCustomer);
					initializeData();	
				}
			}
		});
		btnDelete.setBounds(269, 339, 89, 23);
		frmCustomerDatabase.getContentPane().add(btnDelete);
		
		JButton btnPrevious = new JButton("<");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browse(browseIdx-1);
			}
		});
		btnPrevious.setBounds(27, 413, 89, 23);
		frmCustomerDatabase.getContentPane().add(btnPrevious);
		
		JButton btnNext = new JButton(">");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				browse(browseIdx+1);
			}
		});
		btnNext.setBounds(269, 413, 89, 23);
		frmCustomerDatabase.getContentPane().add(btnNext);
	}
	
	/**
	 * Fill form's fields with first Customer element's data.
	 */
	private void initializeData() {
		File file = new File(xmlName);
		if(!file.exists()) {
			eraseFields();
			currentCustomer = null;
			clm.createNewXml(xmlName);		
		} 
		else {
			List<Customer> lst = new ArrayList<Customer>();
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				lst = clm.getCustomers(fis);
				if(!lst.isEmpty()) {
					currentCustomer = lst.get(0);
					fillFields(currentCustomer);	
				}
				else {
					eraseFields();
					currentCustomer = null;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
		}
		browseIdx = 0;
	}
	
	/**
	 * Browse Customer elements by index.
	 * @param idx - Index of the Customer element
	 */
	private void browse(int idx) {
		File file = new File(xmlName);
		if(!file.exists()) {
			clm.createNewXml(xmlName);
		} 
		else {
			List<Customer> lst = new ArrayList<Customer>();
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				lst = clm.getCustomers(fis);
				if(!lst.isEmpty()) {
					if(idx >= 0 && idx < lst.size()) {
						currentCustomer = lst.get(idx);
						fillFields(currentCustomer);
						browseIdx = idx;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Get browsing index for the last Customer element.
	 * @return
	 */
	private int getLastBrowseIndex() {
		File file = new File(xmlName);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			List<Customer> lst = new ArrayList<Customer>();
			lst = clm.getCustomers(fis);
			return lst.size()-1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Erase all fields in the form.
	 */
	private void eraseFields() {
		txtName.setText("");
		txtStreet1.setText("");
		txtStreet2.setText("");
		txtPostalCode.setText("");
		txtTown.setText("");
		txtPhoneWork.setText("");
		txtPhoneMobile.setText("");
		txtEmail.setText("");
		txtNotes.setText("");
	}
	
	/**
	 * Fill Customer element's data to form's fields.
	 * @param cust
	 */
	private void fillFields(Customer cust) {
		txtName.setText(cust.getName());
		Address address = cust.getAddress();
		txtStreet1.setText(address.getStreet().get(0));
		txtStreet2.setText(address.getStreet().get(1));
		txtPostalCode.setText(address.getPostalCode());
		txtTown.setText(address.getTown());
		List<Object> emailsAndPhones = cust.getEmailOrPhone();
		Phone phone = null;
		Email email = null;
		for(Object obj : emailsAndPhones) {
			if (obj instanceof Phone) {
				phone = ((Phone) obj);
				if(phone.getType().equalsIgnoreCase(PHONE_WORK)) {
					txtPhoneWork.setText(phone.getValue());
				}
				else if(phone.getType().equalsIgnoreCase(PHONE_MOBILE)) {
					txtPhoneMobile.setText(phone.getValue());
				}
			} 
			else if (obj instanceof Email) {
				email = ((Email) obj);
				txtEmail.setText(email.getValue());
			}
		}
		txtNotes.setText(cust.getNotes());	
	}
	
	/**
	 * Create Customer object from the data of form's fields.
	 * @return Customer object
	 */
	private Customer formCustomerFromFields() {
		Address address = CustomerListManager.createAddress(ADDRESS_TYPE, txtStreet1.getText(),
				txtStreet2.getText(), txtPostalCode.getText(), txtTown.getText());
		Phone work = CustomerListManager.createPhone(PHONE_WORK, txtPhoneWork.getText());
		Phone mobile = CustomerListManager.createPhone(PHONE_MOBILE, txtPhoneMobile.getText());
		Email email = CustomerListManager.createEmail(EMAIL_WORK, txtEmail.getText());
		Customer cust = CustomerListManager.createCustomer(txtName.getText(), address, work,
				email, mobile, txtNotes.getText());
	
		return cust;
	}
	
	/**
	 * Set location for the XML file.
	 * @return
	 */
	public String setXmlLocation() {
		FileDialog fd = new FileDialog(new Frame(), "Select XML file", FileDialog.LOAD);
		fd.setFile("*.xml");
		fd.setVisible(true);
		
		return fd.getDirectory()+fd.getFile();
	}
}
