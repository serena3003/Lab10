package it.polito.tdp.porto.model;

public class CoAuthor {
	
	private int idAuthor1;
	private int idAuthor2;
	private int idArticle;
	
	public CoAuthor(int idAuthor1, int idAuthor2, int idArticle) {
		this.idAuthor1 = idAuthor1;
		this.idAuthor2 = idAuthor2;
		this.idArticle = idArticle;
	}

	public int getIdAuthor1() {
		return idAuthor1;
	}

	public void setIdAuthor1(int idAuthor1) {
		this.idAuthor1 = idAuthor1;
	}

	public int getIdAuthor2() {
		return idAuthor2;
	}

	public void setIdAuthor2(int idAuthor2) {
		this.idAuthor2 = idAuthor2;
	}

	public int getIdArticle() {
		return idArticle;
	}

	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idArticle;
		result = prime * result + idAuthor1;
		result = prime * result + idAuthor2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoAuthor other = (CoAuthor) obj;
		if (idArticle != other.idArticle)
			return false;
		if (idAuthor1 != other.idAuthor1)
			return false;
		if (idAuthor2 != other.idAuthor2)
			return false;
		return true;
	}
}
