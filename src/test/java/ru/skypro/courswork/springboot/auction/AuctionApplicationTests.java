package ru.skypro.courswork.springboot.auction;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.*;
import ru.skypro.courswork.springboot.auction.controller.LotController;
import ru.skypro.courswork.springboot.auction.model.dto.LotDTO;
import ru.skypro.courswork.springboot.auction.model.pojo.Status;
import ru.skypro.courswork.springboot.auction.model.view.BidName;
import ru.skypro.courswork.springboot.auction.model.view.BidView;
import ru.skypro.courswork.springboot.auction.model.view.FullLot;
import ru.skypro.courswork.springboot.auction.model.view.LotView;
import ru.skypro.courswork.springboot.auction.repository.BidRepository;
import ru.skypro.courswork.springboot.auction.repository.LotRepository;
import ru.skypro.courswork.springboot.auction.service.bid.BidService;
import ru.skypro.courswork.springboot.auction.service.lot.LotService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AuctionApplicationTests {
    @Autowired
    private LotController testLotController;
    @Autowired
    private BidRepository testBidRepository;
    @Autowired
    private LotRepository testLotRepository;
    @Autowired
    private BidService testBidService;
    @Autowired
    private LotService testLotService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Beans created successfully ")
    void testBeansCreated() {
        assertThat(testLotController).isNotNull();
        assertThat(testBidRepository).isNotNull();
        assertThat(testLotRepository).isNotNull();
        assertThat(testBidService).isNotNull();
        assertThat(testLotService).isNotNull();
    }

    @Test
    @DisplayName("Person with max amount of bids returned successfully")
    void shouldReturnPersonWithMaxAmountOfBids() throws Exception {
        mockMvc.perform(get("/lot/" + 1 + "/frequent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].bidderName", is("Test guy one")));
    }
    @Test
    @DisplayName("Lot full info returned successfully")
    void shouldReturn() throws Exception {
        mockMvc.perform(get("/lot/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("CREATED")))
                .andExpect(jsonPath("$.title", is("Lot number one")))
                .andExpect(jsonPath("$.description", is("Test lot number one with biggest amount of bid")))
                .andExpect(jsonPath("$.startPrice", is(100)))
                .andExpect(jsonPath("$.bidPrice", is(100)));
    }

//    @Test
//    @DisplayName("Employees with max salary returned successfully")
//    void shouldReturnEmployeeWithMaxSalary() throws Exception {
//        mockMvc.perform(get("/employee/withHighestSalary"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("FakeSasha")))
//                .andExpect(jsonPath("$[0].salary", is(60000.00)))
//                .andExpect(jsonPath("$[1].name", is("Sasha")))
//                .andExpect(jsonPath("$[1].salary", is(60000.00)));
//    }
//
//    @Test
//    @DisplayName("Summary salary returned correct")
//    void shouldReturnSumSalary() throws Exception {
//        mockMvc.perform(get("/employee/salary/sum"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(319000.0)));
//    }
//
//    @Test
//    @DisplayName("Employees with salaries above average returned successfully")
//    void shouldReturnEmployeesWithAboveAverageSalary() throws Exception {
//        mockMvc.perform(get("/employee/salary/high-salary"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].name", is("Genadiiy")))
//                .andExpect(jsonPath("$[0].salary", is(30000.00)))
//                .andExpect(jsonPath("$[1].name", is("Kolya")))
//                .andExpect(jsonPath("$[1].salary", is(40000.00)))
//                .andExpect(jsonPath("$[2].name", is("Petya")))
//                .andExpect(jsonPath("$[2].salary", is(50000.00)))
//                .andExpect(jsonPath("$[3].name", is("FakeSasha")))
//                .andExpect(jsonPath("$[3].salary", is(60000.00)))
//                .andExpect(jsonPath("$[4].name", is("Sasha")))
//                .andExpect(jsonPath("$[4].salary", is(60000.00)));
//
//    }
//
//    @Test
//    @DisplayName("Employees with salaries above average returned successfully")
//    void shouldReturnEmployeesWithSalaryMoreThanAverage() throws Exception {
//        mockMvc.perform(get("/employee/salary/salaryHigherThan?salary=" + 59999.00))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$[0].name", is("FakeSasha")))
//                .andExpect(jsonPath("$[0].salary", is(60000.00)))
//                .andExpect(jsonPath("$[1].name", is("Sasha")))
//                .andExpect(jsonPath("$[1].salary", is(60000.00)));
//    }
//
//    @Test
//    @DisplayName("Employee returned successfully by id")
//    void shouldReturnEmployeeById() throws Exception {
//        mockMvc.perform(get("/employee/" + 1))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(jsonPath("$.name", is("Oleg")))
//                .andExpect(jsonPath("$.salary", is(10000.00)));
//    }
//
//    @Test
//    @DisplayName("Employee was created and deleted successfully")
//    void shouldDeleteEmployeeById() throws Exception {
//        Employee testEmployee = new Employee(100, 1.00, "test", null);
//        myTestRepository.save(testEmployee);
//        List<Employee> testList = new ArrayList<>();
//        myTestRepository.findAll().forEach(testList::add);
//        mockMvc.perform(delete("/employee/" + testList
//                        .stream()
//                        .filter(x -> x.getName().equals(testEmployee.getName()))
//                        .mapToLong(Employee::getId)
//                        .findFirst().orElse(0)
//                ))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    @GetMapping("/{id}/first")
//    @Tag(name = "Lot info")
//    public BidView getFirstBidById(@PathVariable("id") Integer id) {
//        return bidService.getFirstBidById(id);
//
//    }

//    @GetMapping("/{id}")
//    @PostMapping("/{id}/start")
//    @PostMapping("/{id}/bid")
//    @PostMapping("/{id}/stop")
//    @PostMapping
//    @GetMapping
//    @GetMapping("/export")

}
