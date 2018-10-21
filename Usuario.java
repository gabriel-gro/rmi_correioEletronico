import java.util.LinkedList;
import java.io.Serializable;

public class Usuario implements Serializable {

	private String userName;
    private String senha;
    private LinkedList<Mensagem> listaDeMensagens;

    public Usuario(String u, String s){
        this.userName = u;
        this.senha = s;
        this.listaDeMensagens = new LinkedList<Mensagem>();
    }

    public String getUserName(){
        return this.userName;
    }

    public String getSenha(){
        return this.senha;
    }

    public void receberMensagem(Mensagem m){
        listaDeMensagens.add(m);
    }

    public Mensagem capturarUmaMensagem(){
        return listaDeMensagens.pop();
    }

    public int verNumDeMensagens(){
        return listaDeMensagens.size();
    }

    public boolean autenticaUsuario(String senha){
        if (this.senha.equals(senha))
            return true;
        else
            return false;
    }
}