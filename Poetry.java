package l2t16.Task16GitHubTask;

import java.io.*;

public class Poetry {

  private static final int SHIFT = 15; // Shift value for the cipher.
  private static final String VOWELS = "aeiou"; // String to check vowels.

  // Method to encode a single message using the Caesar cipher.
  public static String encodeMessage(String message) {
    StringBuilder encodedMessage = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      encodedMessage.append(encodeCharacter(message.charAt(i)));
    }
    return encodedMessage.toString();
  }

  // Method to encode a single character (without vowel capitalisation).
  private static char encodeCharacter(char ch) {
    if (Character.isLetter(ch)) {
      char base = Character.isUpperCase(ch) ? 'A' : 'a';
      // Shift character and wrap around the alphabet using modulo.
      return (char) ((ch - base + SHIFT) % 26 + base);
    }
    return ch; // Non-letter characters remain unchanged.
  }

  // Method to encode the message and capitalise vowels.
  public static String encodeMessageWithCapitalVowels(String message) {
    StringBuilder encodedMessage = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      char encodedChar = encodeCharacter(message.charAt(i));
      if (isVowel(encodedChar)) {
        encodedMessage.append(Character.toUpperCase(encodedChar));
      } else {
        encodedMessage.append(encodedChar);
      }
    }
    return encodedMessage.toString();
  }

  // Helper method to check if a character is a vowel (lowercase or uppercase).
  private static boolean isVowel(char ch) {
    return VOWELS.indexOf(Character.toLowerCase(ch)) != -1;
  }

  // Method to reverse a single string (line).
  private static String reverseString(String input) {
    return new StringBuilder(input).reverse().toString();
  }

  // Method to decode the reversed message back to its original text.
  public static String decodeMessage(String message) {
    StringBuilder decodedMessage = new StringBuilder();
    for (int i = 0; i < message.length(); i++) {
      decodedMessage.append(decodeCharacter(message.charAt(i)));
    }
    return decodedMessage.toString();
  }

  // Method to decode a single character (using the reverse shift).
  private static char decodeCharacter(char ch) {
    if (Character.isLetter(ch)) {
      char base = Character.isUpperCase(ch) ? 'A' : 'a';
      // Reverse the shift and wrap around the alphabet using modulo
      char decodedChar = (char) ((ch - base - SHIFT + 26) % 26 + base);

      // Ensure capital vowels go back to their original lowercase form.
      if (Character.isUpperCase(ch) && isVowel(decodedChar)) {
        return Character.toLowerCase(decodedChar);
      }
      return decodedChar;
    }
    return ch; // Non-letter characters remain unchanged.
  }

  // Main method to handle file I/O and apply encoding/decoding.
  public static void main(String[] args) {
    // Input and output file paths.
    String inputFile = "src\\l2t04\\poem.txt";
    String encodedPoemFile = "src\\l2t04\\encodedPoem.txt";
    String capitalVowelsFile = "src\\l2t04\\capitalVowels.txt";
    String reversedPoemFile = "src\\l2t04\\reversePoem.txt";

    // Encoding phase (existing functionality).
    try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
         BufferedWriter encodedPoemWriter = new BufferedWriter(new FileWriter(encodedPoemFile));
         BufferedWriter capitalVowelsWriter = new BufferedWriter(new FileWriter(capitalVowelsFile))) {

      String line;
      // Read each line from poem.txt
      while ((line = reader.readLine()) != null) {
        // Encode the line without vowel capitalisation and write to encodedPoem.txt
        String encodedLine = encodeMessage(line);
        encodedPoemWriter.write(encodedLine);
        encodedPoemWriter.newLine(); // Move to the next line in the file.

        // Encode the line with vowel capitalisation and write to capitalVowels.txt
        String encodedLineWithVowels = encodeMessageWithCapitalVowels(line);
        capitalVowelsWriter.write(encodedLineWithVowels);
        capitalVowelsWriter.newLine(); // Move to the next line in the file
      }

      System.out.println("Encoding complete.\n");
      System.out.println("Compulsory Task 1: encodedPoem.txt");
      System.out.println("Compulsory Task 2: capitalVowels.txt");

    } catch (IOException e) {
      System.out.println("An error occurred while processing the file: " + e.getMessage());
    }

    // New functionality: Reverse and decode phase
    try (BufferedReader capitalVowelsReader = new BufferedReader(new FileReader(capitalVowelsFile));
         BufferedWriter reversedPoemWriter = new BufferedWriter(new FileWriter(reversedPoemFile))) {

      String line;
      // Read each line from capitalVowels.txt
      while ((line = capitalVowelsReader.readLine()) != null) {
        // Reverse the line.
        String reversedLine = reverseString(line);

        // Decode the reversed line.
        String decodedLine = decodeMessage(reversedLine);

        // Write the decoded line to reversePoem.txt
        reversedPoemWriter.write(decodedLine);
        reversedPoemWriter.newLine(); // Move to the next line in the file.
      }

      System.out.println("Compulsory Task 3: reversedPoem.txt");

    } catch (IOException e) {
      System.out.println("An error occurred while processing the file: " + e.getMessage());
    }
  }
}

/*
My resources:
https://www.w3schools.com/java/java_files.asp
https://www.w3schools.com/java/java_files_create.asp
https://www.w3schools.com/java/java_files_read.asp
https://www.youtube.com/watch?v=hgF21imQ_Is&ab_channel=KeepOnCoding
https://www.youtube.com/watch?v=xk4_1vDrzzo&t=18314s&ab_channel=BroCode
https://stackoverflow.com/questions/30765270/reference-a-text-file-with-relative-path-in-java-with-the-intellij-idea
https://desktop.arcgis.com/en/arcmap/latest/tools/supplement/pathnames-explained-absolute-relative-unc-and-url.htm#:~:text=A%20relative%20path%20refers%20to,moving%20up%20in%20the%20hierarchy

Dear Mentor/Reviewer

I took heed of the feedback given from Moipati, and did some further research as even though I am calling
the poem.txt file by its name only on line 81, my exception error still gets thrown.

The same happens with encodedPoem.txt, capitalVowels.txt, and reversePoem.txt, as I need to specify where
these files need to be created to.

So I found on StackOverflow that I need to give the file path from "src" to make it relative.
https://stackoverflow.com/questions/30765270/reference-a-text-file-with-relative-path-in-java-with-the-intellij-idea

If this is still not right, kindly have me resubmit it, and I'll book a session with a mentor to
figure out if I am missing something wholly wrong.

I added the output files as reference to the Methods that worked to produce them
and also as proof for each of the Compulsory Tasks 1, 2, and 3.

Thanks, Farinaaz :)

 */
