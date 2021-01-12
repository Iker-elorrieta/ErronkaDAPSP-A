import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class CalidadDeAireJson {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		 
		String nomArchivo = "calidadAireJson";
		generarJson("https://opendata.euskadi.eus/contenidos/ds_informes_estudios/calidad_aire_2021/es_def/adjuntos/index.json",nomArchivo);
		limpiarJson(nomArchivo);
		recorrerJson(nomArchivo);
	}
	
	public static void generarJson(String urlStr, String nomArchivo){
		URL url;
		try {
			url = new URL(urlStr);
			URLConnection conexion = url.openConnection();
			conexion.connect();

			BufferedInputStream inputStream = new BufferedInputStream(url.openStream());

			FileOutputStream fileOS = new FileOutputStream("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json"); 
			byte data[] = new byte[1024];
			int byteContent;
			while ((byteContent = inputStream.read(data, 0, 1024)) != -1) {
				fileOS.write(data, 0, byteContent);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void limpiarJson(String nomArchivo) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json",StandardCharsets.UTF_8));
		String texto = "";
		String linea = bf.readLine();
		while(linea != null) {
            texto += "\n"+linea;
            linea = bf.readLine();
        }
        bf.close();
        
        texto = texto.substring(texto.indexOf("["), texto.indexOf("]")+1);
        
        FileWriter fw = new FileWriter("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json");
        fw.write(texto);
        fw.close();
	}
	
	public static void recorrerJson(String nomArchivo) throws IOException {
		JsonParser parser = new JsonParser();

		try {
			FileReader fr = new FileReader("src/ArchivosJSON_CalidadAire/"+nomArchivo+".json");
			JsonElement datos = parser.parse(fr);

			JsonArray array = datos.getAsJsonArray();
			Iterator<JsonElement> iter = array.iterator();
			while (iter.hasNext()) {
				JsonElement entrada = iter.next();
				JsonObject objeto = entrada.getAsJsonObject();
				Iterator<Map.Entry<String, JsonElement>> iter2 = objeto.entrySet().iterator();
				JsonPrimitive format = iter2.next().getValue().getAsJsonPrimitive();
				
				JsonPrimitive name = iter2.next().getValue().getAsJsonPrimitive();
				
				JsonPrimitive url = iter2.next().getValue().getAsJsonPrimitive();
				String urlStr = url.getAsString();
				if (urlStr.contains("datos_indice")) {
					String nameStr = name.getAsString();
					generarJson(urlStr, nameStr);
					limpiarJson(nameStr);
					System.out.println("hecho");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
