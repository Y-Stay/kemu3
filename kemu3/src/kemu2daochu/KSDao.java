package kemu2daochu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;



public class KSDao {
	public ArrayList<KS> getKSbyDate(String rq){
		ArrayList<KS> kss=new ArrayList<KS>();
		Connection conn=null;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		try {
			conn=jdbcUitl.getconn();
			String sql="select xm,yycs, zjhm,jxmc,id,ksjlbh from t_ksrw where ykrq=to_date(?,'yyyy-mm-dd')";
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rq);
			rs=pstat.executeQuery();
			int i=1;
			
			while(rs.next()) {
				KS ks=new KS();
				ks.setXuhao(i);
				ks.setName(rs.getString("xm"));
				ks.setZjhm(rs.getString("zjhm"));
				ks.setJx(rs.getString("jxmc"));
				ks.setKscs(rs.getInt("yycs"));
				ks.setZw(0);
				ks.setRwbh(rs.getString("id"));
				kss.add(ks);
				i++;
			}
			return kss;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
		
	}
	public ArrayList<CJ> getCJbyDate(String rq){
		ArrayList<CJ> cjs=new ArrayList<CJ>();
		Connection conn=null;		
		PreparedStatement pre = null;		
		ResultSet rs = null;
		try {
			
			conn = jdbcUitl.getconn();
			String sql = "select xm,zjhm,jxmc,ykcs,yycs,jgptzt from T_KSRW where ykrq=to_date(?,'yyyy-mm-dd')order by jgptzt";
			
			pre = conn.prepareStatement(sql);// 实例化预编译语句
			pre.setString(1, rq);
			rs = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			
			int xuhao=1;
			while (rs.next()) {
				CJ cj=new CJ();
				cj.setXuhao(xuhao);
				cj.setName(rs.getString("xm"));
				cj.setZjhm(rs.getString("zjhm"));
				cj.setJx(rs.getString("jxmc"));
				cj.setCs(rs.getInt("ykcs"));
				if(rs.getInt("yycs")!=1)
					cj.setBukao("B");
				else cj.setBukao("");
				if(rs.getInt("jgptzt")==1)
					cj.setCj("合格");
				if(rs.getInt("jgptzt")==2)
					cj.setCj("不合格");
				if(rs.getInt("jgptzt")==0)
					cj.setCj("缺考");
			cjs.add(cj);
			xuhao++;
			}			
			return cjs;
			}  catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			jdbcUitl.clos1(conn,pre, rs);
			
		}
	}
	public Tongji getTongji(String jx ,String rq){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	
    	Tongji tongji=new Tongji();
    	try {
			
				conn=jdbcUitl.getconn();
				
				String sql0="select count(id) zongji,sum(case when jgptzt=1  then 1 else 0 end) hege,sum(case when jgptzt=2 then 1 else 0 end) buhege from t_ksrw where ykrq=to_date(?,'yyyy-mm-dd') and jxmc";
				StringBuilder sb= new StringBuilder(sql0);
				if(jx==null)
					sb.append(" is null");
				else sb.append("=?");
				String sql=sb.toString();
				pstat=conn.prepareStatement(sql);
				
				pstat.setString(1, rq);
				if(jx!=null)
				pstat.setString(2, jx);
				
				int i=0;
				rs=pstat.executeQuery();
				while(rs.next()) {
					double hegelv=0.0;
					tongji.setzongji(rs.getInt("hege")+rs.getInt("buhege"));
					tongji.setHege(rs.getInt("hege"));
					tongji.setBuhege(rs.getInt("buhege"));
					if(rs.getInt("zongji")==0||rs.getInt("hege")==0)
					hegelv=0.0;
					else
					hegelv=(double)rs.getInt("hege")/(double)rs.getInt("zongji");
					tongji.setHegelv(hegelv);
				}

			} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return tongji;
    }
	public List<String> getjx(List<String> rqs){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	ArrayList<String> jx = new ArrayList<String>();
    	try {
			conn=jdbcUitl.getconn();
			String sql="select jxmc from t_ksrw where ykrq between to_date(?,'yyyy-mm-dd')  and  to_date(?,'yyyy-mm-dd') group by jxmc order by jxmc";
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rqs.get(0));
			pstat.setString(2, rqs.get(4));
			int i=0;
			rs=pstat.executeQuery();
			while(rs.next()) {
				jx.add(rs.getString("jxmc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return jx;
    }
	public Tongji getDayTongji(String rq){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	Tongji tongji=new Tongji();
    	try {
			conn=jdbcUitl.getconn();
			String sql="select count(id) zongji,sum(case when jgptzt=1 then 1 else 0 end) hege,sum (case when jgptzt=2 then 1 else 0 end) buhege from t_ksrw where ykrq=to_date(?,'yyyy-mm-dd')";
					
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rq);
			
//			int i=0;
			rs=pstat.executeQuery();
			while(rs.next()) {
				double hegelv=0.0;
				int zongji=rs.getInt("hege")+rs.getInt("buhege");
				tongji.setzongji(zongji);
				tongji.setHege(rs.getInt("hege"));
				tongji.setBuhege(rs.getInt("buhege"));
				if(rs.getInt("zongji")==0||rs.getInt("hege")==0)
				hegelv=0.0;
				else
				hegelv=(double)rs.getInt("hege")/(double)zongji;
				tongji.setHegelv(hegelv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return tongji;
    }
	public Tongji getzhouTongji(List<String> rqs){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	Tongji tongji=new Tongji();
    	try {
			conn=jdbcUitl.getconn();
			String sql="select count(id) zongji,sum(case when jgptzt=1 then 1 else 0 end) hege,sum (case when jgptzt=2 then 1 else 0 end) buhege from t_ksrw where ykrq between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') ";
					
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rqs.get(0));
			pstat.setString(2, rqs.get(4));
			
//			int i=0;
			rs=pstat.executeQuery();
			while(rs.next()) {
				double hegelv=0.0;
				int zongji=rs.getInt("hege")+rs.getInt("buhege");
				tongji.setzongji(zongji);
				tongji.setHege(rs.getInt("hege"));
				tongji.setBuhege(rs.getInt("buhege"));
				if(rs.getInt("zongji")==0||rs.getInt("hege")==0)
				hegelv=0.0;
				else
				hegelv=(double)rs.getInt("hege")/(double)zongji;
				tongji.setHegelv(hegelv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return tongji;
    }
	public Tongji getmonthTongji(List<String> rqs){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	Tongji tongji=new Tongji();
    	String rq5=rqs.get(4);
    	String rqx=rq5.substring(0, 7);
    	try {
			conn=jdbcUitl.getconn();
			String sql="select count(id) zongji,sum(case when jgptzt=1 then 1 else 0 end) hege,sum (case when jgptzt=2 then 1 else 0 end) buhege from t_ksrw where to_char(ykrq,'yyyy-mm')=? ";
					
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rqx);
			
//			int i=0;
			rs=pstat.executeQuery();
			while(rs.next()) {
				double hegelv=0.0;
				int zongji=rs.getInt("hege")+rs.getInt("buhege");
				tongji.setzongji(zongji);
				tongji.setHege(rs.getInt("hege"));
				tongji.setBuhege(rs.getInt("buhege"));
				if(rs.getInt("zongji")==0||rs.getInt("hege")==0)
				hegelv=0.0;
				else
				hegelv=(double)rs.getInt("hege")/(double)zongji;
				tongji.setHegelv(hegelv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return tongji;
    }
	public Tongji getyearTongji(List<String> rqs){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	Tongji tongji=new Tongji();
    	String rq5=rqs.get(4);
    	String rqx=rq5.substring(0, 4);
    	try {
			conn=jdbcUitl.getconn();
			String sql="select count(id) zongji,sum(case when jgptzt=1 then 1 else 0 end) hege,sum (case when jgptzt=2 then 1 else 0 end) buhege from t_ksrw where to_char(ykrq,'yyyy')=? ";
					
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rqx);
			
//			int i=0;
			rs=pstat.executeQuery();
			while(rs.next()) {
				double hegelv=0.0;
				int zongji=rs.getInt("hege")+rs.getInt("buhege");
				tongji.setzongji(zongji);
				tongji.setHege(rs.getInt("hege"));
				tongji.setBuhege(rs.getInt("buhege"));
				if(rs.getInt("zongji")==0||rs.getInt("hege")==0)
				hegelv=0.0;
				else
				hegelv=(double)rs.getInt("hege")/(double)zongji;
				tongji.setHegelv(hegelv);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return tongji;
    }
	public List<Tongji> getzhouJxTongji(List<String> rqs){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	
    	ArrayList<Tongji> tongji= new ArrayList<Tongji>();
    	try {
			conn=jdbcUitl.getconn();
			String sql="select jxmc,count(id) zongji,sum(case when jgptzt=1  then 1 else 0 end) hege,sum(case when jgptzt=2 then 1 else 0 end) buhege from t_ksrw where ykrq between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') group by jxmc order by jxmc";
					
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rqs.get(0));
			pstat.setString(2, rqs.get(4));
			
//			int i=0;
			rs=pstat.executeQuery();
			while(rs.next()) {
				Tongji tj=new Tongji();
				double hegelv=0.0;
				tj.setJx(rs.getString("jxmc"));
				int zongji=rs.getInt("hege")+rs.getInt("buhege");
				tj.setzongji(zongji);
				tj.setHege(rs.getInt("hege"));
				tj.setBuhege(rs.getInt("buhege"));
				if(rs.getInt("zongji")==0||rs.getInt("hege")==0)
				hegelv=0.0;
				else
				hegelv=(double)rs.getInt("hege")/(double)zongji;
				tj.setHegelv(hegelv);
				tongji.add(tj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return tongji;
    }
	public Tongji getbukao(String rq){
    	Connection conn=null;
    	PreparedStatement pstat=null;
    	ResultSet rs=null;
    	Tongji tj=new Tongji();	
    	
    	try {
			conn=jdbcUitl.getconn();
			String sql="select sum(case when yycs=1 then 1 else 0 end) chukao,sum(case when yycs>1 then 1 else 0 end) bukao from T_ksrw where ykrq=to_date(?,'yyyy-mm-dd')";
				
			pstat=conn.prepareStatement(sql);
			pstat.setString(1, rq);
			rs=pstat.executeQuery();
			while(rs.next()) {
				
				tj.setChukao(rs.getInt("chukao"));
				tj.setBukao(rs.getInt("bukao"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			jdbcUitl.clos1(conn, pstat, rs);
		}
    	return tj;
    }

}
