package br.com.fiap.autottu.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OracleProceduresService {

    private final DataSource dataSource;

    public OracleProceduresService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String executarPrcExibirCheckinsJson() {
        List<String> linhas = executarComDbmsOutput("begin pct_procs.prc_exibir_checkins_json; end;");
        // Junta todas as linhas do DBMS_OUTPUT em um único JSON
        return String.join("", linhas);
    }

    public String executarPrcRelatorioCheckins() {
        List<String> linhas = executarComDbmsOutput("begin pct_procs.prc_relatorio_checkins; end;");
        return String.join(System.lineSeparator(), linhas);
    }

    private List<String> executarComDbmsOutput(String plsqlBlock) {
        List<String> linhas = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            // Habilita buffer ilimitado do DBMS_OUTPUT
            try (CallableStatement enable = connection.prepareCall("begin dbms_output.enable(null); end;")) {
                enable.execute();
            }

            // Executa o bloco PL/SQL informado (procedure)
            try (CallableStatement callProc = connection.prepareCall(plsqlBlock)) {
                callProc.execute();
            }

            // Lê as linhas do DBMS_OUTPUT
            boolean continuar = true;
            while (continuar) {
                try (CallableStatement getLine = connection.prepareCall(
                        "declare line varchar2(32767); status integer; begin dbms_output.get_line(line, status); ? := line; ? := status; end;")) {
                    getLine.registerOutParameter(1, java.sql.Types.VARCHAR);
                    getLine.registerOutParameter(2, java.sql.Types.INTEGER);
                    getLine.execute();
                    String line = getLine.getString(1);
                    int status = getLine.getInt(2);
                    if (status == 0) {
                        linhas.add(line != null ? line : "");
                    } else {
                        continuar = false;
                    }
                }
            }

        } catch (SQLException e) {
            linhas.clear();
            linhas.add("{\"erro\": \"Falha ao executar procedure: " + e.getMessage().replace("\"", "'") + "\"}");
        }
        return linhas;
    }
}
