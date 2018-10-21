import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Server implements Correio {
	private ArrayList<Usuario> usuarios;

	public Server() {
    	this.usuarios = new ArrayList<Usuario>();
    }

	public Usuario usuarioJaCadastrado(String userName){
		for (Usuario user : this.usuarios){
			if (user.getUserName().equals(userName)) {
				return user;
			}
		}
		return null;
	}

	private void listarUsuarios(){
		for (Usuario user : this.usuarios){
			System.out.println(user.getUserName());
		}
	}

    public boolean cadastrarUsuario (Usuario u){
		
		this.listarUsuarios();
		if (this.usuarioJaCadastrado(u.getUserName()) != null) {
			return false;
		} else {
			this.usuarios.add(u);
			System.out.println(u.getUserName() + " Cadastrado com sucesso!");
			return true;
		}

	}

    // Recupera a primeira mensagem na lista de mensagens do usuario; a mensagem deve ser removida
    // Exigir autenticação do usuário
	public Mensagem getMensagem (String userName, String senha){
		Usuario usuario = this.usuarioJaCadastrado(userName);
		if (usuario != null && usuario.autenticaUsuario(senha)) {
			return usuario.capturarUmaMensagem();
		} else {
			return null;
		}
	}
    
    // retorna o número de mensagens na fila de mensagens dos usuário
    // Exigir autenticação do usuário
    public int getNMensagens (String userName, String senha){
		Usuario usuario = this.usuarioJaCadastrado(userName);
		if (usuario != null && usuario.autenticaUsuario(senha)){
			return usuario.verNumDeMensagens();
		} else {
			return -1;
		}
	}
	
	// Exigir autenticação do usuário (senha do remetente, incluído como atributo da mensagem)
	public boolean sendMensagem (Mensagem m, String senha, String userNameDestinatario){
		Usuario userRemetente = this.usuarioJaCadastrado(m.getUserName()); // USUARIO -> Remetente
		Usuario userDestinatario = this.usuarioJaCadastrado(userNameDestinatario); // USUARIO -> Destinatario

		System.out.println(userRemetente.getUserName() + "  " + userDestinatario.getUserName());

		if ((userRemetente != null) && (userDestinatario != null)) {

			System.out.println(userRemetente.getSenha() + "  " + senha);

			if (userRemetente.autenticaUsuario(senha)) { // Remetente verificado se existente
				System.out.println(userRemetente.getUserName());

				userDestinatario.receberMensagem(m);
				
				System.out.println(m.getUserName() + " Enviou uma mensagem para " + userNameDestinatario);
				return true;
			}
		}
		return false;
	}

	public static void main (String args[]) {
		
		try {
            System.setProperty("java.rmi.server.hostname","10.0.0.103");
			
			//Create and export a remote object
			Server obj = new Server();
			Correio stub = (Correio) UnicastRemoteObject.exportObject(obj,0);
			
			//Register the remote object with a Java RMI registry
			Registry registry = LocateRegistry.getRegistry();
			registry.rebind("Correio", stub);
			
			System.out.println("Server Ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}
}
