package myFile;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXmlWriteDB {

	static PreparedStatement stmt;
	static Connection conn = null;
	static Statement st;

	static String name = null;
	static String pass = null;
	static String fullname = null;
	static String email = null;

	public static void main(String argv[]) {

		try {
			File fXmlFile = new File("D:\\Desktop\\eclipse-workspace\\ReadAndFileWrite\\resources\\file.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			System.out.println("Name of Xml Data:" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("list");
			System.out.print("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					name = eElement.getElementsByTagName("username").item(0).getTextContent();
					pass = eElement.getElementsByTagName("password").item(0).getTextContent();
					fullname = eElement.getElementsByTagName("fullname").item(0).getTextContent();
					email = eElement.getElementsByTagName("email").item(0).getTextContent();

					System.out.println("Id : " + eElement.getAttribute("id"));
					System.out.println("Username : " + name);
					System.out.println("Password : " + pass);
					System.out.println("Fullname : " + fullname);
					System.out.println("Email : " + email);
				}
				try {
					String url = "jdbc:mysql://localhost:3306/sampledb";
					String username = "root";
					String password = "besli";

					conn = DriverManager.getConnection(url, username, password);
					if (conn != null) {
						System.out.println("Connected to the database sampledb");
					}
				} catch (SQLException ex) {
					System.out.println("user/password is invalid");
					ex.printStackTrace();
				}
				st = conn.createStatement();
				int i = st.executeUpdate("insert into users (username,password,fullname,email) values('" + name + "','"
						+ pass + "','" + fullname + "','" + email + "')");	
		           System.out.println("Data is successfully inserted!");
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}