package com.example.backend.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Null
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Unieke resource identificatie van het PRODUCT (UUID4)", format = "uuid")
    private Long id;
    private String firstname;
    private String lastname;
    private String email;

}
