import java.util.ArrayList;

public class OSProjectServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SplitFileThread T1 = new SplitFileThread("File Split Thread");
		T1.start();

	}

}
