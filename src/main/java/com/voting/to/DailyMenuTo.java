package com.voting.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.voting.model.Resto;
import com.voting.web.converter.DateTimeFormatters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.Locale;

@XmlRootElement
@NoArgsConstructor
//@AllArgsConstructor
@Data
public class DailyMenuTo{
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    private int voteId;
    private Resto resto;
    private boolean selected;

    public DailyMenuTo(LocalDate date, Resto resto, int voteId, boolean selected) {
        this.voteId = voteId;
        this.date = date;
        this.resto = resto;
        this.selected  = selected;
    }

    public DailyMenuTo(String date, int voteId, Resto resto, boolean selected) {
        this.voteId = voteId;
        //this.date = date;
        this.date = new DateTimeFormatters.LocalDateFormatter().parse(date, Locale.getDefault());
        this.resto = resto;
        this.selected  = selected;
    }
}
