package ar.edu.utn.frc.tup.lc.iv.services;

import ar.edu.utn.frc.tup.lc.iv.clients.ElectionClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs.ChargesByDistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.districtDTOs.DistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.sectionDTOs.SectionDTO;
import ar.edu.utn.frc.tup.lc.iv.exceptions.NotFoundException;
import ar.edu.utn.frc.tup.lc.iv.records.ChargesRecord;
import ar.edu.utn.frc.tup.lc.iv.records.DistrictRecord;
import ar.edu.utn.frc.tup.lc.iv.records.SectionRecord;
import ar.edu.utn.frc.tup.lc.iv.services.imp.ElectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ElectionServiceTest {
    @Mock
    private ElectionClient electionClient;

    @InjectMocks
    private ElectionService electionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testGetAllDistricts_Success() {
        List<DistrictRecord> districts = List.of(new DistrictRecord(1L, "Arroyo"));

        when(electionClient.getAllDistricts(isNull())).thenReturn(ResponseEntity.ok(districts));

        List<DistrictDTO> result = electionService.GetAllDistricts(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Arroyo", result.get(0).getName());
    }

    @Test
    public void testGetAllDistricts_TeamsNotFound() {
        when(electionClient.getAllDistricts(isNull())).thenReturn(ResponseEntity.ok(new ArrayList<>()));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> electionService.GetAllDistricts(null));
        assertEquals("No se encontraron distritos", exception.getMessage());
    }

    @Test
    public void testGetChargesByDistrictId_Success() {
        List<DistrictRecord> districts = List.of(new DistrictRecord(1L, "Arroyo"));

        when(electionClient.getDistrictsById(anyLong())).thenReturn(ResponseEntity.ok(districts));

        List<ChargesRecord> charges = List.of(new ChargesRecord(2L, "Presidente", 1L));

        when(electionClient.getChargesByDistrictId(anyLong())).thenReturn(ResponseEntity.ok(charges));

        ChargesByDistrictDTO result = electionService.GetChargesByDistrictId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getDistrict().getId());
        assertEquals("Arroyo", result.getDistrict().getName());
        assertEquals(1, result.getCharges().size());
        assertEquals(2L, result.getCharges().get(0).getId());
        assertEquals("Presidente", result.getCharges().get(0).getName());
    }

    @Test
    public void testGetChargesByDistrictId_TeamsNotFound() {
        Long districtId = 999L;
        when(electionClient.getDistrictsById(anyLong())).thenReturn(ResponseEntity.ok(new ArrayList<>()));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> electionService.GetChargesByDistrictId(districtId));
        assertEquals("No se encontro districto con el ID: " + districtId, exception.getMessage());
    }

    @Test
    public void testGetSectionByDistrictId_Success() {
        List<SectionRecord> section = List.of(new SectionRecord(1L, "Arroyo", 5L));

        when(electionClient.getSectionById(anyLong(), isNull())).thenReturn(ResponseEntity.ok(section));

        List<SectionDTO> result = electionService.GetSectionByDistrictId(1L, null);

        assertNotNull(result);
        assertEquals("Arroyo", result.get(0).getName());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1, result.size());
    }

    @Test
    public void testGetSectionByDistrictId_TeamsNotFound() {
        Long districtId = 999L;
        when(electionClient.getSectionById(anyLong(), isNull())).thenReturn(ResponseEntity.ok(new ArrayList<>()));

        NotFoundException exception = assertThrows(NotFoundException.class, () -> electionService.GetSectionByDistrictId(districtId, null));
        assertEquals("No se encontraron secciones con el ID de distrito: " + districtId, exception.getMessage());
    }
}
