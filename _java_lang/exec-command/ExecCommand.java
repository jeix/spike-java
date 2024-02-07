
// @see https://www.baeldung.com/run-shell-command-in-java

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.concurrent.Executors;

public class ExecCommand {

	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("java ExecCommand 1|2|3|4|4a");
			return;
		}
		String mode = args[0];

		System.out.println("os.name=" + System.getProperty("os.name"));
		System.out.println("user.home=" + System.getProperty("user.home"));

		boolean isLinux = System.getProperty("os.name").toLowerCase().startsWith("linux");
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		String homeDirectory = System.getProperty("user.home");
		String commandDirectory = homeDirectory + "/ws/java/exec-command/command";

		if ("1".equals(mode)) {
			System.out.println("====================");
			try {
				Process process;
				if (isWindows) {
					process = Runtime.getRuntime().exec(String.format("cmd.exe /c dir %s", homeDirectory)); // IOException
				} else {
					//process = Runtime.getRuntime().exec(String.format("sh -c ls %s", homeDirectory)); // IOException // directory not works
					process = Runtime.getRuntime().exec("sh -c ls", null, new File(homeDirectory)); // IOException // directory works
				}
				
				StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
				Executors.newSingleThreadExecutor().submit(streamGobbler);
				
				int exitCode = process.waitFor(); // InterruptedException
				assert exitCode == 0;
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		if ("2".equals(mode)) {
			System.out.println("====================");
			List<String> commands = new ArrayList<>();
			String[] cmdarr = new String[] {};
			try {
				ProcessBuilder builder = new ProcessBuilder();
				if (isWindows) {
					//builder.command("cmd.exe", "/c", "dir");
					commands.add("cmd.exe");
					commands.add("/c");
					commands.add("dir");
					builder.command(commands.toArray(cmdarr));
				} else {
					//builder.command("sh", "-c", "ls");
					commands.add("sh");
					commands.add("-c");
					commands.add("ls");
					builder.command(commands.toArray(cmdarr));
				}
				builder.directory(new File(homeDirectory));
				Process process = builder.start(); // IOException

				StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
				Executors.newSingleThreadExecutor().submit(streamGobbler);
				
				int exitCode = process.waitFor(); // InterruptedException
				assert exitCode == 0;
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		if ("3".equals(mode) && isLinux) {
			System.out.println("====================");
			List<String> envs = new ArrayList<>();
			envs.add("ENV_FOO=42");
			envs.add("ENV_BAR=foo");
			String[] envp = new String[] {};
			try {
				Process process = Runtime.getRuntime().exec("python3 exec.py", envs.toArray(envp), new File(commandDirectory)); // IOException
				
				new BufferedReader(new InputStreamReader(process.getInputStream())).lines().forEach(System.out::println);
				
				int exitCode = process.waitFor(); // InterruptedException
				assert exitCode == 0;

				System.out.println("THE END");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		if (("4".equals(mode) || "4a".equals(mode)) && isLinux) {
			System.out.println("====================");
			List<String> commands = new ArrayList<>();
			commands.add("python3");
			commands.add("exec.py");
			String[] cmdarr = new String[] {};

			try {
				ProcessBuilder builder = new ProcessBuilder();
				
				Map<String,String> env = builder.environment();
				env.put("ENV_FOO", "123");
				env.put("ENV_BAR", "xyz");
				
				builder.command(commands.toArray(cmdarr));
				
				builder.directory(new File(commandDirectory));
				
				if ("4a".equals(mode)) {
					builder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("log")));
				}
				
				Process process = builder.start(); // IOException
				
				if ("4".equals(mode)) {
					new BufferedReader(new InputStreamReader(process.getInputStream())).lines().forEach(System.out::println);
				}
				
				int exitCode = process.waitFor(); // InterruptedException
				assert exitCode == 0;

				System.out.println("THE END");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		if ("x".equals(mode)) {
			System.out.println("====================");
			String copyBaseDirectory = homeDirectory + "/tmp/zoo";
			String accessKey = "0081CD5A62E09595C2C9";
			String secretkey = "88C97BCAD3A928725D33C4308694F0B19C5B9682";
			String endpointUrl = "kr.object.ncloudstorage.com";
			String bucket = "prca-amc-ct";
			String command = String.format("s3cmd --access_key=%s --secret_key=%s --host=%s --host-bucket=%s.%s sync s3://%s %s",
					accessKey, secretkey, endpointUrl, bucket, endpointUrl, bucket, bucket
			);
			try {
				Process process = Runtime.getRuntime().exec(command, null, new File(copyBaseDirectory));
				
				new BufferedReader(new InputStreamReader(process.getInputStream())).lines().forEach(System.out::println);

				int exitCode = process.waitFor(); // InterruptedException
				assert exitCode == 0;

				System.out.println("THE END");
			} catch (IOException ioe) {
				ioe.printStackTrace();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	private static class StreamGobbler implements Runnable {

		private InputStream inputStream;
		private Consumer<String> consumer;
	 
		public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}
	 
		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
		}
	}
}
