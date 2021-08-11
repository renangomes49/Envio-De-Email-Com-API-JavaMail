package java_mail.java_mail;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

public class AppTest {

	
	// TESTE - Envio de Email sem customização HTML
	@Test
	public void testeEmail() {
		try {
			
			ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("email_1_@gmail.com, email_2_@gmail.com, email_3_@hotmail.com", 
					"Nome do Remetente", 
					"Assunto do email", 
					"Mensagen do email. Ex.: Olá, tudo bem ? ...");
			
			enviaEmail.enviarEmail(false);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
	}
	
	// TESTE - Envio de Email com customização HTML
	@Test
	public void testeEmailHtml() {
		
		try {
			
			StringBuilder stringBuilderTextoEmail = new StringBuilder();
			stringBuilderTextoEmail.append("Olá, tudo certo ? <br>");
			stringBuilderTextoEmail.append("Você está recebendo essa email para confirmar sua presença no evento Java. <br><br>");
			stringBuilderTextoEmail.append("Para confirmar a sua presença clique no botão abaixo! <br><br>");
			stringBuilderTextoEmail.append(" <a target=\"_blank\" href=\"www.google.com\" >  <button>Clique aqui!</button>  </a> <br>");


			ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("email_1_@gmail.com, email_2_@gmail.com, email_3_@hotmail.com", 
					"Nome Remetente", 
					"Assunto do email", 
					stringBuilderTextoEmail.toString());
			
			enviaEmail.enviarEmail(true);
			
			
		}catch(Exception e) {
			
		}
		
	}

		// TESTE - Envio de Email com customização HTML E ANEXO AO EMAIL
		@Test
		public void testeEmailHtmlAnexo() {
			
			try {
				
				StringBuilder stringBuilderTextoEmail = new StringBuilder();
				stringBuilderTextoEmail.append("Olá, tudo certo ? <br>");
				stringBuilderTextoEmail.append("Você está recebendo essa email para confirmar sua presença no evento Java. <br><br>");
				stringBuilderTextoEmail.append("Para confirmar a sua presença clique no botão abaixo! <br><br>");
				stringBuilderTextoEmail.append(" <a target=\"_blank\" href=\"www.google.com\" >  <button>Clique aqui!</button>  </a> <br>");


				ObjetoEnviaEmail enviaEmail = new ObjetoEnviaEmail("email_1_@gmail.com, email_2_@gmail.com, email_3_@hotmail.com", 
						"Nome remetente", 
						"Assunto do email", stringBuilderTextoEmail.toString());
				
				enviaEmail.enviarEmailComAnexo(true);
				
				
			}catch(Exception e) {
				
			}
			
		}

}



