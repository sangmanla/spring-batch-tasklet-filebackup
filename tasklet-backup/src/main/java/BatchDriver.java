import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BatchDriver {
	ApplicationContext context;
	public static void main(String[] args) {
		new BatchDriver().execute();
	}
	
	private void execute(){
		String[] configFiles = {"batch-config.xml"};
		context = new ClassPathXmlApplicationContext(configFiles);
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job myJob = (Job) context.getBean("myJob");
		
		try{
			JobExecution jobExecution = launcher.run(myJob, new JobParameters());
			System.out.println("batch result : " + jobExecution.getStatus());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}