package world_vaccination_info.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import world_happiness_index.domain.WorldHappinessIndex;
import world_vaccination_info.domain.WorldVaccinationInfo;

/**
 * DDL functions performed in database
 */
public class WorldVaccinationInfoDao {
	/**
	 * user name to connect to the database 
	 */
	private static String MySQL_user = "hunger_world_statistics";  //TODO change user
	
	/**
	 * password of your username to connect to the database
	 */
	private static String MySQL_password = "hunger123";  //TODO change password
	public static WorldVaccinationInfo findByCountry(String countryName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		WorldVaccinationInfo entity1 = new WorldVaccinationInfo();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
		    String sql = "select * from world_vaccination_info where country = ?";
		    PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,countryName);
		    ResultSet resultSet = preparestatement.executeQuery();
		    //ResultSet resultSet  = preparestatement.executeUpdate();
		    while(resultSet.next()){
		    	String country = resultSet.getString("country");
		    	if(country.equals(countryName)){
		    		entity1.setContinent(resultSet.getString("continent"));
		    		entity1.setCountry(country);
		    		entity1.setVaccination_rate(Float.parseFloat(resultSet.getString("vaccination_rate")));
		    	}
		    }
		    connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return entity1;
	}	
	
	/**
	 * insert Entity1
	 * @param form
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	public void add(WorldVaccinationInfo form) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("We are here");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			
			String sql = "insert into world_vaccination_info(vaccination_rate, country, continent) values(?,?,?)";
			PreparedStatement preparestatement = connect.prepareStatement(sql);
		    preparestatement.setFloat(1,form.getVaccination_rate());
		    preparestatement.setString(2,form.getCountry());
		    preparestatement.setString(3,form.getContinent());
		    preparestatement.executeUpdate();
		    connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void update(WorldVaccinationInfo form) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("Now going to update");
		System.out.println(form);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			
			String sql = "UPDATE world_vaccination_info SET vaccination_rate = ?, continent = ? where country = ?;";
			System.out.println("Update Executed");
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
			preparestatement.setFloat(1,form.getVaccination_rate());
			preparestatement.setString(2,form.getContinent());
		    preparestatement.setString(3,form.getCountry());
		    preparestatement.executeUpdate();
		    connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

	
	
	public void delete(String cnty) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("Now going to delete");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			
			String sql = "delete from world_vaccination_info where country = ?";
			System.out.println(cnty);
			System.out.println("Delete Executed");
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,cnty);
		    preparestatement.executeUpdate();
		    connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static List<Object> findcq1() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Object> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide", MySQL_user, MySQL_password);
			String sql = "select DTH.happiness_index AS happiness_index, DTH.country "
						 + "from world_happiness_index DTH "
						 + "INNER JOIN world_vaccination_info VC "
						 + "ON DTH.country = VC.country "
						 + "order by DTH.country;";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
			ResultSet resultSet = preparestatement.executeQuery();			
			while(resultSet.next()){
				WorldVaccinationInfo bf = new WorldVaccinationInfo();
				bf.setVaccination_rate(resultSet.getFloat("vaccination_rate"));
				bf.setCountry(resultSet.getString("country"));
	    		list.add(bf);
			 }
			connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
	}
	
	
}
