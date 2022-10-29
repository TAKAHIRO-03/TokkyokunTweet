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

    @Value("${jplatpat.username}")
    private String jPlatPatUsername;

    @Value("${jplatpat.password}")
    private String jPlatPatPassword;

}
