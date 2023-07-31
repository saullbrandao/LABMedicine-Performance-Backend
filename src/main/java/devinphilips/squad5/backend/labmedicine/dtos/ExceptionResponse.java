package devinphilips.squad5.backend.labmedicine.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {
    String message;
    Integer status;
}
