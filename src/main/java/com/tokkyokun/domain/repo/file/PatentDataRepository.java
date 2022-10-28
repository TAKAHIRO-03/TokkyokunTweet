package com.tokkyokun.domain.repo.file;

public interface PatentDataRepository<T> {

    T fetchPatentData(Integer docNum);

}
