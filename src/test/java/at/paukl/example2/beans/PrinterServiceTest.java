package at.paukl.example2.beans;

import at.paukl.example2.domain.PrinterEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author ext.pkling
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("integrationtest")
public class PrinterServiceTest {

    public static final String TEST_ENTITY_NAME = "TEST_ENTITY_1";

    @MockBean
    PrinterRepository printerRepository;

    @Autowired
    private PrinterService printerService;

    @Test
    public void testService() {
        setupRepositoryMock();
        final Long id = printerService.saveEntity(TEST_ENTITY_NAME);
        assertThat(id)
                .isNotNull();

        final List<PrinterEntity> printerByName = printerService.findPrintersByName(TEST_ENTITY_NAME);
        assertThat(printerByName)
                .hasSize(1);
    }

    private void setupRepositoryMock() {
        // not the most amazing "db" for an integration test... will not get us very far
        when(printerRepository.save(any(PrinterEntity.class)))
                .thenAnswer((inv) -> {
                    final PrinterEntity entitiy = inv.getArgumentAt(0, PrinterEntity.class);
                    entitiy.setId(1L);
                    return entitiy;
                });

        when(printerService.findPrintersByName(any()))
                .thenReturn(Arrays.asList(new PrinterEntity()));
    }

}