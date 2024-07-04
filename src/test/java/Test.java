import nl.harmvdhorst.econfig.EConfig;

import java.io.File;

public class Test {

    public static void main(String[] args) {

        File file = new File("example.ec");

        EConfig config = new EConfig(file);

        System.out.println(config.getInt("category.test.jema.key"));;
        System.out.println(config.getInt("category.test.key"));;
        System.out.println(config.getString("test-2"));;

        //System.out.println("key-3: ]".contains("]"));

    }

}
