package at.paukl.example2.beans;

import at.paukl.example2.domain.PrinterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Paul Klingelhuber
 */
public interface PrinterRepository extends JpaRepository<PrinterEntity, Long> {

    @Query(value = "SELECT e.* FROM printer_entity e WHERE e.json_doc->>\"$.printSpeed\" > ?",
            nativeQuery = true)
    List<PrinterEntity> findByMinSpeed(int minSpeed);

}
