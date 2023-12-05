package space.techsmart.mqttprovider.backend.engine;



import space.techsmart.mqttprovider.backend.utils.Param;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class IdController {
	private static int id_default = 1;
    private static AtomicInteger auid = new AtomicInteger(0);
    public static void setAtomicInteger(){
    	auid.set(Param.uid);
    }
	public static int getId(String type) {
		int id = id_default++ ;
		return id;
	}
	public static void resetIds(){
		id_default=0;
	}
	
	private int id;
	public void setId(){
		this.id =1;
	}
	public int getId(){
		return id++;
	}
	
	public static int getUid() {
		Param.uid = auid.incrementAndGet();
		return Param.uid;
	}

	static public void resetUid(int count) throws IOException {
		PrintWriter pw = new PrintWriter("id.txt");
		pw.close();
		File arquivo = new File("id.txt");
		arquivo = new File("id.txt");
		try (FileWriter fw = new FileWriter(arquivo, true); BufferedWriter bw = new BufferedWriter(fw)) {
			bw.write(String.valueOf(1));
		}
	}

}
