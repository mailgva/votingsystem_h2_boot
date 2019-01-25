package com.voting.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DailyRestoMenuTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private int restoId;

    @NotNull
    private List<Integer> listDishesId;

}
