package Generadores;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class ImportarCertificado {
	
	public static void main(String[] args) {
		principal();
	}
	
	public static void principal() {
		try {
			InputStream certIn = ClassLoader.class.getResourceAsStream("src/euskadi.cer");
			
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
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
