import java.io.File;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class BackupTasklet implements InitializingBean, Tasklet {
	private Resource directory;
	
	
	
	public Resource getDirectory() {
		return directory;
	}

	public void setDirectory(Resource directory) {
		this.directory = directory;
	}
	
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(directory, "directory cannot be null");
	}

	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		File dir = directory.getFile();
		Assert.state(dir.isDirectory(), "given path is not directory");
		
		File backupDir = new File(dir.getPath() + File.separator + "backup");
		if(!backupDir.exists()) backupDir.mkdirs();
		
		for(File file : dir.listFiles()){
			boolean result = file.renameTo(new File(backupDir.getPath() + File.separator + file.getName()));
			if(result) System.out.println(file.getName() + " moved successfully");
			else System.out.println(file.getName() + " failed to move");
		}
		
		System.out.println("complete");
		return RepeatStatus.FINISHED;
	}

	

}
