package testingJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Servlet implementation class senddata
 */
@WebServlet("/senddata")
public class SendData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));

		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		System.out.println(json);

//				Api var = new Gson().fromJson(json, Api.class);
//				Api[] arr = new Gson().fromJson(json,Api[].class);
//				
//				System.out.println(var.getName());

		JSONParser jsonParser = new JSONParser();
//				System.out.println("hello1111111111111");
		try {
			Connection con = ConnectToDB();
			PreparedStatement stmt = con.prepareStatement("INSERT INTO countries values (?, ? )");
//					System.out.println("hello22222222222222222");
			JSONArray jsonArray = (JSONArray) jsonParser.parse(json);
//					JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
			for (Object obj : jsonArray) {

				JSONObject record = (JSONObject) obj;
//						System.out.println("hello 33333333333");

				String nativeName = (String) record.get("nativeName");
				String numCode = (String) record.get("numericCode");
//						System.out.println(record.get("nativeName"));
//						System.out.println(record.get("numericCode"));
				
				stmt.setString(1, nativeName);
				stmt.setString(2, numCode);
				stmt.executeUpdate();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("helloeeeeeeeeeeeeeeeeee	");
			e.printStackTrace();
		}

	}

	public static Connection ConnectToDB() throws Exception {
		// Registering the Driver
//	      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		// Getting the connection
		Class.forName("com.mysql.jdbc.Driver");
		String mysqlUrl = "jdbc:mysql://localhost:3306/demo";
		Connection con = DriverManager.getConnection(mysqlUrl, "student", "student");
		System.out.println("Connection established......");
		return con;
	}
}