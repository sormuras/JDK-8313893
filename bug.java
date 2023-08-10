import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

class bug {
  public static void main(String... args) throws Exception {
    System.out.println("Java " + Runtime.version());

    var errors = Path.of("errors-bug.txt");
    var output = Path.of("output-bug.txt");

    var builder = new ProcessBuilder();
    builder.command().add("java"); // java -cp picocli-4.7.4.jar ASCIIArt.java
    builder.command().add("-cp");
    builder.command().add("picocli-4.7.4.jar");
    builder.command().add("ASCIIArt.java");
    builder.command().add("--help");
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
