package world_death_rate.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import world_body_fat_percentage.domain.WorldBodyFatPercentage;

//import java.util.ArrayList;
//import java.util.List;

import world_death_rate.domain.WorldDeathRate;

/**
 * DDL functions performed in database
 */
public class WorldDeathRateDao {
	/**
	 * user name to connect to the database 
	 */
	private static String MySQL_user = "hunger_world_statistics";  //TODO change user
	
	/**
	 * password of your username to connect to the database
	 */
	private static String MySQL_password = "hunger123";  //TODO change password
	public static WorldDeathRate findByUsername(String cntry) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		WorldDeathRate wdr = new WorldDeathRate();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
		    String sql = "select * from world_death_rate where country=?";
		    PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setString(1,cntry);
		    ResultSet resultSet = preparestatement.executeQuery();
		    //ResultSet resultSet  = preparestatement.executeUpdate();
		    while(resultSet.next()){
		    	String com_cntry = resultSet.getString("country");
		    	if(com_cntry.equals(cntry)){
		    		wdr.setDeath_rate(Float.parseFloat(resultSet.getString("death_rate")));
		    		wdr.setCountry(com_cntry);
		    		wdr.setContinent(resultSet.getString("continent"));		
		    	}
		    }
		    connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return wdr;
	}	
	
	/**
	 * insert Entity1
	 * @param form
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	
	public void add(WorldDeathRate form) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("We are here");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			
			String sql = "insert into world_death_rate values(?,?,?)";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setFloat(1,form.getDeath_rate());
		    preparestatement.setString(2,form.getCountry());
		    preparestatement.setString(3,form.getContinent());
		    preparestatement.executeUpdate();
		    connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
//	
//	
	public void update(WorldDeathRate form) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("Now going to update");
		System.out.println(form);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			
			String sql = "UPDATE world_death_rate SET death_rate = ?, continent = ? where country = ?;";
			System.out.println("Update Executed");
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
		    preparestatement.setFloat(1,form.getDeath_rate());
			preparestatement.setString(2,form.getContinent());
		    preparestatement.setString(3,form.getCountry());
		    preparestatement.executeUpdate();
		    connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
//	
//	
	public void delete(String cnty) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("Now going to delete");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			
			String sql = "delete from world_death_rate where country = ?";
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
	
	public List<Object> findCC() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Object> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			String sql = "select country, continent "
					+ "from world_death_rate "
					+ "where death_rate > 12.00 "
					+ "order by continent;";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
			ResultSet resultSet = preparestatement.executeQuery();			
			while(resultSet.next()){
				
				WorldDeathRate bf = new WorldDeathRate();
				
				bf.setCountry(resultSet.getString("country"));
				bf.setContinent(resultSet.getString("continent"));
				

	    		list.add(bf);
			 }
			connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
		
	}
	public List<Object> findCCD() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		List<Object> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hunger_index_worldwide",MySQL_user, MySQL_password);
			String sql = "select country, continent, death_rate "
					+ "from world_death_rate "
					+ "where exists "
					+ "(select body_fat_percentage "
					+ "from world_body_fat_percentage "
					+ "where world_body_fat_percentage.country = world_death_rate.country and "
					+ "body_fat_percentage > 5.00);";
			PreparedStatement preparestatement = connect.prepareStatement(sql); 
			ResultSet resultSet = preparestatement.executeQuery();			
			while(resultSet.next()){
				
				WorldDeathRate bf = new WorldDeathRate();
//				WorldBodyFatPercentage fb=new WorldBodyFatPercentage();
//				fb.setBody_fat_percentage(resultSet.getFloat("body_fat_percentage"));
				System.out.println(resultSet.getString("country"));
				bf.setDeath_rate(resultSet.getFloat("death_rate"));
				bf.setCountry(resultSet.getString("country"));
				bf.setContinent(resultSet.getString("continent"));
				

	    		list.add(bf);
			 }
			connect.close();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return list;
		
	}
}
