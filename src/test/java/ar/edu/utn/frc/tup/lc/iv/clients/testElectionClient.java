package ar.edu.utn.frc.tup.lc.iv.clients;

import ar.edu.utn.frc.tup.lc.iv.records.ChargesRecord;
import ar.edu.utn.frc.tup.lc.iv.records.DistrictRecord;
import ar.edu.utn.frc.tup.lc.iv.records.GroupRecord;
import ar.edu.utn.frc.tup.lc.iv.records.ResultRecord;
import ar.edu.utn.frc.tup.lc.iv.records.SectionRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class testElectionClient {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ElectionClient electionClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDistricts_Success() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<List<DistrictRecord>> response = electionClient.getAllDistricts(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllDistricts_HttpClientErrorException() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        ResponseEntity<List<DistrictRecord>> response = electionClient.getAllDistricts(null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetAllDistricts_GenericException() {
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), any(ParameterizedTypeReference.class)))
                .thenThrow(new RuntimeException("Error genérico"));

        ResponseEntity<List<DistrictRecord>> response = electionClient.getAllDistricts(null);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetDistrictsById_Success() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<List<DistrictRecord>> response = electionClient.getDistrictsById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetChargesByDistrictId_Success() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<List<ChargesRecord>> response = electionClient.getChargesByDistrictId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetSectionById_Success() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<List<SectionRecord>> response = electionClient.getSectionById(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetResults_Success() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<List<ResultRecord>> response = electionClient.getResults(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetGroup_Success() {

        when(restTemplate.exchange(any(String.class),
                any(HttpMethod.class),
                any(),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok().build());

        ResponseEntity<GroupRecord> response = electionClient.getGroup(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
