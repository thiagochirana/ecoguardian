package br.com.ecoguardian.controllers;

import br.com.ecoguardian.models.Municipio;
import br.com.ecoguardian.models.records.MunicipioDTO;
import br.com.ecoguardian.services.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/municipios")
public class MunicipioController {

    @Autowired
    private MunicipioService municipios;

    @GetMapping("/daUF/{id}")
    @ResponseBody
    public List<Municipio> municipiosDoEstado(@PathVariable String id){
        return municipios.listarMunicipiosDaUF(id);
    }

}
