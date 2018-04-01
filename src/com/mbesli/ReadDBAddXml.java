package com.mbesli;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class ReadDBAddXml {

	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		Statement statement = null;
		ResultSet result = null;

		String url = "jdbc:mysql://localhost:3306/sampledb";
		String username = "root";
		String password = "besli";

		Users users = new Users();
		users.setUserList(new ArrayList<>());

		try {

			conn = DriverManager.getConnection(url, username, password);
			if (conn != null) {
				System.out.println("Connected to DB");
			} else {
				System.out.println("Could not connect to DB");
				throw new SQLException("Database ba�lanamad�");
			}

			String sql = "SELECT * FROM users";
			statement = conn.createStatement();
			result = statement.executeQuery(sql);
			while (result.next()) {
				User user = new User();
				user.setName(result.getString("username"));
				user.setPass(result.getString("password"));
				user.setFullname(result.getString("fullname"));
				user.setEmail(result.getString("email"));
				users.getUserList().add(user);

				System.out.println(user.toString());

			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (result != null)
				result.close();
			if (statement != null)
				statement.close();
			if (conn != null) {
				conn.close();
			}
		}

		try {
			File file = new File("D:\\Desktop\\eclipse-workspace\\ReadAndFileWrite\\resources\\xmlfile2.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(users, file);
			// jaxbMarshaller.marshal(user, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		} finally {
		}
	}
}
