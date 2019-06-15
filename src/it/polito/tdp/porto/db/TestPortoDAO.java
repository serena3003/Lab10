package it.polito.tdp.porto.db;

import java.util.List;

import it.polito.tdp.porto.model.Paper;

public class TestPortoDAO {
	
	public static void main(String args[]) {
		PortoDAO pd = new PortoDAO();
		/*System.out.println(pd.getAutoreId(85));
		System.out.println(pd.getArticoloId(2293546));
		System.out.println(pd.getArticoloId(1941144));*/
		
		List<Paper> list = pd.getPaper();
		System.out.println("\n LIST: \n");
		for (Paper p : list)
			System.out.println(p.toString()+"\n");
		
		//System.out.println(pd.getCoAuthor(1941144));
	}

}
