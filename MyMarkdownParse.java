// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MyMarkdownParse {
    // make a new comment
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {

            

            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            
            // testing back tip
            int nextCodeBlock = markdown.indexOf("`", currentIndex);
            // System.out.println("nextCodeBlock: " + nextCodeBlock);
            if(nextCodeBlock < nextOpenBracket && nextCodeBlock != -1) {
                int endOfCodeBlock = markdown.indexOf("`", nextCodeBlock+1);
                // System.out.println("endOfCodeBlock: " + endOfCodeBlock);
                
                currentIndex = endOfCodeBlock + 1;
                continue;
            }


            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            if(nextOpenBracket == -1 || nextCloseBracket == -1){
                break;
            }
            // check for image url
            if(nextOpenBracket > 0 && markdown.charAt(nextOpenBracket - 1) == '!'){
                currentIndex = nextCloseBracket + 1;
                continue;
            }
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);
            if(openParen == -1 || closeParen == -1){
                break;
            }
            if(openParen - 1 == nextCloseBracket){
                toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
            currentIndex = closeParen + 1;

            
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of("201.md");
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}