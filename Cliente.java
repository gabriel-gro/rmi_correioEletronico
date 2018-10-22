
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;

public class Cliente {

    private static Usuario novoUsuario(){
        Scanner ler = new Scanner( System.in );
        System.out.println("| ----------------------------");
        System.out.println("|         Novo Usuario");
        System.out.println("| ----------------------------");

        System.out.print("| Digite o username: ");
        String userName = ler.next();

        System.out.print("| Digite a senha: ");
        String senha = ler.next();

        Usuario usuario = new Usuario(userName, senha);
        System.out.println();
        return usuario;
    }

    private static Mensagem obterMsg(String userNameRemetente){
        Scanner ler = new Scanner( System.in );

        System.out.print("| Digite o titulo: ");
        String titulo = ler.nextLine();
        
        System.out.println("| Digite o Texto da mensagem ");
        System.out.print("| > ");
        String msg = ler.nextLine();

        Date data = new Date();

        Mensagem m = new Mensagem(userNameRemetente, titulo, msg, data);
        return m;
    }

    private static int menu(){
        int op = -1;
        Scanner ler = new Scanner( System.in );
        System.out.println("| ----------------------------");
        System.out.println("|             MENU");
        System.out.println("| ----------------------------");
        System.out.println("| 1 - Cadastrar");
        System.out.println("| 2 - Obter Mensagem");
        System.out.println("| 3 - Obter Quantas Mensagens Voce Tem");
        System.out.println("| 4 - Enviar Mensagem");
        System.out.println("| ");
        System.out.println("| 0 - SAIR");
        System.out.println("| ----------------------------");
        while (op < 0 || op > 4){
            
            System.out.print("| Opção: ");
            op = ler.nextInt();
            ler.nextLine();
            if(op < 0 || op > 4){
                System.out.println("| Escolha uma opção entre os valores do MENU");
            }
        }
        return op;
    }

    public static void main (String args[]) {

        String host = (args.length < 1) ? null : args[0];

        try {       
            Registry registry = LocateRegistry.getRegistry(host);
            System.out.println(host);
            Correio stub = (Correio) registry.lookup("Correio");
            
            String nome = "ClienteRibolive";
            Scanner ler = new Scanner( System.in );

            String userName;
            String senha;

            while(true){
                switch (menu()) {
                    case 0:
                        System.out.println("| >>> Até logo <<<");
                        return;
                    case 1:
                        boolean rUsCadastrado = stub.cadastrarUsuario(novoUsuario());
                        if (rUsCadastrado){
                            System.out.println("| >>> Cadastrado!!! <<<");
                        } else {
                            System.out.println("| <<< Não foi possivel cadastrar, tente um outro userName >>>");
                        }
                        break;
                    case 2:
                        System.out.println("| ----------------------------");
                        System.out.println("|        Obeter Mensagem");
                        System.out.println("| ----------------------------");
                        System.out.print("| Digite o username: ");
                        userName = ler.next();

                        System.out.print("| Digite a senha: ");
                        senha = ler.next();
                        System.out.println("| ----------------------------");

                        Mensagem rUsGetMensagem = stub.getMensagem(userName, senha);
                        if (rUsGetMensagem != null) {
                            System.out.println(rUsGetMensagem.getMensagem());
                        } else {
                            System.out.println("| <<< Não foi possivel obter uma mensagem >>>");
                        }
                        System.out.print("\n");
                        break;
                    case 3:
                        System.out.println("| ----------------------------");
                        System.out.println("|   Quantidade de mensagens");
                        System.out.println("| ----------------------------");
                        System.out.print("| Digite o username: ");
                        userName = ler.next();

                        System.out.print("| Digite a senha: ");
                        senha = ler.next();
                        System.out.println("| ----------------------------");
                        

                        int rUsGetNMensagem = stub.getNMensagens(userName, senha);
                        if (rUsGetNMensagem != -1) {
                            System.out.print("| Num de mensagem: ");
                            System.out.println(rUsGetNMensagem);
                        } else {
                            System.out.println("| <<< Não foi possivel obter quantas mensagens existem para esse usuario >>>");
                        }
                        System.out.print("\n");
                        break;
                    case 4:
                        System.out.println("| ----------------------------");
                        System.out.println("|         Enviar Mensagem");
                        System.out.println("| ----------------------------");
                        System.out.print("| Digite seu username: ");
                        String userNameRemetente = ler.next();

                        System.out.print("| Digite sua senha: ");
                        senha = ler.next();

                        System.out.print("| Digite o Username do destinatario: ");
                        String destinatario = ler.next();

                        Mensagem msg = obterMsg(userNameRemetente);
                        Boolean rSendMensagem = stub.sendMensagem(msg, senha, destinatario);
                        if (rSendMensagem) {
                            System.out.println("| >>> Mensagem enviada com sucesso <<< ");
                        } else {
                            System.out.println("| Não foi possivel enviar a mensagem ao destinatario");
                        }
                        break;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Cliente exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
