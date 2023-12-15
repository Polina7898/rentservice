package rest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool.Record;
import rest.dto.RecordDTO;
import rest.service.RecordService;

import java.io.IOException;
import java.util.UUID;

@Service
public class HtmlPageService {

    private final RecordService recordService;

    public HtmlPageService(RecordService recordService) {
        this.recordService = recordService;
    }

    public ModelAndView createRecordPage() {
        return recordService.getAllRecords();
    }

    public ModelAndView createRecord(RecordDTO recordDTO) throws IOException {
        return recordService.createRecord(recordDTO);
    }

    public void removeRecord(UUID id) {
        recordService.removeRecordById(id);
    }
}
