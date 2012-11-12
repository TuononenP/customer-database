package com.petrituononen.customerdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.petrituononen.customerdb.jaxb.Address;
import com.petrituononen.customerdb.jaxb.Customer;
import com.petrituononen.customerdb.jaxb.Customers;
import com.petrituononen.customerdb.jaxb.Email;
import com.petrituononen.customerdb.jaxb.ObjectFactory;
import com.petrituononen.customerdb.jaxb.Phone;


/**
 * Add/remove/modify Customer elements in an XML file.
 * Form JAXB data types.
 *
 * @author Petri Tuononen
 *
 */
public class CustomerListManager {

	private JAXBContext jaxbContext = null;
	private Unmarshaller unmarshaller = null;
	private Marshaller marshaller = null;
	private Customers customers = null;
	private static ObjectFactory factory = null;
	
	public CustomerListManager() {
		try {
			factory = new ObjectFactory();
			jaxbContext = JAXBContext.newInstance("com.petrituononen.customerdb.jaxb");
			unmarshaller = jaxbContext.createUnmarshaller();
			marshaller = jaxbContext.createMarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get a list of Customer elements in an XML file.
	 * @param istrm
	 * @return List of Customer objects
	 */
	public List<Customer> getCustomers(InputStream istrm) {
		try {
			customers = (Customers) unmarshaller.unmarshal(istrm);
			return(customers.getCustomer());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Create an XML file with Customers root element.
	 * @param xmlName - Name of the XML file
	 */
	public void createNewXml(String xmlName) {
		customers = factory.createCustomers();
		
		try {
			marshaller.marshal(customers, new FileOutputStream(xmlName)) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add Customer element to XML file.
	 * @param xmlName - Name of the XML file
	 * @param cust - Customer object
	 */
	public void addCustomer(String xmlName, Customer cust) {
		try {
			File file = new File(xmlName);
			if(!file.exists()) {
				createNewXml(xmlName);
			}
			FileInputStream fis = new FileInputStream(file);
			
			customers = (Customers) unmarshaller.unmarshal(fis);
			customers.getCustomer().add(cust);
			
			marshaller.marshal(customers, new FileOutputStream(xmlName)) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(JAXBException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove Customer element from the XML file.
	 * @param xmlName - Name of the XML file
	 * @param cust - Customer object
	 */
	public void removeCustomer(String xmlName, Customer cust) {
		File file = new File(xmlName);
		if(file.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				customers = (Customers) unmarshaller.unmarshal(fis);
				List<Customer> lst = customers.getCustomer();
				for(int i=0; i<lst.size(); i++) {
					Customer c = lst.get(i);
					if(c.getName().equalsIgnoreCase(cust.getName())) {
						customers.getCustomer().remove(c);
						break;
					}
				}
				marshaller.marshal(customers, new FileOutputStream(xmlName)) ;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Modify Customer element in the XML file.
	 * @param xmlName - Name of the xml file
	 * @param idx - Index of the Customer to replace
	 * @param newC - Customer object
	 */
	public void modifyCustomer(String xmlName, int idx, Customer newC) {
		File file = new File(xmlName);
		if(file.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				customers = (Customers) unmarshaller.unmarshal(fis);
				customers.getCustomer().set(idx, newC);
				marshaller.marshal(customers, new FileOutputStream(xmlName)) ;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Create Customer object.
	 * @param name
	 * @param address
	 * @param work
	 * @param email
	 * @param mobile
	 * @param notes
	 * @return Customer
	 */
	public static Customer createCustomer(String name, Address address,
			Phone work, Email email, Phone mobile, String notes) {
		Customer cust = factory.createCustomer();
		cust.setName(name);
		cust.setAddress(address);
		cust.getEmailOrPhone().add(work);
		cust.getEmailOrPhone().add(email);
		cust.getEmailOrPhone().add(mobile);
		cust.setNotes(notes);
		
		return cust;
	}
	
	/**
	 * Create Address object.
	 * @param type
	 * @param street1
	 * @param street2
	 * @param postalCode
	 * @param town
	 * @return Address
	 */
	public static Address createAddress(String type, String street1,
			String street2, String postalCode, String town) {
		Address address = factory.createAddress();
		address.setType(type);
		address.getStreet().add(street1);
		address.getStreet().add(street2);
		address.setPostalCode(postalCode);
		address.setTown(town);
		
		return address;
	}
	
	/**
	 * Create Phone object.
	 * @param type
	 * @param value
	 * @return Phone
	 */
	public static Phone createPhone(String type, String value) {
		Phone phone = factory.createPhone();
		phone.setType(type);
		phone.setValue(value);
		
		return phone;
	}
	
	/**
	 * Create Email object.
	 * @param type 
	 * @param value 
	 * @return Email
	 */
	public static Email createEmail(String type, String value) {
		Email email = factory.createEmail();
		email.setType(type);
		email.setValue(value);
		
		return email;
	}
	
}
