package ru.alexeymalinov.taskautomation.core.services.clirobotservice;

import ru.alexeymalinov.taskautomation.core.services.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.BiFunction;

public enum CliOperation implements Operation<Runtime> {
    EXECUTE(CliOperation::execute),
    EXECUTE_SCRIPT(CliOperation::executeScript);

    private final BiFunction<Runtime,String,String> action;

    CliOperation(BiFunction<Runtime, String, String> action) {
        this.action = action;
    }

    @Override
    public String apply(Runtime runtime, String s) {
        return action.apply(runtime, s);
    }

    private static String execute(Runtime runtime, String s){
        Process process = null;
        try {
            process = runtime.exec(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            return read(process);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "";
    }

    private static String executeScript(Runtime runtime, String s){
        return execute(runtime, s);
    }

    private static String read(Process process) throws IOException {
        return read(process.getInputStream()) +
                read(process.getErrorStream());
    }

    private static String read(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return this.name();
    }
}
