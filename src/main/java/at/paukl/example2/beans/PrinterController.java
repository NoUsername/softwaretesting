package at.paukl.example2.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Paul Klingelhuber
 */
@RestController
public class PrinterController {

    @Autowired
    private PrinterService printerService;

    @GetMapping("/printers")
    public List<Object> listPrinters() {
        return printerService.getAll().stream()
                .collect(Collectors.toList());
    }

    @PostMapping("/newprinter/{name}")
    @Transactional
    public String createPrinter(@PathVariable("name") String name) {
        if (printerService.findPrintersByName(name).size() == 0) {
            printerService.saveEntity(name);
        }
        return "ok";
    }

    @GetMapping("/printers/{minSpeed}")
    public List<Object> listPrinters(@PathVariable("minSpeed") int minSpeed) {
        return printerService.findPrinterByMinSpeed(
                // INTRODUCE BUG:
                Math.min(minSpeed, 0)
                ).stream()
                .collect(Collectors.toList());
    }


}
