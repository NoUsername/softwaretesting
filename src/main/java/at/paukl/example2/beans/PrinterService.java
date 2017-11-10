package at.paukl.example2.beans;

import at.paukl.example2.domain.PrinterEntity;
import at.paukl.example2.domain.PrinterFeatures;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
@Component
public class PrinterService {
    private static final Logger LOG = getLogger(PrinterService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PrinterRepository printerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Long saveEntity(String name) {
        PrinterEntity entitySave = new PrinterEntity();
        entitySave.setName(name);
        final PrinterFeatures value = getPrinterFeatures(name);
        try {
            entitySave.setJsonDoc(objectMapper.writeValueAsString(value));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        entitySave = printerRepository.save(entitySave);
        Long entityId = entitySave.getId();
        LOG.info("entiy persisted with id {}", entityId);
        return entityId;
    }

    private PrinterFeatures getPrinterFeatures(String name) {
        final PrinterFeatures printerFeatures = new PrinterFeatures();
        printerFeatures.setWidth(name.length());
        printerFeatures.setHeight(name.length() * 2);
        printerFeatures.setLength(name.length() * 3);
        printerFeatures.setPrintSpeed(name.length() * 5);
        return printerFeatures;
    }

    @Transactional
    public List<PrinterEntity> getAll() {
        return printerRepository.findAll();
    }

    @Transactional
    public List<String> loadNamesByIds(ArrayList<Long> ids) {
        final List<PrinterEntity> tests = printerRepository.findAll(ids);

        LOG.info("fetched {} entities", tests.size());

        final List<String> names = tests.stream()
                .map(it -> it.getName())
                .collect(Collectors.toList());
        LOG.info("names collected, returning");

        return names;
    }

    public List<PrinterEntity> findPrintersByName(String name) {
        final PrinterEntity example = new PrinterEntity();
        example.setName(name);
        return printerRepository.findAll(Example.of(example));
    }

    public List<PrinterEntity> findPrinterByMinSpeed(int minSpeed) {
        return printerRepository.findByMinSpeed(minSpeed);
    }

}
