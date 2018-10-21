import java.util.Date;
import java.io.Serializable;


public class Mensagem implements Serializable {

	private String userNameRemetente;
    private String titulo;
    private String texto;
    private Date data;

    public Mensagem(String userRemetente, String titulo, String msg, Date data){
        this.userNameRemetente = userRemetente;
        this.titulo = titulo;
        this.texto = msg;
        this.data = data;
    }

    public String getMensagem(){
        String saida;
        saida = "|      Mensagem";
        saida += "\n| UserName (Remetente): " + this.userNameRemetente;
        saida += "\n| Titulo: " + this.titulo;
        saida += "\n| " + this.texto;
        saida += "\n| Data: " + this.data;
        return saida;
    }

    public String getUserName(){
        return this.userNameRemetente;
    }
}