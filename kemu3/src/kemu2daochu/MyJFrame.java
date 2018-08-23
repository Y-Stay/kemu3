package kemu2daochu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.eltima.components.ui.DatePicker;


import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MyJFrame extends JFrame{
	private JLabel chakan=new JLabel("ѡ������:");
	final DatePicker datepick;
	final DatePicker datepick1;
	final DatePicker datepick2;
	final DatePicker datepick3;
	final DatePicker datepick4;
	final DatePicker datepick5;
	private JTextField xinxi=new JTextField(18);
	private JButton chaxun=new JButton("��������");
	private JButton cjd=new JButton("�ɼ�������");
	private JButton tongji=new JButton("ͳ�Ʋ�����");
	private JPanel nPanel=new JPanel();
	private JPanel cPanel=new JPanel();
	
	private JPanel sPanel=new JPanel();
	public MyJFrame() {
		setSize(220,500);//�����С
		setTitle("��Ŀ������");//����
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);//������ʾ
		BorderLayout bl=new BorderLayout();
	    bl.setHgap(10);
		setLayout(bl);
		datepick = getDatePicker();
		 datepick1 = getDatePicker();
		    
		    datepick2 = getDatePicker();
		    
		    datepick3 = getDatePicker();
		  
		    datepick4 = getDatePicker();
		   
		    datepick5 = getDatePicker();
		chaxun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rq=datepick.getText();
				int x=mingdandaochu(rq);
				if(x==1) {
					xinxi.setText(rq+"���������ɹ�");
				}
				
			}
		});
		cjd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rq=datepick.getText();
				int x=chengjidaochu(rq);
				if(x==1) {
					xinxi.setText(rq+"�ɼ��������ɹ�");
				}
			}
		});
        tongji.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String rq1=datepick1.getText();
				String rq2=datepick2.getText();
				String rq3=datepick3.getText();
				String rq4=datepick4.getText();
				String rq5=datepick5.getText();
				ArrayList<String> rqs = new ArrayList<String>();
				rqs.add(rq1);
				rqs.add(rq2);
				rqs.add(rq3);
				rqs.add(rq4);
				rqs.add(rq5);
				String ss=zhoutongji(rqs);
				String xx=jxzhoutongji(rqs);
				xinxi.setText(ss);
				xinxi.setText(xx);
			}
		});
		//	    add(chakan);
//	    add(datepick);
//	    add(chaxun);
//	    add(cjd);
	    cPanel.add(chakan);
	    cPanel.add(datepick);
	    cPanel.add(chaxun);
	    cPanel.add(cjd);
	    cPanel.add(datepick1);
		cPanel.add(datepick2);
		cPanel.add(datepick3);
		cPanel.add(datepick4);
		cPanel.add(datepick5);
		cPanel.add(tongji);
	    sPanel.add(xinxi);
	    add(cPanel,BorderLayout.CENTER);
	    add(sPanel,BorderLayout.SOUTH);
	    
	    this.setVisible(true);
	}
	private static DatePicker getDatePicker() {
        final DatePicker datepick;
        // ��ʽ
        String DefaultFormat = "yyyy-MM-dd";
        // ��ǰʱ��
        Date date = new Date();
        // ����
        Font font = new Font("Times New Roman", Font.BOLD, 10);
   
        Dimension dimension = new Dimension(177, 24);
   
        //int[] hilightDays = { 1, 3, 5, 7 };
   
       // int[] disabledDays = { 4, 6, 5, 9 };
   
        datepick = new DatePicker(date, DefaultFormat, font, dimension);
   
        datepick.setLocation(137, 83);
        datepick.setBounds(137, 83, 177, 24);
        // ����һ���·�����Ҫ������ʾ������
        //datepick.setHightlightdays(hilightDays, Color.red);
        // ����һ���·��в���Ҫ�����ӣ��ʻ�ɫ��ʾ
        //datepick.setDisableddays(disabledDays);
        // ���ù���
        datepick.setLocale(Locale.CHINA);
        // ����ʱ�����ɼ�
        datepick.setTimePanleVisible(true);
        return datepick;
    }
	public int mingdandaochu(String rq) {
		KSDao kd=new KSDao();
		ArrayList<KS>  kss=kd.getKSbyDate(rq);
		try {
			File file1=new File("D:\\��Ŀ������\\"+rq+"��Ŀ����������.xls");
			if (!file1.getParentFile().exists())
				file1.getParentFile().mkdirs();
			WritableWorkbook book = Workbook.createWorkbook(new File("D:\\��Ŀ������\\"+rq+"��Ŀ����������.xls"));
			
			WritableSheet sheet = book.createSheet("��һҳ", 0);
			WritableFont font1 = new WritableFont(
					WritableFont.createFont("����"), 16, WritableFont.NO_BOLD);
					font1.setColour(Colour.BLACK);
					WritableCellFormat format1 = new WritableCellFormat(font1);
					format1.setAlignment(jxl.format.Alignment.CENTRE);
					format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					format1.setBorder(Border.ALL, BorderLineStyle.THIN);

					WritableFont font2 = new WritableFont(
					WritableFont.createFont("����"), 14, WritableFont.NO_BOLD);
					font1.setColour(Colour.BLACK);
					WritableCellFormat format2 = new WritableCellFormat(font2);
					format2.setAlignment(jxl.format.Alignment.CENTRE);
					format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					format2.setBorder(Border.ALL, BorderLineStyle.THIN);


					sheet.mergeCells(0, 0, 7, 0);//�ϲ���Ԫ��
					Label label = new Label(0, 0, rq+"��Ŀ����������",format1);
					sheet.addCell(label);
					sheet.setColumnView(0, 7);//�����п��
					sheet.setColumnView(1, 7);
					sheet.setColumnView(2, 10);
					sheet.setColumnView(3, 7); 
					sheet.setColumnView(4, 5);
					sheet.setColumnView(5, 6);
					sheet.setColumnView(6, 28); 
					sheet.setColumnView(7, 18); 
					Label labelA = new Label(0, 1, "���",format2);
					Label labelB = new Label(1, 1, "����",format2);
					Label labelC = new Label(2, 1, "����",format2);
					Label labelD = new Label(3, 1, "�ѿ�",format2);
					Label labelE = new Label(4, 1, "ָ��",format2);
					Label labelF = new Label(5, 1, "����",format2);
					Label labelG = new Label(6, 1, "���֤��",format2);
					Label labelH = new Label(7, 1, "��У",format2);
					 

					sheet.addCell(labelA);
					sheet.addCell(labelB);
					sheet.addCell(labelC);
					sheet.addCell(labelD);
					sheet.addCell(labelE);
					sheet.addCell(labelF);
					sheet.addCell(labelG);
					sheet.addCell(labelH);
					
	       
	       int i=0;
	       int c=0,b=0;
	       for(i=0;i<kss.size();i++) {
	    	   KS ks=kss.get(i);
	    	   if(ks.getKscs()!=1) {
	    		   b++;
	    		  ks.setBukao("B");}
	    	   else {
	    		   c++;
	    		   ks.setBukao(null);
	    	   }
	    	   Number labelXh = new jxl.write.Number(0, i + 2, ks.getXuhao(),format2); 
	    	   Label labelDm = new Label(1, i + 2, null,format2); 
	    	   Label labelXM = new Label(2, i + 2, ks.getName(),format2);
	    	   Label labelYK = new Label(3, i + 2, null,format2);
	    	 
	    	   Number labelZW = new jxl.write.Number(4, i + 2, ks.getZw(),format2); 
	    	   Label labelBK = new Label(5, i + 2, ks.getBukao(),format2);
	    	   Label labelZJHM = new Label(6, i + 2, ks.getZjhm(),format2);
	    	   Label labelJX = new Label(7, i + 2, ks.getJx(),format2);
	    	   sheet.addCell(labelXh);
	    	   sheet.addCell(labelDm);
	    	   sheet.addCell(labelXM);
	    	   sheet.addCell(labelYK);
	    	   sheet.addCell(labelZW);
	    	   sheet.addCell(labelBK);
	    	   sheet.addCell(labelZJHM);
	    	   sheet.addCell(labelJX);
	       }
	       sheet.mergeCells(0, i+2, 7,i+2 );
	       Label labelhz = new Label(0, i +2, rq+"��Ŀ��������"+c+"�ˣ�����"+b+"�ˡ����ƣ�"+(b+c)+"�ˡ�",format2); 
			sheet.addCell(labelhz);
	       book.write();
	       book.close();
	       return 1;
		} 
		catch (Exception e) {
			e.printStackTrace();
			xinxi.setText(e.getMessage());
			return 0;
		}
	}
	public int chengjidaochu(String rq) {
		KSDao kd=new KSDao();
		ArrayList<CJ>  cjs=kd.getCJbyDate(rq);
		try {
			File file1=new File("D:\\��Ŀ�����Գɼ���\\"+rq+"���Գɼ���.xls");
			if (!file1.getParentFile().exists())
				file1.getParentFile().mkdirs();
			// ���ļ�
			WritableWorkbook book = Workbook.createWorkbook(new File("D:\\��Ŀ�����Գɼ���\\"+rq+"���Գɼ���.xls"));
			 
			// ������Ϊ"��һҳ"�Ĺ���������0��ʾ���ǵ�һ
			WritableSheet sheet = book.createSheet("sheet", 0);
			sheet.getSettings().setTopMargin(0.5d);//����ҳ�߾�
			sheet.getSettings().setLeftMargin(0.3d);
			sheet.getSettings().setRightMargin(0.3d);
			sheet.getSettings().setBottomMargin(0.5d);


			// ��������Ϊ����,16����,�Ӵ�,��ɫΪ��ɫ
			WritableFont font1 = new WritableFont(
			WritableFont.createFont("����"), 16, WritableFont.NO_BOLD);
			font1.setColour(Colour.BLACK);
			WritableCellFormat format1 = new WritableCellFormat(font1);
			format1.setAlignment(jxl.format.Alignment.CENTRE);
			format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format1.setBorder(Border.ALL, BorderLineStyle.THIN);

			WritableFont font2 = new WritableFont(
			WritableFont.createFont("����"), 12, WritableFont.NO_BOLD);
			font1.setColour(Colour.BLACK);
			WritableCellFormat format2 = new WritableCellFormat(font2);
			format2.setAlignment(jxl.format.Alignment.CENTRE);
			format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
			format2.setBorder(Border.ALL, BorderLineStyle.THIN);
            format2.setWrap(true);

			sheet.mergeCells(0, 0, 6, 0);//�ϲ���Ԫ��
			Label label = new Label(0, 0, rq+"��Ŀ�����Գɼ���",format1);
			sheet.addCell(label);
			sheet.setColumnView(0, 5);//�����п��
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 24);
			sheet.setColumnView(3, 8); 
			sheet.setColumnView(4, 5);
			sheet.setColumnView(5, 6);
			sheet.setColumnView(6, 17); 

			// Label labelA = new Label(0, 0, "CALL_GUID", format1);
			// Label labelB = new Label(1, 0, "RELATIONID", format1);
			// Label labelC = new Label(2, 0, "ANI", format1);
			// Label labelD = new Label(3, 0, "DNIS", format1);
			// Label labelE = new Label(4, 0, "STAFF_ID", format1);
			// Label labelF = new Label(5, 0, "CALLSTARTTIME", format1);
			// Label labelG = new Label(6, 0, "CALLENDTIME", format1);
			// Label labelH = new Label(7, 0, "CALLRESULT", format1);
			// Label labelI = new Label(8, 0, "CALLRESULTREASON_ID", format1);
			// Label labelJ = new Label(9, 0, "CALLREMARK", format1);
			// Label labelK = new Label(10, 0, "EVENT_GUID", format1);
			Label labelA = new Label(0, 1, "���",format2);
			Label labelB = new Label(1, 1, "����",format2);
			Label labelC = new Label(2, 1, "���֤��",format2);
			Label labelD = new Label(3, 1, "�ɼ�",format2);
			Label labelE = new Label(4, 1, "����",format2);
			Label labelF = new Label(5, 1, "����",format2);
			Label labelG = new Label(6, 1, "��У",format2);

			sheet.addCell(labelA);
			sheet.addCell(labelB);
			sheet.addCell(labelC);
			sheet.addCell(labelD);
			sheet.addCell(labelE);
			sheet.addCell(labelF);
			sheet.addCell(labelG);
			int i=0;
			int yk=0,sk=0,hg=0,bhg=0,qk=0;
			double hegelv;
			for (i=0;i<cjs.size();i++) {
				CJ cj=cjs.get(i);
				if(cj.getCj().equals("�ϸ�"))
					hg++;
				if(cj.getCj().equals("���ϸ�"))
					bhg++;
				if(cj.getCj().equals("ȱ��"))
					qk++;
				Number labelxh = new jxl.write.Number(0, i + 2,cj.getXuhao(),format2); 
				Label labelxm = new Label(1, i + 2,cj.getName(),format2);
				Label labelzjhm = new Label(2, i + 2, cj.getZjhm(),format2);
				Label labelcj = new Label(3, i + 2,cj.getCj(),format2);
				Number labelcs = new jxl.write.Number(4, i + 2, cj.getCs(),format2);
				Label labelyycs = new Label(5, i + 2, cj.getBukao(),format2);
				Label labeljx = new Label(6, i + 2, cj.getJx(),format2);
				sheet.addCell(labelxh);
				sheet.addCell(labelxm);
				sheet.addCell(labelzjhm);
				sheet.addCell(labelcj);
				sheet.addCell(labelcs);
				sheet.addCell(labelyycs);
				sheet.addCell(labeljx);
			}
			yk=i;
			sk=hg+bhg;
			sheet.mergeCells(0, i +2, 6, i +2);			
			 hegelv=(double)hg/(double)sk*100;
			DecimalFormat num= new DecimalFormat("#.00");
			Tongji tj1=kd.getbukao(rq);
			Label labelhz = new Label(0, i +2, "Ӧ��"+yk+"�ˣ�ʵ��"+sk+"�ˣ�ȱ��"+qk+"�ˣ��ϸ�"+hg+"�ˣ��ϸ���"+num.format(hegelv)+"��������"+tj1.getChukao()+"�ˣ�����"+tj1.getBukao()+"�ˡ�",format2); 
			sheet.addCell(labelhz);
			 book.write();
		       book.close();
		       return 1;
		} catch (Exception e) {
			e.printStackTrace();
			xinxi.setText(e.getMessage());
			return 0;
		}
	}
public String zhoutongji(List<String> rqs)  {
	KSDao kd=new KSDao();
		String s=null;
		try {
			File file=new File("D:\\��Ŀ����Уͳ��");
			if(!file.exists())
				file.mkdirs();
			WritableWorkbook book = Workbook.createWorkbook(new File("D:\\��Ŀ����Уͳ��\\"+rqs.get(0)+"��"+rqs.get(4)+"��ͳ��.xls"));
			DecimalFormat num= new DecimalFormat("#.00");
			// ������Ϊ"��һҳ"�Ĺ���������0��ʾ���ǵ�һ
			WritableSheet sheet = book.createSheet("��һҳ", 0);
			for(int i=0;i<=20;i++) {
				sheet.setColumnView(i, 7);
			}
			WritableFont font1 = new WritableFont(
					WritableFont.createFont("����"), 10, WritableFont.NO_BOLD);
					font1.setColour(Colour.BLACK);
					WritableCellFormat format1 = new WritableCellFormat(font1);
					format1.setAlignment(jxl.format.Alignment.CENTRE);
					format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					format1.setBorder(Border.ALL, BorderLineStyle.THIN);

					WritableFont font2 = new WritableFont(
					WritableFont.createFont("����"), 10, WritableFont.NO_BOLD);
					font1.setColour(Colour.BLACK);
					WritableCellFormat format2 = new WritableCellFormat(font2);
					format2.setAlignment(jxl.format.Alignment.CENTRE);
					format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					format2.setBorder(Border.ALL, BorderLineStyle.THIN);
			sheet.mergeCells(0, 0, 20, 0);
			Label label = new Label(0, 0,rqs.get(0)+"��"+rqs.get(4)+"��������Ŀ��������Уͳ��",format1);	
			Label label14=new Label(0,1,null,format1);
			Label label15=new Label(0,2,"��У",format1);
			sheet.mergeCells(1, 1, 4, 1);
			Label label1 = new Label(1,1,rqs.get(0),format1);
			sheet.mergeCells(5, 1, 8, 1);
			Label label2 = new Label(5,1,rqs.get(1),format1);
			sheet.mergeCells(9, 1, 12, 1);
			Label label3 = new Label(9,1,rqs.get(2),format1);
			sheet.mergeCells(13, 1, 16, 1);
			Label label4 = new Label(13,1,rqs.get(3),format1);
			sheet.mergeCells(17, 1, 20, 1);
			Label label5 = new Label(17,1,rqs.get(4),format1);
			sheet.addCell(label);
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);
			sheet.addCell(label14);
			sheet.addCell(label15);
			//sheet.setColumnView(5, 30);
			List<String> jx=kd.getjx(rqs);
			int i=3;
			for (String string : jx) {
				Label labelA = new Label(0, i, string,format2);
				sheet.addCell(labelA);
				i++;
			}
			int clume=1;
			int row=3;
			int rowx=3;
			for(String rq:rqs) {
				Label label6 = new Label(clume,row-1,"������",format1);
				Label label7 = new Label(clume+1,row-1,"�ϸ�",format1);
				Label label8 = new Label(clume+2,row-1,"���ϸ�",format1);
				Label label9 = new Label(clume+3,row-1,"�ϸ���",format1);
				sheet.addCell(label6);
				sheet.addCell(label7);
				sheet.addCell(label8);
				sheet.addCell(label9);
				for (String string : jx) {
					Tongji tongji=kd.getTongji(string, rq);
					Number label10 = new jxl.write.Number(clume, rowx, tongji.getzongji(),format1);
					Number label11 = new jxl.write.Number(clume+1, rowx, tongji.getHege(),format1);
					Number label12 = new jxl.write.Number(clume+2, rowx, tongji.getBuhege(),format1);
					String hegelvs=null;
					if(tongji.getHegelv()==0.0)
						hegelvs="0";
					else if (tongji.getHegelv()==1.0)
						hegelvs="100";
					else hegelvs=num.format(tongji.getHegelv()*100)+"";
					Label label13 = new  Label(clume+3, rowx,hegelvs+"%",format1);
					
					sheet.addCell(label10);
					sheet.addCell(label11);
					sheet.addCell(label12);
					sheet.addCell(label13);
					rowx++;
				}
				clume+=4;
				rowx=3;
			}
			int rowday=jx.size()+3;
			Label label16=new Label(0,rowday,"�ϼ�",format1);
			sheet.addCell(label16);
			int clumeday=1;
			for (String rq : rqs) {
				Tongji tongji=kd.getDayTongji(rq);
				Number label17 = new jxl.write.Number(clumeday, rowday, tongji.getzongji(),format1);
				Number label18 = new jxl.write.Number(clumeday+1, rowday, tongji.getHege(),format1);
				Number label19 = new jxl.write.Number(clumeday+2, rowday, tongji.getBuhege(),format1);
				Label label20= new  Label(clumeday+3, rowday,num.format(tongji.getHegelv()*100)+"%",format1);
				sheet.addCell(label17);
				sheet.addCell(label18);
				sheet.addCell(label19);
				sheet.addCell(label20);
				clumeday+=4;
			}
			int rowzhou=jx.size()+4;
			Label label21=new Label(0,rowzhou,"����ͳ��",format1);
			sheet.addCell(label21);
			sheet.mergeCells(1, rowzhou, 20,rowzhou);
			Tongji zhoutongji=kd.getzhouTongji(rqs);
			Label label22=new Label(1,rowzhou,"���ܿ�������"+zhoutongji.getzongji()+"�ˣ��ϸ�����"+zhoutongji.getHege()+"�ˣ����ϸ�����"+zhoutongji.getBuhege()+"�ˣ��ϸ���"+num.format(zhoutongji.getHegelv()*100)+"%��",format1);
			sheet.addCell(label22);
			
			int rowmonth=jx.size()+5;
			Label label23=new Label(0,rowmonth,"����ͳ��",format1);
			sheet.addCell(label23);
			sheet.mergeCells(1, rowmonth, 20,rowmonth);
			Tongji yuetongji=kd.getmonthTongji(rqs);
			Label label24=new Label(1,rowmonth,"���¿�������"+yuetongji.getzongji()+"�ˣ��ϸ�����"+yuetongji.getHege()+"�ˣ����ϸ�����"+yuetongji.getBuhege()+"�ˣ��ϸ���"+num.format(yuetongji.getHegelv()*100)+"%��",format1);
			sheet.addCell(label24);
			
			int rowyear=jx.size()+6;
			Label label25=new Label(0,rowyear,"����ͳ��",format1);
			sheet.addCell(label25);
			sheet.mergeCells(1, rowyear, 20,rowyear);
			Tongji yeartongji=kd.getyearTongji(rqs);
			Label label26=new Label(1,rowyear,"���꿼������"+yeartongji.getzongji()+"�ˣ��ϸ�����"+yeartongji.getHege()+"�ˣ����ϸ�����"+yeartongji.getBuhege()+"�ˣ��ϸ���"+num.format(yeartongji.getHegelv()*100)+"%��",format1);
			sheet.addCell(label26);
			
			book.write();
			book.close();
			 s="��������ͳ�Ƴɹ���";
			System.out.println("������У��ͳ�Ƴɹ�!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	public String jxzhoutongji(List<String> rqs) {
		KSDao kd=new KSDao();
		String s=null;
		try {
			
			WritableWorkbook book = Workbook.createWorkbook(new File("D:\\��Ŀ����Уͳ��\\"+rqs.get(0)+"��"+rqs.get(4)+"��ͳ���ܱ�.xls"));
			DecimalFormat num= new DecimalFormat("#.00");
			// ������Ϊ"��һҳ"�Ĺ���������0��ʾ���ǵ�һ
			WritableSheet sheet = book.createSheet("��һҳ", 0);
			WritableFont font1 = new WritableFont(
					WritableFont.createFont("����"), 14, WritableFont.NO_BOLD);
					font1.setColour(Colour.BLACK);
					WritableCellFormat format1 = new WritableCellFormat(font1);
					format1.setAlignment(jxl.format.Alignment.CENTRE);
					format1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
					format1.setBorder(Border.ALL, BorderLineStyle.THIN);
					WritableFont font2 = new WritableFont(
							WritableFont.createFont("����"), 16, WritableFont.NO_BOLD);
							font1.setColour(Colour.BLACK);
							WritableCellFormat format2 = new WritableCellFormat(font2);
							format2.setAlignment(jxl.format.Alignment.CENTRE);
							format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
							format2.setBorder(Border.ALL, BorderLineStyle.THIN);
			sheet.setColumnView(0, 16);
			sheet.setColumnView(1, 16);
			sheet.setColumnView(2, 16);
			sheet.setColumnView(3, 16);
			sheet.setColumnView(4, 16);
			sheet.mergeCells(0, 0, 4, 0);
			Label label1=new Label(0,0,rqs.get(0)+"��"+rqs.get(4)+"��������Ŀ��������Уͳ�ƣ��ܣ�",format2);
			Label label2=new Label(0,1,"��У",format1);
			Label label3=new Label(1,1,"������",format1);
			Label label4=new Label(2,1,"�ϸ�",format1);
			Label label5=new Label(3,1,"���ϸ�",format1);
			Label label6=new Label(4,1,"�ϸ���",format1);
			sheet.addCell(label1);
			sheet.addCell(label2);
			sheet.addCell(label3);
			sheet.addCell(label4);
			sheet.addCell(label5);
			sheet.addCell(label6);
			int i=2;
			List<Tongji> ztj=kd.getzhouJxTongji(rqs);
			
			for (Tongji tongji : ztj) {
				Label label7=new Label(0,i,tongji.getJx(),format1);
				Number label8 = new jxl.write.Number(1, i, tongji.getzongji(),format1);
				Number label9 = new jxl.write.Number(2, i, tongji.getHege(),format1);
				Number label10 = new jxl.write.Number(3, i, tongji.getBuhege(),format1);
				String hegelvs=null;
				if(tongji.getHegelv()==0.0)
					hegelvs="0";
				else if (tongji.getHegelv()==1.0)
					hegelvs="100";
				else hegelvs=num.format(tongji.getHegelv()*100)+"";
				Label label11=new Label(4,i,hegelvs+"%",format1);
				sheet.addCell(label7);
				sheet.addCell(label8);
				sheet.addCell(label9);
				sheet.addCell(label10);
				sheet.addCell(label11);
			   i++;
			}
			sheet.mergeCells(0, ztj.size()+2, 4, ztj.size()+2);	
			
			Tongji zhoutongji=kd.getzhouTongji(rqs);
			Label label12=new Label(0,ztj.size()+2,"���ܿ�������"+zhoutongji.getzongji()+"�ˣ��ϸ�"+zhoutongji.getHege()+"�ˣ����ϸ�"+zhoutongji.getBuhege()+"�ˣ��ϸ���"+num.format(zhoutongji.getHegelv()*100)+"%��",format1);
			sheet.addCell(label12);
			book.write();
			book.close();
				 s="�����ܼ�Уͳ�Ƴɹ���";
				System.out.println("������У��ͳ�Ƴɹ�!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
}
