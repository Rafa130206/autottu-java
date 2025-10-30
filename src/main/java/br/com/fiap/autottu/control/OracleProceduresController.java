package br.com.fiap.autottu.control;

import br.com.fiap.autottu.service.OracleProceduresService;
import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oracle")
public class OracleProceduresController {

    private final OracleProceduresService oracleProceduresService;
    private final Gson gson = new Gson();

    public OracleProceduresController(OracleProceduresService oracleProceduresService) {
        this.oracleProceduresService = oracleProceduresService;
    }

    @GetMapping(value = "/checkins-json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object checkinsJson() {
        String json = oracleProceduresService.executarPrcExibirCheckinsJson();

        // Se veio erro do Oracle, devolve como texto mesmo
        if (json.trim().startsWith("{\"erro\"")) {
            return gson.fromJson(json, Object.class);
        }

        // Caso contrário, faz o parse do JSON gerado pelo Oracle e devolve objeto real
        try {
            return gson.fromJson(json, Object.class);
        } catch (Exception e) {
            // Se o parse falhar, devolve o texto original
            return "{\"erro\":\"Falha ao converter JSON: " + e.getMessage() + "\"}";
        }
    }

    @GetMapping(value = "/relatorio-checkins", produces = MediaType.TEXT_PLAIN_VALUE)
    public String relatorioCheckins() {
        return oracleProceduresService.executarPrcRelatorioCheckins();
    }
}
