BEGIN;

DROP TABLE IF EXISTS public.upd_search_mstr CASCADE;

-- サーチマスタ
CREATE TABLE IF NOT EXISTS public.upd_search_mstr
(
    del_flg                      INTEGER     NOT NULL,
    isn                          BIGINT PRIMARY KEY,
    app_num                      BIGINT,
    rep_doc_num_pub_exam_pub_num VARCHAR(12) NOT NULL,
    doc_num                      VARCHAR(12),
    doc_typ                      VARCHAR(1)  NOT NULL,
    app_dt                       INTEGER,
    well_known_dt                INTEGER     NOT NULL,
    invent_title                 TEXT DEFAULT ''
);

CREATE INDEX ON upd_search_mstr (app_dt);

COMMENT ON TABLE public.upd_search_mstr IS '特許検索用マスタ';

COMMENT ON COLUMN public.upd_search_mstr.del_flg IS '？';

COMMENT ON COLUMN public.upd_search_mstr.isn IS '？';

COMMENT ON COLUMN public.upd_search_mstr.app_num IS '出願番号';

COMMENT ON COLUMN public.upd_search_mstr.rep_doc_num_pub_exam_pub_num IS '？';

COMMENT ON COLUMN public.upd_search_mstr.doc_num IS 'ドキュメント番号';

COMMENT ON COLUMN public.upd_search_mstr.doc_typ IS 'ドキュメントタイプ';

COMMENT ON COLUMN public.upd_search_mstr.app_dt IS '出願日';

COMMENT ON COLUMN public.upd_search_mstr.well_known_dt IS '公開日';

COMMENT ON COLUMN public.upd_search_mstr.invent_title IS '特許タイトル';

COPY upd_search_mstr
    FROM '/tmp/upd_search_mstr.tsv' (delimiter E'\t', format csv, header true);

-- ツイート特許データ
CREATE TABLE IF NOT EXISTS public.tweet_patent_data
(
    tweet_patent_data_id   BIGSERIAL PRIMARY KEY,
    applicant              TEXT      NOT NULL,
    app_dt                 INTEGER   NOT NULL,
    invent_title           TEXT               DEFAULT '' NOT NULL,
    summary                TEXT               DEFAULT '' NOT NULL,
    is_tweeted             BOOL      NOT NULL DEFAULT 'FALSE',
    representative_diagram BYTEA NOT NULL,
    num_of_references      SMALLINT  NOT NULL,
    created_at             TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    updated_at             TIMESTAMP NOT NULL DEFAULT CURRENT_DATE
);

COMMENT ON TABLE public.tweet_patent_data IS 'ツイート特許データ';

COMMENT ON COLUMN public.tweet_patent_data.tweet_patent_data_id IS 'ID';

COMMENT ON COLUMN public.tweet_patent_data.applicant IS '出願人';

COMMENT ON COLUMN public.tweet_patent_data.app_dt IS '出願日';

COMMENT ON COLUMN public.tweet_patent_data.invent_title IS '特許タイトル';

COMMENT ON COLUMN public.tweet_patent_data.summary IS '要約';

COMMENT ON COLUMN public.tweet_patent_data.is_tweeted IS 'ツイート済みか否か';

COMMENT ON COLUMN public.tweet_patent_data.representative_diagram IS '代表図';

COMMENT ON COLUMN public.tweet_patent_data.num_of_references IS '引用数';

COMMENT ON COLUMN public.tweet_patent_data.created_at IS '作成日';

COMMENT ON COLUMN public.tweet_patent_data.updated_at IS '更新日';

COMMIT;