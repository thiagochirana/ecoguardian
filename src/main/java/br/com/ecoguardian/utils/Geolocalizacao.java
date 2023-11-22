package br.com.ecoguardian.utils;

import br.com.ecoguardian.models.records.localizacao.CoordenadasDTO;
import com.opencagedata.jopencage.JOpenCageGeocoder;
import com.opencagedata.jopencage.model.JOpenCageForwardRequest;
import com.opencagedata.jopencage.model.JOpenCageLatLng;
import com.opencagedata.jopencage.model.JOpenCageResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Geolocalizacao {

    @Autowired
    private Environment environment;

    public CoordenadasDTO getCoordenadas(String enderecoCompleto){

        JOpenCageGeocoder jOpenCageGeocoder = new JOpenCageGeocoder(environment.getProperty("geolocalizacao.api.key"));

        JOpenCageForwardRequest request = new JOpenCageForwardRequest(tratarEndereco(enderecoCompleto, true));
        request.setLanguage("pt-BR");

        request.setPretty(true);

        JOpenCageResponse response = jOpenCageGeocoder.forward(request);
        JOpenCageLatLng firstResultLatLng = response.getFirstPosition();
        if (firstResultLatLng == null) {
            return new CoordenadasDTO(enderecoCompleto, "", "");
        }
        return new CoordenadasDTO(tratarEndereco(enderecoCompleto, false), firstResultLatLng.getLat().toString(), firstResultLatLng.getLng().toString());
    }

    public String tratarEndereco(String enderecoCru, boolean tratarCaractereEspecial){
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < enderecoCru.length(); i++) {
            char c = enderecoCru.charAt(i);

            if (c == ',') {
                if (i < enderecoCru.length() - 1 && enderecoCru.charAt(i + 1) != ' ') {
                    deveTratarCaractere(resultado, tratarCaractereEspecial, ',');
                    deveTratarCaractere(resultado, tratarCaractereEspecial, ' ');
                } else {
                    deveTratarCaractere(resultado, tratarCaractereEspecial, ',');
                }
            } else {
                deveTratarCaractere(resultado, tratarCaractereEspecial, c);
            }
        }

        return resultado.toString().trim();
    }

    private void deveTratarCaractere(StringBuilder res, boolean deveTratar, char caractere){
        if (deveTratar) {
            res.append(encodeCaractereEspecial(caractere));
        } else {
            res.append(caractere);
        }
    }

    private static String encodeCaractereEspecial(char c) {
        if (c == '+'){
            return "+";
        }
        if (c == ' '){
            return "+";
        }
        return String.valueOf(c);
    }
}
