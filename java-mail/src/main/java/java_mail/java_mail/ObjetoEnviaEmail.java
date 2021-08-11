package java_mail.java_mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	// Email que vai ser utilizado para o envio
	private String emailRemetente = "meuemail@gmail.com";
	private String senhaEmailRemetente = "senhaMeuEmail";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = ""; 
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaEmail(String listaDestinatarios, String nomeRemetente, String assuntoEmail,String textoEmail) {
		
		this.listaDestinatarios = listaDestinatarios;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
	
	
	public void enviarEmail(boolean envioHtml) throws Exception{
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true"); /* Autorização */
		properties.put("mail.smtp.starttls", "true"); /* Autenticação */
		properties.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor do gmail */
		properties.put("mail.smtp.port", "465"); /* Porta do Servidor */
		properties.put("mail.smtp.socketFactory.port", "465"); /* Especifica a porta a ser conectada pelo socket */
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); /* Classe socket de conexão ao SMTP */

		Session session = Session.getInstance(properties, new Authenticator() {
		
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(emailRemetente, senhaEmailRemetente);
			}
		
		});
		
		// Destinatários
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
			
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailRemetente, nomeRemetente)); /*Quem está Enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser); /* Email's destinatários */
		message.setSubject(assuntoEmail); /* Assunto do e-mail */
		
		if(envioHtml) {
			message.setContent(textoEmail, "text/html; charset=utf-8"); /* Mensagem a ser enviada para os destinatários customizada com HTML */
		}else {
			message.setText(textoEmail); /* Mensagem a ser enviada para os destinatários */
		}
		
		
		Transport.send(message);
		
		
	}
	
	// Inicio - Enviando email com um anexo (PDF)
	
	/**
	 * Esse método simula um PDF ou qualquer arquivo que possa ser enviado por anexo no email
	 * O arquivo pode ser pego do banco de dados
	 * O arquivo pode ser pego de uma pasta ...
	 * 
	 * Retorna um PDF com apenas um paragrafo para ser enviado como anexo no e-mail
	 * */
	private FileInputStream simuladorDePdf() throws Exception{
		
		Document document = new Document();
		File file = new File("fileanexo.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));
		document.open();
		document.add(new Paragraph("Anexo com JavaMail"));
		document.close();
		
		return new FileInputStream(file);
		
	}
	
	public void enviarEmailComAnexo(boolean envioHtml) throws Exception{
			
			Properties properties = new Properties();
			properties.put("mail.smtp.ssl.trust", "*");
			properties.put("mail.smtp.auth", "true"); /* Autorização */
			properties.put("mail.smtp.starttls", "true"); /* Autenticação */
			properties.put("mail.smtp.host", "smtp.gmail.com"); /* Servidor do gmail */
			properties.put("mail.smtp.port", "465"); /* Porta do Servidor */
			properties.put("mail.smtp.socketFactory.port", "465"); /* Especifica a porta a ser conectada pelo socket */
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); /* Classe socket de conexão ao SMTP */
	
			Session session = Session.getInstance(properties, new Authenticator() {
			
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					
					return new PasswordAuthentication(emailRemetente, senhaEmailRemetente);
				}
			
			});
			
			// Destinatários
			Address[] toUser = InternetAddress.parse(listaDestinatarios);
				
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailRemetente, nomeRemetente)); /*Quem está Enviando*/
			message.setRecipients(Message.RecipientType.TO, toUser); /* Email's destinatários */
			message.setSubject(assuntoEmail); /* Assunto do e-mail */
			
			
			/* Parte 1 do email que é um texto e a descrição do e-mail */
			MimeBodyPart corpoEmail = new MimeBodyPart();
			if(envioHtml) {
				corpoEmail.setContent(textoEmail, "text/html; charset=utf-8"); /* Mensagem a ser enviada para os destinatários customizada com HTML */
			}else {
				corpoEmail.setText(textoEmail); /* Mensagem a ser enviada para os destinatários */
			}
			
			// Parte 2 Anexo email
			MimeBodyPart anexoEmail = new MimeBodyPart();
			// Onde é passado o simuladorPdf, pode ser passado um arquivo do banco de dados
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(this.simuladorDePdf(), "application/pdf")));
			anexoEmail.setFileName("anexoemail.pdf");
			
			// Juntar as partes 1 e 2
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmail);
			multipart.addBodyPart(anexoEmail);

			
			message.setContent(multipart);
			
			Transport.send(message);
			
			
		}
		

	// Fim - Enviando email com um anexo (PDF)
	
	
}







