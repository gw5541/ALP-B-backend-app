-- =================================================
-- ALP-B Backend Database Schema Backup
-- Generated from JPA Entity Classes
-- Date: 2025-08-20
-- =================================================

-- Enable JSONB extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- =================================================
-- 1. DISTRICT TABLE (자치구 마스터)
-- =================================================
CREATE TABLE IF NOT EXISTS district (
    district_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_district PRIMARY KEY (district_id)
);

-- =================================================
-- 2. USER_ACCOUNT TABLE (사용자 계정)
-- =================================================
CREATE TABLE IF NOT EXISTS user_account (
    user_id BIGSERIAL NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP,
    CONSTRAINT pk_user_account PRIMARY KEY (user_id)
);

-- =================================================
-- 3. POPULATION_STAT_RAW TABLE (인구 통계 원본 데이터)
-- =================================================
CREATE TABLE IF NOT EXISTS population_stat_raw (
    district_id BIGINT NOT NULL,
    stdr_de_id DATE NOT NULL,
    tmzon_pd_se BIGINT NOT NULL,
    tot BIGINT,
    male_buckets JSONB,
    female_buckets JSONB,
    CONSTRAINT pk_population_stat_raw PRIMARY KEY (district_id, stdr_de_id, tmzon_pd_se)
);

-- =================================================
-- 4. POPULATION_STAT_AGG TABLE (인구 통계 집계 데이터)
-- =================================================
CREATE TABLE IF NOT EXISTS population_stat_agg (
    district_id BIGINT NOT NULL,
    period_type VARCHAR(255) NOT NULL,
    period_start_date DATE NOT NULL,
    period_end_date DATE NOT NULL,
    tot_avg BIGINT,
    male_buckets_avg JSONB,
    female_buckets_avg JSONB,
    daytime_avg BIGINT,
    nighttime_avg BIGINT,
    CONSTRAINT pk_population_stat_agg PRIMARY KEY (district_id, period_type, period_start_date, period_end_date)
);

-- =================================================
-- 5. USER_FAVORITE_DISTRICT TABLE (사용자 관심 지역)
-- =================================================
CREATE TABLE IF NOT EXISTS user_favorite_district (
    user_id BIGINT NOT NULL,
    district_id BIGINT NOT NULL,
    created_at TIMESTAMP,
    CONSTRAINT pk_user_favorite_district PRIMARY KEY (user_id, district_id)
);

-- =================================================
-- 6. USER_NOTE TABLE (사용자 메모)
-- =================================================
CREATE TABLE IF NOT EXISTS user_note (
    note_id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    district_id BIGINT,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP,
    CONSTRAINT pk_user_note PRIMARY KEY (note_id)
);

-- =================================================
-- 7. USER_PRESET TABLE (사용자 프리셋)
-- =================================================
CREATE TABLE IF NOT EXISTS user_preset (
    preset_id BIGSERIAL NOT NULL,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    filters JSONB,
    created_at TIMESTAMP,
    CONSTRAINT pk_user_preset PRIMARY KEY (preset_id)
);

-- =================================================
-- FOREIGN KEY CONSTRAINTS
-- =================================================

-- Population Stat Raw -> District
ALTER TABLE population_stat_raw 
ADD CONSTRAINT fk_population_stat_raw_district 
FOREIGN KEY (district_id) REFERENCES district(district_id);

-- Population Stat Agg -> District
ALTER TABLE population_stat_agg 
ADD CONSTRAINT fk_population_stat_agg_district 
FOREIGN KEY (district_id) REFERENCES district(district_id);

-- User Favorite District -> User Account
ALTER TABLE user_favorite_district 
ADD CONSTRAINT fk_user_favorite_district_user 
FOREIGN KEY (user_id) REFERENCES user_account(user_id);

-- User Favorite District -> District
ALTER TABLE user_favorite_district 
ADD CONSTRAINT fk_user_favorite_district_district 
FOREIGN KEY (district_id) REFERENCES district(district_id);

-- User Note -> User Account
ALTER TABLE user_note 
ADD CONSTRAINT fk_user_note_user 
FOREIGN KEY (user_id) REFERENCES user_account(user_id);

-- User Note -> District
ALTER TABLE user_note 
ADD CONSTRAINT fk_user_note_district 
FOREIGN KEY (district_id) REFERENCES district(district_id);

-- User Preset -> User Account
ALTER TABLE user_preset 
ADD CONSTRAINT fk_user_preset_user 
FOREIGN KEY (user_id) REFERENCES user_account(user_id);

-- =================================================
-- INDEXES
-- =================================================

-- District name index
CREATE INDEX IF NOT EXISTS idx_district_name ON district(name);

-- User Account email index
CREATE INDEX IF NOT EXISTS idx_user_account_email ON user_account(email);

-- Population Stat Raw indexes
CREATE INDEX IF NOT EXISTS idx_population_stat_raw_date ON population_stat_raw(stdr_de_id);
CREATE INDEX IF NOT EXISTS idx_population_stat_raw_time ON population_stat_raw(tmzon_pd_se);
CREATE INDEX IF NOT EXISTS idx_population_stat_raw_district_date ON population_stat_raw(district_id, stdr_de_id);

-- Population Stat Agg indexes
CREATE INDEX IF NOT EXISTS idx_population_stat_agg_period ON population_stat_agg(period_type);
CREATE INDEX IF NOT EXISTS idx_population_stat_agg_date_range ON population_stat_agg(period_start_date, period_end_date);

-- User Note indexes
CREATE INDEX IF NOT EXISTS idx_user_note_user ON user_note(user_id);
CREATE INDEX IF NOT EXISTS idx_user_note_district ON user_note(district_id);

-- User Preset indexes
CREATE INDEX IF NOT EXISTS idx_user_preset_user ON user_preset(user_id);

-- =================================================
-- COMMENTS
-- =================================================

COMMENT ON TABLE district IS '자치구 마스터 테이블';
COMMENT ON TABLE user_account IS '사용자 계정 테이블';
COMMENT ON TABLE population_stat_raw IS '인구 통계 원본 데이터 (시간 단위)';
COMMENT ON TABLE population_stat_agg IS '인구 통계 집계 데이터 (기간 단위)';
COMMENT ON TABLE user_favorite_district IS '사용자 관심 지역 테이블';
COMMENT ON TABLE user_note IS '사용자 메모 테이블';
COMMENT ON TABLE user_preset IS '사용자 프리셋 테이블';

-- =================================================
-- END OF SCHEMA BACKUP
-- =================================================
