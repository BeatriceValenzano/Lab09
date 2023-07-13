package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryAndNumber;

public class BordersDAO {

	public List<Country> loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				result.add(c);
				idMap.put(c.getcCode(), c);
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Country> getCountryPairs(int anno, Map<Integer, Country> idMap) {

		final String sql = "SELECT distinct state1no "
				+ "FROM contiguity "
				+ "WHERE YEAR <= ? AND conttype=1";
		List<Country> vertici = new LinkedList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Country c = idMap.get(rs.getInt("state1no")); 
				vertici.add(c);
			}
			conn.close();
			return vertici;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			return null;
		}
	}
	
	public List<Border> getEdges(int anno, Map<Integer, Country> idMap) {
		
		final String sql = "SELECT state1no, state2no "
				+ "FROM contiguity "
				+ "WHERE YEAR <= ? AND conttype = 1 AND state1no<state2no";

		List<Border> edges = new LinkedList<Border>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Border b = new Border(idMap.get(rs.getInt("state1no")), idMap.get(rs.getInt("state2no")));
				edges.add(b);
			}
			conn.close();
			return edges;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			return null;
		}
	}
	
	public List<CountryAndNumber> getConfini(int anno, Map<Integer, Country> idMap) {
		
		
		final String sql = "SELECT state1no, co.stateNme, COUNT(*) "
				+ "FROM contiguity c, country co "
				+ "WHERE YEAR <= 1900 AND conttype=1 and co.CCode=c.state1no "
				+ "GROUP BY STATE1NO "
				+ "ORDER BY stateNme";
		List<CountryAndNumber> confini = new LinkedList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
			}
			
			conn.close();
			return confini;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
