package br.com.ecoguardian.apis;

import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.utils.Log;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class Request {

    Log LOG = new Log(Request.class);

    public List<MunicipioDTO> municipiosDoEstado(int id){
        String url = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/"+id+"/municipios";
        String resp = realizarRequest(url, "GET", null);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<MunicipioDTO> listaDeMunicipios = objectMapper.readValue(
                    resp,
                    new TypeReference<List<MunicipioDTO>>() {}
            );
            return listaDeMunicipios;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }



    private String realizarRequest(String urlApi, String metodo, Map<String, Object> params){
        try {
            URL url = new URL(urlApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(metodo.toUpperCase());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
            reader.close();
            connection.disconnect();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
