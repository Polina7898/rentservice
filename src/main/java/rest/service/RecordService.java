package rest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import rest.dto.RecordDTO;
import rest.persistence.repository.RecordRepository;
import rest.persistence.entity.Record;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@EnableWebMvc
public class RecordService {

    private final RecordRepository recordRepository;

    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public ModelAndView createRecord(RecordDTO recordDTO) throws IOException {
        Record record = new Record();
        record.setId(UUID.randomUUID());
        record.setTitle(recordDTO.getTitle());
        record.setAddress(recordDTO.getAddress());
        record.setBerth(recordDTO.getBerth());
        record.setRent(recordDTO.getRent());
        record.setDescription(recordDTO.getDescription());

        String pathDir = System.getProperty("user.dir") + "/src/main/resources/files/";
        Path path = Paths.get(pathDir + record.getId() + ".jpg");
        File directory = new File(pathDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        if (!recordDTO.getImage().isEmpty()){
            InputStream inputStream = recordDTO.getImage().getInputStream();
            Files.copy(inputStream, path,
                    StandardCopyOption.REPLACE_EXISTING);
        } else{
            Path sourcePath = Paths.get(pathDir + "default.jpg");
            Files.copy(sourcePath, path,
                    StandardCopyOption.REPLACE_EXISTING);
        }
        recordRepository.save(record);
        return getAllRecords();
    }

    public ModelAndView getAllRecords() {
        List<Record> records = recordRepository.getAllRecords();
        List<RecordDTO> resultList = new ArrayList<>();
        for (Record record : records) {
            RecordDTO recordDTO = new RecordDTO();
            recordDTO.setId(record.getId().toString());
            recordDTO.setTitle(record.getTitle());
            recordDTO.setAddress(record.getAddress());
            recordDTO.setBerth(record.getBerth());
            recordDTO.setRent(record.getRent());
            recordDTO.setDescription(record.getDescription());
//            recordDTO.setImage(record.getImage());
//            recordDTO.setImage();
            resultList.add(recordDTO);
        }
        return createAndFillModel(resultList);
    }

    private ModelAndView createAndFillModel(List<RecordDTO> recordDTOs) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.clear();
        modelAndView.getModel().put("listRecords", recordDTOs);
        modelAndView.setViewName("records-page");
        return modelAndView;
    }

    public void removeRecordById(UUID id) {
        recordRepository.deleteById(id);
    }
}