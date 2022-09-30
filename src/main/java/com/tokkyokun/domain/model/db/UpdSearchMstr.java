package com.tokkyokun.domain.model.db;

import lombok.NonNull;
import lombok.Value;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Value
@Table(value = "upd_search_mstr")
public class UpdSearchMstr {

    @Column(value = "del_flg")
    @NonNull
    @NotNull
    Integer delFlg;

    @Id
    @Column(value = "isn")
    @NonNull
    @NotNull
    Long isn;

    @Column(value = "rep_doc_num_pub_exam_pub_num")
    @NonNull
    @NotNull
    String repDocNumPubExamPubNum;

    @Column(value = "doc_num")
    String docNum;

    @Column(value = "doc_typ")
    String docTyp;

    @Column(value = "app_dt")
    Integer appDt;

    @Column(value = "well_known_dt")
    @NonNull
    @NotNull
    Integer wellKnownDt;

    @Column(value = "invent_title")
    String inventTitle;

}
