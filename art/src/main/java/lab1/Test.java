package lab1;

import java.util.Random;

public class Test {
    public static void main(String[] args){
        DataFrame df = new  DataFrame(new String[]{"kol1","kol2","kol3"},
                new String[]{"int","double","MyCustomType"});
        Value[] n = new Value[3];
        for(int i =0;i<3;i++){
            n[i]=new Integer(i);
        }
        Value[] s = new Value[3];
        for(int i =0;i<3;i++){
            s[i]=new Integer(i+10);
        }
        Value[] j = new Value[3];
        for(int i =0;i<3;i++){
            j[i]=new Integer(i+20);
        }
        
        Value[] l = new Value[3];
        for(int i =0;i<3;i++){
        	Random rand = new Random();
        	int  k = rand.nextInt(50) + 1;
            l[i]=new Integer(k);
        }
        Value[] l1 = new Value[3];
        for(int i =0;i<3;i++){
        	Random rand = new Random();
        	int  k = rand.nextInt(50) + 1;
            l1[i]=new Integer(k);
        }
        Value[] l2 = new Value[3];
        for(int i =0;i<3;i++){
        	Random rand = new Random();
        	int  k = rand.nextInt(50) + 1;
            l2[i]=new Integer(k);
        }
    	String[] typy = {"Double","Double","Double"};
    	boolean header = true;
        try {
			DataFrame plik = new DataFrame("C:\\Users\\darkb\\Documents\\data.csv",typy,header);
			
			System.out.println("1max");
	        plik.max().print();
	        System.out.println("1min");
	        plik.min().print();
	        System.out.println("1mean");
	        plik.mean().print();
	        System.out.println("1var");
	        plik.var().print();
	        System.out.println("1sum");
	        plik.sum().print();
	        System.out.println("1std");
	        plik.std().print();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        
        df.add(j);
        df.add(s);
        df.add(l);
        df.add(l1);
        df.add(l2);
        df.add(n);
        df.print();
        System.out.println("max");
        df.max().print();
        System.out.println("min");
        df.min().print();
        System.out.println("mean");
        df.mean().print();
        System.out.println("var");
        df.var().print();
        System.out.println("sum");
        df.sum().print();
        System.out.println("std");
        df.std().print();

        }



    }