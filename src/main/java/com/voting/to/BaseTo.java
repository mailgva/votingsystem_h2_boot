package com.voting.to;

import com.voting.HasId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseTo implements HasId {
    protected Integer id;
}
