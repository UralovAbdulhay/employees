package com.example.demo.service.impl;

import com.example.demo.Validation.ObjectParser;
import com.example.demo.Validation.validatioinGroup.SaveValidation;
import com.example.demo.Validation.validatioinGroup.UpdateValidation;
import com.example.demo.base.BaseURL;
import com.example.demo.entity.Attendance;
import com.example.demo.entity.Employee;
import com.example.demo.exceptions.BadRequest;
import com.example.demo.exceptions.ResourceNotFound;
import com.example.demo.file.fileInStorage.InStorageFileService;
import com.example.demo.payload.requests.EmployeeRequest;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.shaded.apache.poi.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private final EmployeeRepository employeeRepository;
    private final PositionServiceImpl positionService;
    private final AttendanceRepository attendanceRepository;
    private final Validator validator;
    private final ObjectParser objectParser;
    private final InStorageFileService storageFileService;


    @Override
    public Employee save(EmployeeRequest request) {
        System.out.println("**************************************");
        System.out.println(request);
        Employee employee = new Employee();
        if (isValidForSave(request)) {
            objectParser.copyFieldsIgnoreNulls(employee, request, true);
            employee.setPosition(positionService.findById((request.getPositionId())));
            return employeeRepository.save(employee);
        }
        return employee;
    }

    @Override
    public Employee update(EmployeeRequest request) {
        if (isValidForUpdate(request)) {
            Employee employee = findById((request.getId()));
            objectParser.copyFieldsIgnoreNulls(employee, request, true);
            employee.setPosition(positionService.findById((request.getPositionId())));
            return employeeRepository.save(employee);
        } else {
            throw BadRequest.get("EmployeeRequest not available for update ");
        }
    }

    @Override
    public Employee findById(@Valid @NotNull @Min(1) Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> ResourceNotFound.get("Employee", "id", id));
    }

    @Override
    public List<Employee> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable).getContent();
    }

    @Override
    public boolean delete(Long id) {
        employeeRepository.deleteById(id);
        return isDeleted(id);
    }

    @Override
    public boolean isDeleted(Long id) {
        return !employeeRepository.existsById(id);
    }

    @Override
    public boolean existById(Long id) {
        return employeeRepository.existsById(id);
    }

    @Override
    public boolean isValidForUpdate(EmployeeRequest request) {
        return validator.validate(request, UpdateValidation.class).size() == 0;
    }

    @Override
    public boolean isValidForSave(EmployeeRequest request) {
        System.out.println("isValidForSave ########################");
        System.out.println(validator.validate(request, SaveValidation.class));
        return validator.validate(request, SaveValidation.class).size() == 0;
    }

    @Override
    public EmployeeRequest convertToPayload(Employee entity) {
        System.out.println(entity);
        return EmployeeRequest.getInstance(entity);
    }

    @Override
    public String exportAll() {
        return exportToExcel(convertToPayload(employeeRepository.findAll()));
    }

    public EmployeeRequest uploadImg(Long id, MultipartFile multipartFile) {
        Employee employee = findById(id);
        employee.setImage(storageFileService.save(multipartFile, BaseURL.EMPLOYEE_FILE_PATH));
        employeeRepository.save(employee);
        return EmployeeRequest.getInstance(employee);
    }

    public ResponseEntity exportToPdf(Long id) {
        Employee employee = findById(id);

        File file = new File(writeToPdf(employee));
        try {
            return file.exists() ?
                    ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                            .body(new ByteArrayResource(IOUtils.toByteArray(new FileInputStream(file))))
                    :
                    null;
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @SneakyThrows
    private String writeToPdf(Employee employee) {

        Document document = new Document();
//        Path path = Paths.get(ClassLoader.getSystemResource("files/employee/22-02-2022/jpg/1aa9aa71-86d0-496a-a27c-32d0db5dfa27.jpg").toURI());


        String fileName = String.format("files/%s/%s/export-%s.pdf",
                "pdf",
                DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDateTime.now()),
                UUID.randomUUID().toString());


        File file = new File(fileName);
        file.getParentFile().mkdirs();
        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);


        Image img = null;

        Paragraph paragraph = new Paragraph();


        try {
            img = Image.getInstance(storageFileService.getFileIO(employee.getImage()).toURL());
        } catch (BadElementException | IOException e) {
            try {
                img = Image.getInstance(new File("resources/statics/account_img.png").toURL());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }

        paragraph.add(new Chunk(img, 0, 0, true));

        if (img != null) {
            img.scaleToFit(160, 240);
        }

        Paragraph textParagraph = new Paragraph();

        textParagraph.add(new Chunk(String.format("Name : %s", employee.getName())).setLineHeight(16));
        textParagraph.add(Chunk.NEWLINE);

        textParagraph.add(new Chunk(String.format("Surname : %s", employee.getSureName())).setLineHeight(16));
        textParagraph.add(Chunk.NEWLINE);

        textParagraph.add(new Chunk(String.format("Birth Date : %s", DateTimeFormatter.ofPattern("dd-MM-yyyy").format(employee.getBirthDate()))).setLineHeight(16));
        textParagraph.add(Chunk.NEWLINE);

        textParagraph.add(new Chunk(String.format("Position : %s", employee.getPosition().getName())).setLineHeight(16));
        textParagraph.add(Chunk.NEWLINE);

        PdfPTable table = new PdfPTable(new float[]{3, 5});

        table.addCell(paragraph);
        table.addCell(textParagraph);
        for (PdfPRow row : table.getRows()) {
            for (PdfPCell cell : row.getCells()) {
                cell.setBorder(0);
                cell.setVerticalAlignment(Element.ALIGN_CENTER);
            }
        }

        table.setWidthPercentage(100);
        document.add(table);
        document.add(new Paragraph(Chunk.NEWLINE.setLineHeight(20)));
        document.add(new Paragraph(Chunk.NEWLINE.setLineHeight(20)));
        document.add(new Paragraph(Chunk.NEWLINE.setLineHeight(20)));


        PdfPTable attendanceTable = new PdfPTable(new float[]{1, 3, 3});
        addTableHeader(attendanceTable);
        List<Attendance> attendances = attendanceRepository.findAllByEmployeeId(employee.getId());

        attendances.sort(Comparator.comparing(Attendance::getEventTime));

        for (int i = 0; i < attendances.size(); i++) {

            attendanceTable.addCell(new Phrase((i + 1) + ""));
            attendanceTable.addCell(new Phrase(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(attendances.get(i).getEventTime())));
            attendanceTable.addCell(new Phrase(attendances.get(i).getType().toString()));

        }

        document.add(attendanceTable);


        document.close();

        return fileName;
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of(" № ", "Attendance date time", "Action type")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

}
