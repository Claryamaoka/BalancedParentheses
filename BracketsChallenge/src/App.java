import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Digite o caminho para o arquivo texto que você quer validar: ");

        Scanner scanner = new Scanner(System.in);
        //String path = scanner.nextLine();
        var path = System.console().readLine();
        System.out.println("----------------------------------");
        
        Path caminho = Paths.get(path);
        List<String> list = Files.readAllLines(caminho, StandardCharsets.UTF_8);

        if(list.isEmpty()){
            System.out.println("Não foi possível encontrar o arquivo.");
        }
        else{
            ArrayList<String> responses = new ArrayList<String>();
            responses = validateBrackets(list);

            for (String string : responses) {
                System.out.println(string);
            }
        }
    }

    public static ArrayList<String> validateBrackets(List<String> lines){
        ArrayList<String> answers = new ArrayList<String>();
        String lineResponse = "";
        

        for (String line : lines) {
            
            lineResponse = "";
            Stack<Character> stack = new Stack<Character>(); 
            line = line.trim();
            char[] chars = line.toCharArray();

            for (char ch : chars) {
                if(ch != '[' && ch != ']' 
                && ch != '{' && ch != '}' 
                && ch != '(' && ch != ')' 
                && ch != '<' && ch != '>'){
                    lineResponse = "Essa linha possui caracteres inválidos";
                    break;
                }
            }

            if(lineResponse.equals("")){
                for(int i = 0; i< line.length(); i++){
                    if(line.charAt(i) == '(' ||
                    line.charAt(i) == '{' ||
                    line.charAt(i) == '[' || 
                    line.charAt(i) == '<'){
                        stack.push(line.charAt(i));
                    } else {
                        if(stack.isEmpty()){
                            lineResponse = "Inválido";
                            break;
                        } 

                        if(line.charAt(i) == ')' && stack.peek() != '(' ||
                        line.charAt(i) == ']' && stack.peek() != '[' ||
                        line.charAt(i) == '}' && stack.peek() != '{'||
                        line.charAt(i) == '>' && stack.peek() != '<'){
                            lineResponse = "Inválido";
                            break;
                        }

                        stack.pop();
                    }
                }

                if(lineResponse.equals(""))
                    lineResponse = "Válido";
            }

            answers.add(line + " - " + lineResponse);
        }

        return answers;
    }
}
