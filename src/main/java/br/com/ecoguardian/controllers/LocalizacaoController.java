package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.records.localizacao.CoordenadasDTO;
import br.com.ecoguardian.models.records.localizacao.EnderecoJSON;
import br.com.ecoguardian.services.LocalizacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/coordenadas")
public class LocalizacaoController {

    @Autowired
    private LocalizacaoService localizacaoService;

    @PostMapping("/obter")
    @ResponseBody
    public CoordenadasDTO obterCoordenadas(@RequestBody EnderecoJSON json){
        return localizacaoService.obterCoordenadasDoEndereco(json.endereco());
    }

    @PostMapping("/trataEnderecoTeste")
    @ResponseBody
    public String tratarEndereco(@RequestBody EnderecoJSON json){
        return localizacaoService.tratarEnderecoTeste(json.endereco());
    }
}
