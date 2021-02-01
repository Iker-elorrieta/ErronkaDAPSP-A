package Generadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class ImportarCertificado {
	
	public static void main(String[] args) {
		principal();
	}
	
	public static void principal() {
		for (int i = 1; i <= 2; i++) {
			try {
				System.out.println("[Certificado] >> Importando certificado...");
				if (i == 1) {
					InputStream certIn = ClassLoader.class.getResourceAsStream("Archivos/euskadi.cer");
					
					final char sep = File.separatorChar;
					File dir = new File(System.getProperty("java.home") + sep + "lib" + sep + "security");
					File file = new File(dir, "cacerts");
					InputStream localCertIn = new FileInputStream(file);
					
					char[] passphrase = "changeit".toCharArray();
					KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
					keystore.load(localCertIn, passphrase);
					if (keystore.containsAlias("DreamTeam")) {
					    certIn.close();
					    localCertIn.close();
					    return;
					}
					
					localCertIn.close();
					System.out.println("[Certificado] >> Certificado installado. \n");
				} else {
					trustEveryone();
					System.out.println("[Certificado] >> Error saltado. \n");
				}
				break;
			} catch (FileNotFoundException e) {
				if (!e.getMessage().contains("denied")) {
					System.out.println("[Certificado] >> ERROR: "+e.getMessage());
					System.exit(1);
				}
			} catch (Exception e) {
				System.out.println("[Certificado] >> ERROR: "+e.getMessage());
				System.exit(1);
			}
		}
	}
	
	private static void trustEveryone() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return false;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] {new X509TrustManager() {
				public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
				public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				}
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			}}, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			System.out.println("[Certificado] >> ERROR: "+e.getMessage());
		}
	}

}
