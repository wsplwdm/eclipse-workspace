package lab1;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import valueTypes.SValue;
import valueTypes.Value;

public class DB extends DataFrame {
    private Connection connectvar = null;
    private Statement stat = null;
    private ResultSet result = null;
   // private String[] dblist;
    public String dbname;
    
    public void connect(){
        try{
           connectvar = DriverManager.getConnection("jdbc:mysql://mysql.agh.edu.pl/margansk","margansk","pAuhC1rjphRvK86h");
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    public DataFrame getDataFrame(){
        DataFrame returnframe =null;
        try{
            connect();
            stat= connectvar.createStatement();
            result=stat.executeQuery("select *  FROM "+dbname);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select *  FROM "+dbname);
                Value typo = new SValue();
                String gt =rsmd.getColumnTypeName(i);
                if(gt=="INT" || gt=="TINYINT"|| gt=="SMALLINT"|| gt=="MEDIUMINT" || gt=="BIGINT"){
                    typo = new valueTypes.Integer();
                }
                if(gt=="FLOAT"){
                    typo = new valueTypes.Float();
                }
                if(gt=="DOUBLE"){
                    typo = new valueTypes.Double();
                }
                if(gt=="DATETIME"){
                    typo=new valueTypes.DateTime();
                }
                cols[i-1]=new Column(rsmd.getColumnName(i),typo);
                while (result.next()) {

                    cols[i-1].addElement(new SValue(result.getString(i)));
                }
            }
             returnframe =new DataFrame(cols);
        }catch(java.sql.SQLException e){
            System.out.println("SQL exception "+e.getMessage());
            
        }finally {
            if(result!=null) {
                try {
                    result.close();
                } catch(SQLException e) {}
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch(SQLException e) {}
            }


        }
        return returnframe ;
    }
    /*
    public DB(String path) throws IOException {
    	super(path);
    	try {
    	dbname = fxcontroller.StackPaneController.nazwapliku;
    	 connect();
         stat = connectvar.createStatement();
         //stat.executeUpdate("Drop table 1groupby");
         String command ="LOAD DATA INFILE \""+path+"\"\r\n" + 
         		"INTO TABLE "+dbname+"\r\n" + 
         		"COLUMNS TERMINATED BY ','\r\n" + 
         		"OPTIONALLY ENCLOSED BY '\"'\r\n" + 
         		"ESCAPED BY '\"'\r\n" + 
         		"LINES TERMINATED BY '\\n'\r\n" + 
         		"IGNORE 1 LINES;";
         System.out.println(command);
         stat.executeUpdate(command);
    	 }catch (java.sql.SQLException e){
             System.out.println("SQL exception "+e.getMessage());
             System.out.println("SQL state "+e.getSQLState());
             System.out.println("SQL vendor error "+e.getErrorCode());
         }
    }*/
    public DB(String name){
    	super();
    	this.dbname=name;
    	
    }
    public DB(DataFrame dftosave) {
    	super(dftosave);
    	
    	  try {
          	dbname = fxcontroller.StackPaneController.nazwapliku;
              connect();
              stat = connectvar.createStatement();
              stat.executeUpdate("Drop table 1groupby");
              //System.out.println(dbname);
              String command = "CREATE TABLE "+dbname+" (";
              
              for(int i=0;i<dftosave.getListOfColumns().size()-1;i++){
                  command=command+dftosave.getListOfColumns().get(i).getName()+" ";
                  if((dftosave.getListOfColumns().get(i).getVType())instanceof valueTypes.Integer){
                      command = command+"INT, \n";
                  }
                  else if((dftosave.getListOfColumns().get(i).getVType())instanceof valueTypes.Double){
                      command = command+"DOUBLE, \n";
                  }
                  else if((dftosave.getListOfColumns().get(i).getVType())instanceof valueTypes.Float){
                      command = command+"FLOAT, \n";
                  }
                  else if((dftosave.getListOfColumns().get(i).getVType())instanceof valueTypes.DateTime){
                      command = command+"DATETIME, \n";
                  }
                  else{
                      command = command+"VARCHAR(255), \n";
                  }

              }
              
              command=command+dftosave.getListOfColumns().get(dftosave.getListOfColumns().size()-1).getName()+" ";
              if((dftosave.getListOfColumns().get(dftosave.getListOfColumns().size()-1).getVType())instanceof valueTypes.Integer){
                  command = command+"INT ) \n";
              }
              else if((dftosave.getListOfColumns().get(dftosave.getListOfColumns().size()-1).getVType())instanceof valueTypes.Double){
                  command = command+"DOUBLE) \n";
              }
              else if((dftosave.getListOfColumns().get(dftosave.getListOfColumns().size()-1).getVType())instanceof valueTypes.Float){
                  command = command+"FLOAT )\n";
              }
              
              else if((dftosave.getListOfColumns().get(dftosave.getListOfColumns().size()-1).getVType())instanceof valueTypes.DateTime){
                  command = command+"DATETIME) \n";
              }
              else{
                  command = command+"VARCHAR(255) )\n";
              }
              System.out.println(command);
              stat.executeUpdate(command);
              String secondcommand;
              int ile=10000;
              
            	  for(int k =0;k<dftosave.size();k+=ile) {
            		  System.out.println(k);
                   secondcommand="INSERT INTO "+dbname+" (" ;
                  for(int j=0;j<dftosave.getListOfColumns().size()-1;j++){
                      secondcommand=secondcommand+dftosave.getListOfColumns().get(j).getName()+" , ";
                  }
                  secondcommand=secondcommand+dftosave.getListOfColumns().get(dftosave.getListOfColumns().size()-1).getName();
                  secondcommand=secondcommand+") VALUES";
                  for(int i =k;(i<k+ile && i<dftosave.size());i++){
                	  secondcommand+="('";
                	  for(int j=0;j<dftosave.getListOfColumns().size()-1;j++){
	                      secondcommand = secondcommand + dftosave.get(j).get(i) + "','";

                  }
                  secondcommand = secondcommand + dftosave.getListOfColumns().get(dftosave.getNumberOfCOlumns()-1).getListOfValues().get(i);
                  secondcommand=secondcommand+"'),";
                 
              }
                  secondcommand=secondcommand.substring(0,secondcommand.length()-1);
                  secondcommand=secondcommand+";";
                  //System.out.println(secondcommand);
                  stat.executeUpdate(secondcommand);
            	  }
              
          }catch (java.sql.SQLException e){
              System.out.println("SQL exception "+e.getMessage());
              System.out.println("SQL state "+e.getSQLState());
              System.out.println("SQL vendor error "+e.getErrorCode());
          }
      }
    
    public DataFrame getDataFrameFrom(String where){
        DataFrame returnframe =null;
        try{
            connect();
            stat= connectvar.createStatement();
            result=stat.executeQuery(where);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery(where);
                Value typo = new SValue();
                String gt =rsmd.getColumnTypeName(i);
                if(gt=="INT" || gt=="TINYINT"|| gt=="SMALLINT"|| gt=="MEDIUMINT" || gt=="BIGINT"){
                    typo = new valueTypes.Integer();
                }
                if(gt=="FLOAT"){
                    typo = new valueTypes.Float();
                }
                if(gt=="DOUBLE"){
                    typo = new valueTypes.Double();
                }
                
                if(gt=="DATETIME"){
                    typo=new valueTypes.DateTime();
                }
                cols[i-1]=new Column(rsmd.getColumnName(i),typo);
                while (result.next()) {

                    cols[i-1].addElement(new SValue(result.getString(i)));
                }
            }
            returnframe =new DataFrame(cols);
        }catch(java.sql.SQLException e){
            System.out.println("SQL exception "+e.getMessage());
            System.out.println("SQL state "+e.getSQLState());
            System.out.println("SQL vendor error "+e.getErrorCode());
        }finally {
            if(result!=null) {
                try {
                    result.close();
                } catch(SQLException e) {}
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch(SQLException e) {}
            }


        }
        return returnframe ;
    }
    
    public DataFrame min(){
    	System.out.println("min DB");
        DataFrame returnframe =null;
        try{
            connect();
            stat= connectvar.createStatement();


            result=stat.executeQuery("select *  FROM "+dbname);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select MIN("+rsmd.getColumnName(i)+")  FROM "+dbname);
                Value typo = new valueTypes.Integer();
                cols[i-1]=new Column(rsmd.getColumnName(i),typo);
                result.next();
                cols[i-1].addElement(new SValue(result.getString(1)));
            }
            returnframe =new DataFrame(cols);
        }catch(java.sql.SQLException e){
            System.out.println("SQL exception "+e.getMessage());
            System.out.println("SQL state "+e.getSQLState());
            System.out.println("SQL vendor error "+e.getErrorCode());
        }finally {
            if(result!=null) {
                try {
                    result.close();
                } catch(SQLException e) {}
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch(SQLException e) {}
            }


        }
        return returnframe ;
    }
    
    public DataFrame max(){
    	System.out.println("max DB");
        DataFrame returnframe =null;
        try{
            connect();
            stat= connectvar.createStatement();


            result=stat.executeQuery("select *  FROM "+dbname);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select MAX("+rsmd.getColumnName(i)+")  FROM "+dbname);
                Value typo = new valueTypes.Integer();
                cols[i-1]=new Column(rsmd.getColumnName(i),typo);
                result.next();
                cols[i-1].addElement(new SValue(result.getString(1)));
            }
            returnframe =new DataFrame(cols);
        }catch(java.sql.SQLException e){
            System.out.println("SQL exception "+e.getMessage());
            System.out.println("SQL state "+e.getSQLState());
            System.out.println("SQL vendor error "+e.getErrorCode());
        }finally {
            if(result!=null) {
                try {
                    result.close();
                } catch(SQLException e) {}
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch(SQLException e) {}
            }


        }
        return returnframe ;
    }
    
    public ArrayList<DataFrame> groupby(String colname){
        ArrayList<DataFrame> returnframe = new ArrayList<DataFrame>();
        
        System.out.println("groupby DB");
        try{
            connect();
            stat= connectvar.createStatement();

            stat.executeQuery("SET OPTION SQL_SELECT_LIMIT=DEFAULT");
            System.out.println("select *  FROM "+dbname+"  GROUP BY "+colname+";");
            result=stat.executeQuery("select *  FROM "+dbname+"  GROUP BY "+colname+";");
            System.out.println("select *  FROM "+dbname+"  GROUP BY "+colname+";");
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select *  FROM "+dbname+"  GROUP BY "+colname+";");
                //System.out.println("select *  FROM "+dbname+"  GROUP BY "+colname+";");
                Value typo = new SValue();
                String gt =rsmd.getColumnTypeName(i);
                if(gt=="INT" || gt=="TINYINT"|| gt=="SMALLINT"|| gt=="MEDIUMINT" || gt=="BIGINT"){
                    typo = new valueTypes.Integer();
                }
                if(gt=="FLOAT"){
                    typo = new valueTypes.Float();
                }
                if(gt=="DOUBLE"){
                    typo = new valueTypes.Double();
                }
              
                if(gt=="DATETIME"){
                    typo=new valueTypes.DateTime();
                }
                cols[i-1]=new Column(rsmd.getColumnName(i),typo);
                while (result.next()) {

                    cols[i-1].addElement(new SValue(result.getString(i)));


                }

            }
            
            returnframe.add(new DataFrame(cols));
        }catch(java.sql.SQLException e){
            System.out.println("SQL exception "+e.getMessage());
            System.out.println("SQL state "+e.getSQLState());
            System.out.println("SQL vendor error "+e.getErrorCode());
        }finally {
            if(result!=null) {
                try {
                    result.close();
                } catch(SQLException e) {}
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch(SQLException e) {}
            }


        }
        return returnframe ;
    }
}