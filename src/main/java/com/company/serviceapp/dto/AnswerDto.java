package com.company.serviceapp.dto;


import com.company.serviceapp.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class AnswerDto {

   UUID id;

   UUID answerId;
}
