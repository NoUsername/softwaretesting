package at.paukl.example2.domain;

import org.slf4j.Logger;

import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Paul Klingelhuber
 */
@Entity
public class PrinterEntity {
    private static final Logger LOG = getLogger(PrinterEntity.class);

    private Long id;
    private String name;
    private String jsonDoc;

    public PrinterEntity() {
        LOG.debug("TestEntity created");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(columnDefinition = "JSON")
    public String getJsonDoc() {
        return jsonDoc;
    }

    public void setJsonDoc(String jsonDoc) {
        this.jsonDoc = jsonDoc;
    }

    @Override
    public String toString() {
        return "PrinterEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", jsonDoc='" + jsonDoc + '\'' +
                '}';
    }
}
