package lab1;

import java.sql.*;

public class DB extends DataFrame {
    private Connection connectvar = null;
    private Statement stat = null;
    private ResultSet result = null;
    
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
            result=stat.executeQuery("select *  FROM Frame");
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select *  FROM Frame");
                Value typo = new SValue();
                String gt =rsmd.getColumnTypeName(i);
                if(gt=="INT" || gt=="TINYINT"|| gt=="SMALLINT"|| gt=="MEDIUMINT" || gt=="BIGINT"){
                    typo = new lab1.Integer();
                }
                if(gt=="FLOAT"){
                    typo = new lab1.Float();
                }
                if(gt=="DOUBLE"){
                    typo = new lab1.Double();
                }
                if(gt=="DATETIME"){
                    typo=new lab1.DateTime();
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
    public DB(DataFrame dftosave) {
    	super(dftosave);
        try {
            connect();
            stat = connectvar.createStatement();
            stat.executeUpdate("Drop table Frame");
            String command = "CREATE TABLE Frame (";
            
            for(int i=0;i<dftosave.toList().size()-1;i++){
                command=command+dftosave.toList().get(i).getName()+" ";
                if((dftosave.toList().get(i).getVType())instanceof lab1.Integer){
                    command = command+"INT, \n";
                }
                else if((dftosave.toList().get(i).getVType())instanceof lab1.Double){
                    command = command+"DOUBLE, \n";
                }
                else if((dftosave.toList().get(i).getVType())instanceof lab1.Float){
                    command = command+"FLOAT, \n";
                }
                else if((dftosave.toList().get(i).getVType())instanceof lab1.DateTime){
                    command = command+"DATETIME, \n";
                }
                else{
                    command = command+"VARCHAR(255), \n";
                }

            }
            command=command+dftosave.toList().get(dftosave.toList().size()-1).getName()+" ";
            if((dftosave.toList().get(dftosave.toList().size()-1).getVType())instanceof lab1.Integer){
                command = command+"INT ) \n";
            }
            else if((dftosave.toList().get(dftosave.toList().size()-1).getVType())instanceof lab1.Double){
                command = command+"DOUBLE) \n";
            }
            else if((dftosave.toList().get(dftosave.toList().size()-1).getVType())instanceof lab1.Float){
                command = command+"FLOAT )\n";
            }
            
            else if((dftosave.toList().get(dftosave.toList().size()-1).getVType())instanceof lab1.DateTime){
                command = command+"DATETIME) \n";
            }
            else{
                command = command+"VARCHAR(255) )\n";
            }
            stat.executeUpdate(command);
            String secondcommand;
            for(int i =0;i<dftosave.size();i++){
                 secondcommand="INSERT INTO Frame (" ;
                for(int j=0;j<dftosave.toList().size()-1;j++){
                    secondcommand=secondcommand+dftosave.toList().get(j).getName()+" , ";
                }
                secondcommand=secondcommand+dftosave.toList().get(dftosave.toList().size()-1).getName();
                secondcommand=secondcommand+") VALUES (";
                for(int j=0;j<dftosave.toList().size()-1;j++){
                    secondcommand = secondcommand + dftosave.toList().get(j).list.get(i) + ",";

                }
                secondcommand = secondcommand + dftosave.toList().get(dftosave.toList().size()-1).list.get(i);
                secondcommand=secondcommand+");";
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
                    typo = new lab1.Integer();
                }
                if(gt=="FLOAT"){
                    typo = new lab1.Float();
                }
                if(gt=="DOUBLE"){
                    typo = new lab1.Double();
                }
                
                if(gt=="DATETIME"){
                    typo=new lab1.DateTime();
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
        DataFrame returnframe =null;
        try{
            connect();
            stat= connectvar.createStatement();


            result=stat.executeQuery("select *  FROM Frame");
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select MIN("+rsmd.getColumnName(i)+")  FROM Frame");
                Value typo = new lab1.Integer();
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
        DataFrame returnframe =null;
        try{
            connect();
            stat= connectvar.createStatement();


            result=stat.executeQuery("select *  FROM Frame");
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select MAX("+rsmd.getColumnName(i)+")  FROM Frame");
                Value typo = new lab1.Integer();
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
    
    public DataFrame groupby(String colname){
        DataFrame returnframe =null;
        try{
            connect();
            stat= connectvar.createStatement();


            result=stat.executeQuery("select *  FROM Frame WHERE GROUP BY"+colname);
            ResultSetMetaData rsmd = result.getMetaData();
            int numberofcols= rsmd.getColumnCount();
            Column[] cols = new Column[numberofcols];
            for(int i=1;i<=numberofcols;i++) {
                result=stat.executeQuery("select *  FROM Frame WHERE GROUP BY"+colname);
                Value typo = new SValue();
                String gt =rsmd.getColumnTypeName(i);
                if(gt=="INT" || gt=="TINYINT"|| gt=="SMALLINT"|| gt=="MEDIUMINT" || gt=="BIGINT"){
                    typo = new lab1.Integer();
                }
                if(gt=="FLOAT"){
                    typo = new lab1.Float();
                }
                if(gt=="DOUBLE"){
                    typo = new lab1.Double();
                }
              
                if(gt=="DATETIME"){
                    typo=new lab1.DateTime();
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
}