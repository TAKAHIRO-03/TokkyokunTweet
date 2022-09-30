package com.tokkyokun.util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
@EqualsAndHashCode
public class EnvUtil {

    @Value("${j-platpat.username}")
    private String j_platpatUsername;

    @Value("${j-platpat.password}")
    private String j_platpatPassword;

}
