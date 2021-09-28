package Utils;

import java.io.PrintStream;
import java.util.TreeSet;

public class CustomPrintStream extends PrintStream {

    private final TreeSet<String> outList = new TreeSet<>();

    public CustomPrintStream(){
        super(System.out, true);
    }

    public TreeSet<String> getList() {
        return outList;
    }

    @Override
    public void println(String s) {
        outList.add(s);
    }

}
