package jken.module.scheduler.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NativeJob implements Job {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /*
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Constants.
     *
     *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
     * Required parameter that specifies the name of the command (executable)
     * to be ran.
     */
    public static final String PROP_COMMAND = "command";

    /**
     * Optional parameter that specifies the parameters to be passed to the
     * executed command.
     */
    public static final String PROP_PARAMETERS = "parameters";


    /**
     * Optional parameter (value should be 'true' or 'false') that specifies
     * whether the job should wait for the execution of the native process to
     * complete before it completes.
     *
     * <p>Defaults to <code>true</code>.</p>
     */
    public static final String PROP_WAIT_FOR_PROCESS = "waitForProcess";

    /**
     * Optional parameter (value should be 'true' or 'false') that specifies
     * whether the spawned process's stdout and stderr streams should be
     * consumed.  If the process creates output, it is possible that it might
     * 'hang' if the streams are not consumed.
     *
     * <p>Defaults to <code>false</code>.</p>
     */
    public static final String PROP_CONSUME_STREAMS = "consumeStreams";


    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     *
     * Interface.
     *
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        JobDataMap data = context.getMergedJobDataMap();

        String command = data.getString(PROP_COMMAND);

        String parameters = data.getString(PROP_PARAMETERS);

        if (parameters == null) {
            parameters = "";
        }

        boolean wait = true;
        if (data.containsKey(PROP_WAIT_FOR_PROCESS)) {
            wait = data.getBooleanValue(PROP_WAIT_FOR_PROCESS);
        }
        boolean consumeStreams = false;
        if (data.containsKey(PROP_CONSUME_STREAMS)) {
            consumeStreams = data.getBooleanValue(PROP_CONSUME_STREAMS);
        }

        Integer exitCode = 0; //this.runNativeCommand(command, parameters, wait, consumeStreams);
        context.setResult(exitCode);

    }

    protected Logger getLog() {
        return log;
    }

    private Integer runNativeCommand(String command, String parameters, boolean wait, boolean consumeStreams) throws JobExecutionException {

        String[] cmd;
        String[] args = new String[2];
        Integer result = null;
        args[0] = command;
        args[1] = parameters;


        try {
            //with this variable will be done the swithcing
            String osName = System.getProperty("os.name");

            // specific for Windows
            if (osName.startsWith("Windows")) {
                cmd = new String[args.length + 2];
                if (osName.equals("Windows 95")) { // windows 95 only
                    cmd[0] = "command.com";
                } else {
                    cmd[0] = "cmd.exe";
                }
                cmd[1] = "/C";
                System.arraycopy(args, 0, cmd, 2, args.length);
            } else if (osName.equals("Linux")) {
                cmd = new String[3];
                cmd[0] = "/bin/sh";
                cmd[1] = "-c";
                cmd[2] = args[0] + " " + args[1];
            } else { // try this...
                cmd = args;
            }

            Runtime rt = Runtime.getRuntime();
            // Executes the command
            getLog().info("About to run " + cmd[0] + " " + cmd[1] + " " + (cmd.length > 2 ? cmd[2] : "") + " ...");
            Process proc = rt.exec(cmd);
            // Consumes the stdout from the process
            StreamConsumer stdoutConsumer = new StreamConsumer(proc.getInputStream(), "stdout");

            // Consumes the stderr from the process
            if (consumeStreams) {
                StreamConsumer stderrConsumer = new StreamConsumer(proc.getErrorStream(), "stderr");
                stdoutConsumer.start();
                stderrConsumer.start();
            }

            if (wait) {
                result = proc.waitFor();
            }
            // any error message?

        } catch (Throwable x) {
            throw new JobExecutionException("Error launching native command: ", x, false);
        }

        return result;
    }

    /**
     * Consumes data from the given input stream until EOF and prints the data to stdout
     *
     * @author cooste
     * @author jhouse
     */
    class StreamConsumer extends Thread {
        InputStream is;
        String type;

        /**
         *
         */
        public StreamConsumer(InputStream inputStream, String type) {
            this.is = inputStream;
            this.type = type;
        }

        /**
         * Runs this object as a separate thread, printing the contents of the InputStream
         * supplied during instantiation, to either stdout or stderr
         */
        @Override
        public void run() {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(is));
                String line;

                while ((line = br.readLine()) != null) {
                    if (type.equalsIgnoreCase("stderr")) {
                        getLog().warn(type + ">" + line);
                    } else {
                        getLog().info(type + ">" + line);
                    }
                }
            } catch (IOException ioe) {
                getLog().error("Error consuming " + type + " stream of spawned process.", ioe);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception ignore) {
                    }
                }
            }
        }
    }

}
