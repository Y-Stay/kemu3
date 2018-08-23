package kemu2daochu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



public class oracletest {

	public static void main(String[] args) {
		KSDao kd=new KSDao();
		Tongji tj=new Tongji();
		tj=kd.getDayTongji("2018-04-26");
		System.out.println(tj.getzongji());
	}

}
