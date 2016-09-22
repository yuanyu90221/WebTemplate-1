package util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GenerateMyBatisCode {
	private final String genCfg = System.getProperty("user.dir") + "\\mybatis-gen-config\\mybatis_generator_config.xml"; 
	
	private boolean overwrite = true;
	
	private void go() throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<String>();  

		ConfigurationParser cp = new ConfigurationParser(warnings);

        Configuration config = null;  
    	File configFile = new File(genCfg);
        config = cp.parseConfiguration(configFile);
        
//        System.out.println(config.toDocument().getFormattedContent());

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(new MyProgressCallBack());
        
        if (warnings.size() != 0) {
        	System.err.println("~~~~~~ Generate failed ~~~~~~");
        	System.err.println("Error Messages:");
        	warnings.forEach(System.out::println);
        }
        else {
        	System.out.println("------ Generate succeed ------");
        }
	}
	
	private class MyProgressCallBack implements ProgressCallback {

		@Override
		public void introspectionStarted(int totalTasks) {
		}

		@Override
		public void generationStarted(int totalTasks) {
			System.out.println("------ Generation started ------");
		}

		@Override
		public void saveStarted(int totalTasks) {
		}

		@Override
		public void startTask(String taskName) {
		}

		@Override
		public void done() {
			System.out.println("------ DONE ------");
		}

		@Override
		public void checkCancel() throws InterruptedException {
		}
	}

	public static void main(String[] args) {
		try {
			new GenerateMyBatisCode().go();
		} catch (IOException | XMLParserException | InvalidConfigurationException | SQLException | InterruptedException e) {
			System.err.println("~~~~~~ Generate failed, msg: <" + e.getMessage() + ">");
		}
	}
}
