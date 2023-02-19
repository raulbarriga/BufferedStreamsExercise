//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public final class MakeShards {
//    private static final int SHARD_SIZE = 100;
//    //static Path inputFilePath = Path.of("unsorted.txt");
//    //static Path outputDirPath = Path.of("shards");
//
//    static String[] arr = {"unsorted.txt", "shards"};
//    //throws Exception
//    public static void main(String[] args) throws IOException {
//        if (args.length != 2) {
//            System.out.println("Usage: MakeShards [input file] [output folder]");
//            return;
//        }
//
//        Path input = Path.of(args[0]);
//        Path outputFolder = Files.createDirectory(Path.of(args[1]));
//
//
//        Path outputDirPath = Path.of(args[1]);
//        //Path outputFolder = null;
//
//        // delete the folder if it already exists
//        if (Files.exists(outputDirPath)) {
//            try {
//                Files.walk(outputDirPath)
//                        .sorted((path1, path2) -> -path1.compareTo(path2))
//                        .forEach(MakeShards::deleteFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//        } else {
//            try {
//                outputFolder = Files.createDirectory(Path.of(args[1]));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (!Files.exists(outputDirPath)) {
//            try {
//                Files.createDirectory(outputDirPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return;
//            }
//        }
//
//        // TODO: Read the unsortesto many shard files.
//        //  Each shard file should contain at most SHARD_SIZE words, in sorted order.
//        //  All the words should be accounted for in the output shard files; you should not skip any words.
//        //  Write the shard files in the newly created "outputFolder",
//        //  using the getOutputFileName(int) method to name the individual shard files.
//
//        //Path filePath = Paths.get("unsorted.txt");
//        //input
//        if(Files.exists(input.toAbsolutePath())) {
//            System.out.println("file exists");
//        } else {
//            System.out.println("file not found");
//            System.out.println(input.toAbsolutePath());
//        }
//        try (BufferedReader reader = Files.newBufferedReader(input)) {
//            String line = reader.readLine();
//            while (line != null) {
//                String[] words = line.split("\\s+");
//                List<String> wordBatch = new ArrayList<>();
//                int batchCount = 1;
//
//                for (String word : words) {
//                    wordBatch.add(word);
//                    if (wordBatch.size() == SHARD_SIZE) {
//                        Collections.sort(wordBatch);
//                        Path outputFilePath = outputFolder.resolve(getOutputFileName(batchCount));
//                        try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
//                            for (String sortedWord : wordBatch) {
//                                writer.write(sortedWord);
//                                writer.write("\n");
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        wordBatch.clear();
//                        batchCount++;
//                    }
//                }
//
//                // process the line
//                //System.out.println(line);
//                // read the next line
//                //line = reader.readLine();
//                // Write the remaining words to a new output file
//                if (!wordBatch.isEmpty()) {
//                    Collections.sort(wordBatch);
//                    Path outputFilePath = outputFolder.resolve(getOutputFileName(batchCount));
//                    try (BufferedWriter writer = Files.newBufferedWriter(outputFilePath)) {
//                        for (String sortedWord : wordBatch) {
//                            writer.write(sortedWord);
//                            writer.write("\n");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private static void deleteFile(Path path) {
//        try {
//            Files.delete(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String getOutputFileName(int shardNum) {
//        return String.format("shard%02d.txt", shardNum);
//    }
//}

//solution from instructor:
import java.io.BufferedReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class MakeShardsCopy {
  private static final int SHARD_SIZE = 100;

  public static void main(String[] args) throws Exception {
    //if (args.length != 2) {
    //  System.out.println("Usage: MakeShards [input file] [output folder]");
    //  return;
    //}

    //Path input = Path.of(args[0]);
    Path input = Path.of("src", "unsorted.txt");
    //Path outputFolder = Files.createDirectory(Path.of(args[1]));
    Path outputFolder = Files.createDirectory(Path.of("src", "shards"));

    // uses try-with-resources to ensure that BufferedReader, Writer, and other resources
    // are properly closed after use, even if an exception occurs.
    try (BufferedReader reader = Files.newBufferedReader(input)) {
      int shardNum = 0;
      String word = reader.readLine();
      while (word != null) {
        List<String> shard = new ArrayList<>(SHARD_SIZE);

          // adds words to the shard until it reaches SHARD_SIZE OR there are no more words in the input file.
        while (shard.size() < SHARD_SIZE && word != null) {
          shard.add(word);
          // reads the next line
          word = reader.readLine();
        }

        // Once a shard is complete, it sorts the shard alphabetically
        shard.sort(String::compareTo);
        Path output = Path.of(outputFolder.toString(), getOutputFileName(shardNum));

        try (Writer writer = Files.newBufferedWriter(output)) {
          for (int i = 0; i < shard.size(); i++) {
            writer.write(shard.get(i));

            if (i < shard.size() - 1) {
              writer.write(System.lineSeparator());
            }
          }
        }

        shardNum++;
      }
    }
  }

  private static String getOutputFileName(int shardNum) {
    return String.format("shard%02d.txt", shardNum);
  }
}
