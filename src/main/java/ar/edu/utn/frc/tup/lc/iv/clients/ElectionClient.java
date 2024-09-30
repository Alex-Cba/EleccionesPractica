package ar.edu.utn.frc.tup.lc.iv.clients;

import ar.edu.utn.frc.tup.lc.iv.records.ChargesRecord;
import ar.edu.utn.frc.tup.lc.iv.records.DistrictRecord;
import ar.edu.utn.frc.tup.lc.iv.records.GroupRecord;
import ar.edu.utn.frc.tup.lc.iv.records.ResultRecord;
import ar.edu.utn.frc.tup.lc.iv.records.SectionRecord;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Data
@Service
public class ElectionClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${EXTERNAL_API_URL}")
    private String url;

    public ElectionClient () {
        if (url == null || url.isEmpty()) {
            url = "http://localhost:8080/ping";
        }
    }

    public ResponseEntity<List<DistrictRecord>> getAllDistricts(String fieldName) {
        try {
            var response = restTemplate.exchange(url + "/distritos"
                            + (fieldName == null || fieldName.isEmpty() ? "" : "?distritoNombre=" + fieldName),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<DistrictRecord>>() {
                    });
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<DistrictRecord>> getDistrictsById(Long districtId) {
        try {
            var response = restTemplate.exchange(url + "/distritos?distritoId=" + districtId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<DistrictRecord>>() {
                    });
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<ChargesRecord>> getChargesByDistrictId(Long districtId) {
        try {
            var response = restTemplate.exchange(url + "/cargos?distritoId=" + districtId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ChargesRecord>>() {
                    });
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<SectionRecord>> getSectionById(Long districtId, Long sectionId) {
        try {
            var response = restTemplate.exchange(url + "/secciones?distritoId=" + districtId
                            + (sectionId != null ? "&seccionId=" + sectionId : ""),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<SectionRecord>>() {
                    });
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<List<ResultRecord>> getResults(Long districtId) {
        try {
            var response = restTemplate.exchange(url + "/resultados?districtId=" + districtId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ResultRecord>>() {
                    });
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseEntity<GroupRecord> getGroup(Long groupId) {
        try {
            var response = restTemplate.exchange(url + "/agrupaciones/" + groupId,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<GroupRecord>() {
                    });
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
