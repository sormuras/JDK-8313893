import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

class version {
  public static void main(String... args) throws Exception {
    System.out.println("Java " + Runtime.version());

    var errors = Path.of("errors.txt");
    var output = Path.of("output.txt");

    var builder = new ProcessBuilder();
    builder.command().add("java");
    builder.command().add("--version");
    builder.redirectError(errors.toFile());
    builder.redirectOutput(output.toFile());

    var process = builder.start();
    var timely = process.waitFor(5, TimeUnit.SECONDS);

    System.err.println("=== ERRORS ===");
    Files.readAllLines(errors).forEach(System.err::println);
    System.err.println("=== OUTPUT ===");
    Files.readAllLines(output).forEach(System.out::println);

    System.exit(timely ? process.exitValue() : -1);
  }
}
