package utils;

import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log {
    public void iniciarLog() throws IOException {
        //texto que ira ser gravado no cabeçalho do arquivo
        String[] cabecalho = {"datahora", "tipo","mensagem","duracao"};

        //definição da data e hora da criação do arquivo de log para colocar no seu nome
        String datahorainicial = new SimpleDateFormat("yyyy-MMM-dd HH-mm").format(Calendar.getInstance().getTime());
        System.out.println("Data e hora: "+datahorainicial);
        Writer writer = Files.newBufferedWriter(Paths.get("target/logs/userDD-"+datahorainicial+".csv"));//físico

        //prepara a escrita no arquivo o arquivo
        CSVWriter csvWriter = new CSVWriter(writer);//lógico

        //Escreve para o arquivo
        csvWriter.writeNext(cabecalho,false);

        //Salvar arquivo
        csvWriter.flush();

        //fechar arquivo
        csvWriter.close();//físico
        writer.close();//lógico

    }

    public void logar(String datahora, String tipo, String mensagem, int duracao){

    }

    public void finalisarLog(){

    }
}
