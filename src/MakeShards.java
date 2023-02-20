//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;

// my code that didn't work
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
/////////////////////////////////////////////////////////////////////


//solution from instructor:

// modified instructor code so I don't have to run the terminal & pass in any arguments to it (still works)
// part 1: creating the shards folder with sorted files of 100 words
/*
import java.io.BufferedReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
public final class MakeShards {
  private static final int SHARD_SIZE = 100;

  public static void main(String[] args) throws Exception {
    Path input = Path.of("src", "unsorted.txt");
    Path outputFolder = Path.of("src", "shards");
    // checks whether the output folder already exists, if it doesn't then it creates it
    if (!Files.exists(outputFolder)) {
      Files.createDirectory(outputFolder);
    }

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

 */


import java.io.BufferedReader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

// first, run the part 1 code to render the shards folder with seperate shard files for part 2
// initial code for part 2 exercise
public final class MakeShards {
  private static final int SHARD_SIZE = 100;
// note: initial code has to be run via terminal commands (hence the args variables)
  // first run: `javac MergeShards.java`, then run: `java MergeShards shards/ sorted.txt`
  public static void main(String[] args) throws Exception {
    // can try to modify this later on so as not to use the command-line commands,
    // but instead just run it
    if (args.length != 2) {
      System.out.println("Usage: MergeShards [input folder] [output file]");
      return;
    }

    List<Path> inputs = Files.walk(Path.of(args[0]), 1).skip(1).collect(Collectors.toList());
    List<BufferedReader> readers = new ArrayList<>(inputs.size());
    Path outputPath = Path.of(args[1]);

    // TODO: Inside a try-finally block, create the List of BufferedReaders: one for each "input"
    //       Path. Without modifying the shard files, merge them together into a single text file
    //       whose location is specified by the "outputPath".
    //
    //       To do this, you should put words in a PriorityQueue<WordEntry>, but make sure the
    //       priority queue never contains more entries than there are input files. The whole point
    //       of doing a distributed merge sort is that there are too many words to fit into memory!
    //
    //       In the "finally" part of the try-finally block, make sure to close all the
    //       BufferedReaders.

    try {
      for (Path path : inputs) {
        readers.add(Files.newBufferedReader(path));
      }
      PriorityQueue<WordEntry> words = new PriorityQueue<>();

      for (BufferedReader reader : readers) {
        String word = reader.readLine();
        if (word != null) {
          words.add(new WordEntry(word, reader));
        }
      }

      try (Writer writer = Files.newBufferedWriter(outputPath)) {
        while (!words.isEmpty()) {
          WordEntry entry = words.poll();
          writer.write(entry.word);
          writer.write(System.lineSeparator());
          String word = entry.reader.readLine();
          if (word != null) {
            words.add(new WordEntry(word, entry.reader));
          }
        }
      }
    } finally {
        for (BufferedReader reader : readers) {
          try {
            reader.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
    }
  }

  private static final class WordEntry implements Comparable<WordEntry> {
    private final String word;
    private final BufferedReader reader;

    private WordEntry(String word, BufferedReader reader) {
      this.word = Objects.requireNonNull(word);
      this.reader = Objects.requireNonNull(reader);
    }

    @Override
    public int compareTo(WordEntry other) {
      return word.compareTo(other.word);
    }
  }

}
