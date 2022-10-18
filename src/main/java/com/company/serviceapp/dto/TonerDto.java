package com.company.serviceapp.dto;

import com.company.serviceapp.model.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.util.LocaleID;

import javax.persistence.ManyToOne;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TonerDto {

    String name;

    String inventarNumber;

    Integer count;

    List<String> department;

}


