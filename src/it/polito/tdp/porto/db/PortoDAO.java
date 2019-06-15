package it.polito.tdp.porto.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.CoAuthor;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	public Author getAutoreId(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticoloId(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<CoAuthor> getCoAuthor(){
		String sql = "SELECT c1.authorid id1, c2.authorid id2, c1.eprintid articolo "+
					"FROM creator c1, creator c2 " + 
				"WHERE c1.eprintid=c2.eprintid AND c1.authorid>c2.authorid";
		
		List<CoAuthor> result = new ArrayList<CoAuthor>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new CoAuthor(rs.getInt("id1"), rs.getInt("id2"), rs.getInt("articolo")));
			}

			return result;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Paper> getPaper(){
		
		String sql="SELECT DISTINCT (creator.eprintid), paper.title, paper.issn, paper.publication, paper.TYPE, paper.types " + 
				" FROM  creator, paper " + 
				" WHERE creator.eprintid = paper.eprintid";
		List<Paper> result = new ArrayList<Paper>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Paper p = new Paper(rs.getInt("eprintid"), rs.getString("title"),rs.getString("issn"), rs.getString("publication"), rs.getString("TYPE"), rs.getString("types"));
				result.add(p);
			}

			return result;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	public List<Author> getAuthor(){
		String sql = "SELECT * " + 
				" FROM author ";
		
		List<Author> result = new ArrayList<Author>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//Author a = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				result.add(new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname")));
			}

			return result;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}