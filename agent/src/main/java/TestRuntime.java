import java.io.*;
import java.util.Scanner;

import static java.lang.Runtime.getRuntime;

public class TestRuntime {
    private static Runtime runtime;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command:");
            String command = scanner.nextLine();

            if ("exit".equals(command)) {
                break;
            }

            if ("execute".equals(command)) {
                execute(scanner);
            }

        }
    }

    public static void execute(Scanner scanner) throws IOException {
        runtime = Runtime.getRuntime();
        Process cmdProcess = runtime.exec("cmd.exe");
        try (Writer writer = new OutputStreamWriter(cmdProcess.getOutputStream());
        Reader reader = new InputStreamReader(cmdProcess.getInputStream());
        Reader errorReader = new InputStreamReader(cmdProcess.getErrorStream())) {
            Thread in = new Thread(new ConsoleIn(reader));
            in.start();
            Thread error = new Thread(new ConsoleIn(errorReader));
            error.start();
            while (true) {
                String command = scanner.nextLine();

                if ("q".equals(command)) break;

                writer.write(command);
                writer.write(System.lineSeparator());
                writer.flush();

            }
            in.interrupt();
            error.interrupt();
            cmdProcess.destroy();
        }
    }



    /**
     * Запускает скрипт
     *
     * @param scriptPath - путь до файла скрипта
     * @return результат выполнения скрипта ввиде объекта типа {@code String}
     * @throws IOException
     */
    public static String executeScript(String scriptPath) throws IOException {
        runtime = getRuntime();
        Process process = runtime.exec(scriptPath);
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();

    }
}
