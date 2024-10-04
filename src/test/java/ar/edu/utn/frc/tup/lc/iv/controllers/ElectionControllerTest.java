package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs.ChargesByDistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.chargeDTOs.ChargesDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.districtDTOs.DistrictDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs.PoliticalResult;
import ar.edu.utn.frc.tup.lc.iv.dtos.resultDTOs.ResultDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.sectionDTOs.SectionDTO;
import ar.edu.utn.frc.tup.lc.iv.exceptions.AppException;
import ar.edu.utn.frc.tup.lc.iv.exceptions.NotFoundException;
import ar.edu.utn.frc.tup.lc.iv.services.IElectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ElectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IElectionService electionService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetAllDistrictsNotFoundException() throws Exception {
        given(electionService.GetAllDistricts(null)).willThrow(new NotFoundException("No se encontraron distritos"));

        mockMvc.perform(get("/distritos"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No se encontraron distritos"))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGetAllDistrictsNotFoundExceptionWithParam() throws Exception {
        given(electionService.GetAllDistricts(anyString())).willThrow(new NotFoundException("No se encontraron distritos"));

        mockMvc.perform(get("/distritos" ).param("distrito_nombre", "test"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No se encontraron distritos"))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGetAllDistricts() throws Exception {
        List<DistrictDTO> expectedDistrict = List.of(new DistrictDTO(1L, "Arroyo"));

        when(electionService.GetAllDistricts(null)).thenReturn(expectedDistrict);

        mockMvc.perform(get("/distritos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Arroyo"));
    }

    @Test
    void testGetChargesByDistrictIdNotFoundException() throws Exception {
        int districtId = 999;

        given(electionService.GetChargesByDistrictId(anyLong())).willThrow(new NotFoundException("No se encontraron cargos con el ID de districto: " + districtId));

        mockMvc.perform(get("/cargos").param("distrito_id", String.valueOf(districtId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No se encontraron cargos con el ID de districto: " + districtId))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGetChargesByDistrictId() throws Exception {
        ChargesByDistrictDTO expectedCharges = new ChargesByDistrictDTO(new DistrictDTO(1L, "Arroyo"), List.of(new ChargesDTO(2L, "Presidente")));

        when(electionService.GetChargesByDistrictId(anyLong())).thenReturn(expectedCharges);

        mockMvc.perform(get("/cargos").param("distrito_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distrito.id").value(1L))
                .andExpect(jsonPath("$.distrito.nombre").value("Arroyo"))
                .andExpect(jsonPath("$.cargos[0].id").value(2L))
                .andExpect(jsonPath("$.cargos[0].nombre").value("Presidente"));
    }

    @Test
    void testGetSectionByDistrictIdNotFoundException() throws Exception {
        int districtId = 999;

        given(electionService.GetSectionByDistrictId(anyLong(), isNull())).willThrow(new NotFoundException("No se encontraron secciones con el ID de distrito: " + districtId));

        mockMvc.perform(get("/secciones").param("distrito_id", String.valueOf(districtId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No se encontraron secciones con el ID de distrito: " + districtId))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGetSectionByDistrictId() throws Exception {
        List<SectionDTO> expectedSection = List.of(new SectionDTO(1L, "Córdoba"));

        when(electionService.GetSectionByDistrictId(anyLong(), isNull())).thenReturn(expectedSection);

        mockMvc.perform(get("/secciones").param("distrito_id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nombre").value("Córdoba"));
    }

    @Test
    void testGeResultsByDistrictAndSectionNotFoundException() throws Exception {
        int districtId = 999;

        given(electionService.GeResultsByDistrictAndSection(anyLong(), anyLong())).willThrow(new NotFoundException("No se encontraron resultados con el ID de districto: " + districtId));

        mockMvc.perform(get("/resultados").param("distrito_id", String.valueOf(districtId)).param("section_id", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("No se encontraron resultados con el ID de districto: " + districtId))
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testGeResultsByDistrictAndSectionAppException() throws Exception {
        int districtId = 999;

        given(electionService.GeResultsByDistrictAndSection(anyLong(), anyLong())).willThrow(new AppException("Hubo un error inesperado"));

        mockMvc.perform(get("/resultados").param("distrito_id", String.valueOf(districtId)).param("section_id", "1"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Hubo un error inesperado"))
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }

    @Test
    void testGeResultsByDistrictAndSectionIllegalException() throws Exception {
        int districtId = 999;

        given(electionService.GeResultsByDistrictAndSection(anyLong(), anyLong())).willThrow(new IllegalArgumentException("Debe colocar todos los parametros obligatorios"));

        mockMvc.perform(get("/resultados").param("distrito_id", String.valueOf(districtId)).param("section_id", "1"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Debe colocar todos los parametros obligatorios"))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void testGeResultsByDistrictAndSection() throws Exception {
        ResultDTO expectedResult = new ResultDTO("Córdoba", "Arroyo", List.of(new PoliticalResult(1, "Juntos Por el titulo", 100, new BigDecimal(50))));

        when(electionService.GeResultsByDistrictAndSection(anyLong(), anyLong())).thenReturn(expectedResult);

        mockMvc.perform(get("/resultados").param("distrito_id", "1").param("section_id", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.distrito").value("Córdoba"))
                .andExpect(jsonPath("$.seccion").value("Arroyo"))
                .andExpect(jsonPath("$.resultados[0].orden").value(1L))
                .andExpect(jsonPath("$.resultados[0].nombre").value("Juntos Por el titulo"))
                .andExpect(jsonPath("$.resultados[0].votos").value(100))
                .andExpect(jsonPath("$.resultados[0].porcentaje").value(50));
    }
}
