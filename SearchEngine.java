import java.io.IOException;
import java.net.URI;
import java.util.*;

class Handler implements URLHandler {
    // The array that will be changed through out the user interaction with the uri
    ArrayList<String> user = new ArrayList<String>();
    ArrayList<String> savedSearch = new ArrayList<String>();

    // Keeps track how many inputs were given
    int num = 0;

    public String handleRequest(URI url) {
        // The instantiated string list
        if (url.getPath().equals("/")) {
            return String.format("Number of strings in our list: %d", num);
        } 
        // If search command given, will return list of all strings
        else if (url.getPath().equals("/search")) {
            String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String search = parameters[1];
                    for( String s : user) {
                        if(s.contains(search)) {
                            savedSearch.add(s);
                        }
                    }
                    if(savedSearch.size() > 0) {
                        return savedSearch.toString();
                    }
                    // Return String when empty query
                    else return String.format("Add words to the query please");
                }
            return ("Number incremented!");
        } 
        // If add command given, will add new substring to list of strings
        else if (url.getPath().contains("/add")) {
            String[] params = url.getQuery().split("=");
            
                if(params[0].equals("s")) {
                    user.add(params[1]);
                    num+=1;
                    return String.format("The word %s has been added to the list! Now we have %d word(s) in the list and our counter is at %d.", params[1], user.size(), num);
                }
            
            return String.format("Please input keyword in correct format.");
        }
        
        else {
            System.out.println("Path: " + url.getPath());
            return "404 Not Found!";
        }
    }
}


public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
