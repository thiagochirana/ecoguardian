package br.com.ecoguardian.utils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;

@Component
public class VersaoUpdater implements ApplicationRunner{

    private static Log LOG = new Log(VersaoUpdater.class);

    private static final String FILE_PATH = "file_version";

    private final ResourceLoader resourceLoader;

    public VersaoUpdater(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) {
        gerarVersao();
    }


    public static void gerarVersao(){
        String versao[] = VersaoManager.getVersaoAtual().split("\\.");
        int numFinal = Integer.parseInt(versao[2]);
        numFinal += 1;
        String verFinal = versao[0] +"."+ versao[1] +"."+ String.format("%03d", numFinal);
        VersaoManager.salvarVersao(verFinal);
        LOG.info("Versão atual da aplicação: " + verFinal);
    }

    public class VersaoManager {

        public static String getVersaoAtual() {
            try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
                String s = reader.readLine();
                reader.close();
                return s;
            } catch (IOException e) {
                LOG.warn("Erro em ler arquivo file_version: "+e.getMessage());
                return "1.00.000";
            }
        }

        public static void salvarVersao(String versao){
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(), false))){
                writer.write(versao);
                writer.close();
            } catch (Exception e){
                LOG.erro("Não foi possível salvar a versão", e);
            }
        }

        private static String getFilePath() {
            try {
                Resource resource = new ClassPathResource(FILE_PATH);
                return resource.getFile().getAbsolutePath();
            } catch (Exception e) {
                LOG.erro("Houve um erro ao tentar obter o caminho do arquivo file_version", e);
            }
            return null;
        }
    }
}
