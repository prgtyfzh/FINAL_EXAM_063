/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.a.FINAL_EXAM;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */

@RestController
@CrossOrigin
public class FEController {
    Surat data = new Surat();
    SuratJpaController ctrl = new SuratJpaController();
    
    @GetMapping(value = "/GET", produces = APPLICATION_JSON_VALUE)
    public List getData() {
        List<Surat> buffer = new ArrayList<>();
        buffer = ctrl.findSuratEntities();
        return buffer;
    }
    
    @PostMapping(value = "/POST", consumes = APPLICATION_JSON_VALUE)
    public String sendData(HttpEntity<String> datasend) throws JsonProcessingException{
        String feedback = "Do Nothing";
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(datasend.getBody(), Surat.class);
        
        try {
            ctrl.create(data);
            feedback = data.getJudul()+ " Saved";
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }
    
    @PutMapping(value = "/PUT", consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> datasend) throws JsonProcessingException {
        String feedback = "Do Nothing";
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(datasend.getBody(), Surat.class);
        
        try {
            ctrl.edit(data);
            feedback = data.getJudul()+ " Edited";
        } catch (Exception error) {
            feedback =  error.getMessage();
        }
        return feedback;
    }
    
    @DeleteMapping(value = "/DELETE", consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> datasend) throws JsonProcessingException{
        String feedback = "Do Nothing";
        ObjectMapper mapper = new ObjectMapper();
        data = mapper.readValue(datasend.getBody(), Surat.class);
        
        try {
            ctrl.destroy(data.getId());
            feedback = " Deleted";
        } catch (Exception error) {
            feedback = error.getMessage();
        }
        return feedback;
    }
    
}
