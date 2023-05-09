package hexlet.code;

import java.util.concurrent.Callable;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(
    name = "gendiff",
    mixinStandardHelpOptions = true,
    version = "1.0.3",
    description = "Compares two configuration files and shows a difference."
)
public class Main implements Callable<Integer> {

    @Option(names = {"-f",
        "--format"}, paramLabel = "format", description = "output format [default: stylish]")
    String format = "stylish";

    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    String filePathOne;

    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    String filePathTwo;

    @Override
    public Integer call() {
        String differResult = Differ.generate(filePathOne, filePathTwo, format);
        System.out.println(differResult);

        return 0;
    }

    public static void main(String[] args) {
        Main main = new Main();

        int exitCode = new CommandLine(main).execute(args);

        System.exit(exitCode);
    }

}
