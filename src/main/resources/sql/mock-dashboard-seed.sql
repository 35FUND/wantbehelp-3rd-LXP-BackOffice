-- -----------------------------------------------------------------------------
-- Backoffice 통합 테스트용 목데이터 시드
-- 대상 화면/API
-- - 대시보드: /api/v1/dashboard/uploads, /conversion-rate, /categories/shorts-count
-- - 유저 관리: /api/v1/users
-- - 카테고리/키워드 관리: /api/v1/categories, /api/v1/keywords
-- - 댓글 신고 관리: /api/v1/comments/reports
-- -----------------------------------------------------------------------------

USE shortudy;

-- -----------------------------------------------------------------------------
-- 0) 기존 MOCK-SEED 데이터 정리
-- -----------------------------------------------------------------------------
DELETE FROM comment_reports WHERE reason LIKE '[MOCK-SEED]%';
DELETE FROM shorts WHERE title LIKE '[MOCK-SEED]%';
DELETE FROM refreshtokens WHERE token LIKE 'mock-seed-%';
DELETE FROM keyword WHERE display_name LIKE '[MOCK-SEED]%' OR normalized_name LIKE 'mock-seed-%';
DELETE FROM users WHERE email LIKE 'mock.seed.%@shortudy.local';
DELETE FROM categories WHERE name LIKE '[MOCK-SEED]%';

-- -----------------------------------------------------------------------------
-- 1) 카테고리 목데이터
-- -----------------------------------------------------------------------------
INSERT INTO categories (id, name, created_at, updated_at) VALUES
    (8101, '[MOCK-SEED] AI', DATE_SUB(NOW(), INTERVAL 45 DAY), NOW()),
    (8102, '[MOCK-SEED] BACKEND', DATE_SUB(NOW(), INTERVAL 45 DAY), NOW()),
    (8103, '[MOCK-SEED] FRONTEND', DATE_SUB(NOW(), INTERVAL 45 DAY), NOW()),
    (8104, '[MOCK-SEED] DATA', DATE_SUB(NOW(), INTERVAL 45 DAY), NOW()),
    (8105, '[MOCK-SEED] DEVOPS', DATE_SUB(NOW(), INTERVAL 45 DAY), NOW());

-- -----------------------------------------------------------------------------
-- 2) 키워드 목데이터 (keyword 단수 테이블 기준)
-- -----------------------------------------------------------------------------
INSERT INTO keyword (id, display_name, normalized_name, created_at, updated_at) VALUES
    (8201, '[MOCK-SEED] AI', 'mock-seed-ai', DATE_SUB(NOW(), INTERVAL 20 DAY), NOW()),
    (8202, '[MOCK-SEED] Spring Boot', 'mock-seed-spring-boot', DATE_SUB(NOW(), INTERVAL 19 DAY), NOW()),
    (8203, '[MOCK-SEED] React', 'mock-seed-react', DATE_SUB(NOW(), INTERVAL 18 DAY), NOW()),
    (8204, '[MOCK-SEED] Python', 'mock-seed-python', DATE_SUB(NOW(), INTERVAL 17 DAY), NOW()),
    (8205, '[MOCK-SEED] Kubernetes', 'mock-seed-kubernetes', DATE_SUB(NOW(), INTERVAL 16 DAY), NOW());

-- -----------------------------------------------------------------------------
-- 3) 사용자 목데이터 (유저 목록/유저 통계 확인용)
-- -----------------------------------------------------------------------------
INSERT INTO users (
    id,
    email,
    password,
    nickname,
    role,
    profile_url,
    status,
    created_at,
    updated_at
) VALUES
    (8301, 'mock.seed.01@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저01', 'USER', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 29 DAY), NOW()),
    (8302, 'mock.seed.02@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저02', 'USER', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 26 DAY), NOW()),
    (8303, 'mock.seed.03@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저03', 'USER', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 22 DAY), NOW()),
    (8304, 'mock.seed.04@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저04', 'USER', NULL, 'INACTIVE', DATE_SUB(NOW(), INTERVAL 18 DAY), NOW()),
    (8305, 'mock.seed.05@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저05', 'USER', NULL, 'DELETED', DATE_SUB(NOW(), INTERVAL 14 DAY), NOW()),
    (8306, 'mock.seed.06@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저06', 'USER', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 10 DAY), NOW()),
    (8307, 'mock.seed.07@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저07', 'USER', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 7 DAY), NOW()),
    (8308, 'mock.seed.08@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저08', 'USER', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 4 DAY), NOW()),
    (8309, 'mock.seed.09@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저09', 'ADMIN', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 2 DAY), NOW()),
    (8310, 'mock.seed.10@shortudy.local', '$2a$10$2Yw6QJv.f5Jq4f6e5xN4ZeQmPl3s1J1fQ2l7H3j8qR9wV0aB1cD2e', '시드유저10', 'USER', NULL, 'ACTIVE', DATE_SUB(NOW(), INTERVAL 1 DAY), NOW());

-- 유저 목록의 마지막 갱신 시각 표시용
INSERT INTO refreshtokens (id, user_id, token, created_at, updated_at) VALUES
    (8401, 8301, 'mock-seed-refresh-8301', DATE_SUB(NOW(), INTERVAL 3 DAY), DATE_SUB(NOW(), INTERVAL 1 DAY)),
    (8402, 8302, 'mock-seed-refresh-8302', DATE_SUB(NOW(), INTERVAL 2 DAY), NOW());

-- -----------------------------------------------------------------------------
-- 4) Shorts 목데이터 (대시보드 지표 + 검수 상태 확인용)
-- -----------------------------------------------------------------------------
INSERT INTO shorts (
    user_id,
    category_id,
    title,
    description,
    video_url,
    thumbnail_url,
    duration_sec,
    like_count,
    view_count,
    published_at,
    status,
    created_at,
    updated_at
) VALUES
    -- PUBLISHED
    (8301, 8101, '[MOCK-SEED] pub-ai-01', '통합 시드 데이터', 'https://example.com/v/pub-ai-01', 'https://example.com/t/pub-ai-01', 34, 12, 240, DATE_SUB(NOW(), INTERVAL 9 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 10 DAY), NOW()),
    (8302, 8101, '[MOCK-SEED] pub-ai-02', '통합 시드 데이터', 'https://example.com/v/pub-ai-02', 'https://example.com/t/pub-ai-02', 29, 18, 310, DATE_SUB(NOW(), INTERVAL 8 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 8 DAY), NOW()),
    (8303, 8102, '[MOCK-SEED] pub-be-01', '통합 시드 데이터', 'https://example.com/v/pub-be-01', 'https://example.com/t/pub-be-01', 31, 7, 145, DATE_SUB(NOW(), INTERVAL 7 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 7 DAY), NOW()),
    (8304, 8102, '[MOCK-SEED] pub-be-02', '통합 시드 데이터', 'https://example.com/v/pub-be-02', 'https://example.com/t/pub-be-02', 27, 9, 188, DATE_SUB(NOW(), INTERVAL 6 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 6 DAY), NOW()),
    (8305, 8103, '[MOCK-SEED] pub-fe-01', '통합 시드 데이터', 'https://example.com/v/pub-fe-01', 'https://example.com/t/pub-fe-01', 24, 6, 101, DATE_SUB(NOW(), INTERVAL 5 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 5 DAY), NOW()),
    (8306, 8104, '[MOCK-SEED] pub-data-01', '통합 시드 데이터', 'https://example.com/v/pub-data-01', 'https://example.com/t/pub-data-01', 38, 23, 420, DATE_SUB(NOW(), INTERVAL 3 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 3 DAY), NOW()),
    (8307, 8105, '[MOCK-SEED] pub-devops-01', '통합 시드 데이터', 'https://example.com/v/pub-devops-01', 'https://example.com/t/pub-devops-01', 36, 14, 265, DATE_SUB(NOW(), INTERVAL 2 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 2 DAY), NOW()),
    (8308, 8103, '[MOCK-SEED] pub-fe-02', '통합 시드 데이터', 'https://example.com/v/pub-fe-02', 'https://example.com/t/pub-fe-02', 25, 5, 89, DATE_SUB(NOW(), INTERVAL 1 DAY), 'PUBLISHED', DATE_SUB(NOW(), INTERVAL 1 DAY), NOW()),

    -- PENDING / AI_CHECK / REJECT
    (8301, 8101, '[MOCK-SEED] pending-01', '검수 대기 확인용', 'https://example.com/v/pending-01', 'https://example.com/t/pending-01', 22, 0, 0, NULL, 'PENDING', DATE_SUB(NOW(), INTERVAL 6 DAY), NOW()),
    (8302, 8102, '[MOCK-SEED] pending-02', '검수 대기 확인용', 'https://example.com/v/pending-02', 'https://example.com/t/pending-02', 21, 0, 0, NULL, 'PENDING', DATE_SUB(NOW(), INTERVAL 4 DAY), NOW()),
    (8303, 8103, '[MOCK-SEED] ai-check-01', '검수 진행중 확인용', 'https://example.com/v/ai-check-01', 'https://example.com/t/ai-check-01', 23, 0, 0, NULL, 'AI_CHECK', DATE_SUB(NOW(), INTERVAL 5 DAY), NOW()),
    (8304, 8105, '[MOCK-SEED] ai-check-02', '검수 진행중 확인용', 'https://example.com/v/ai-check-02', 'https://example.com/t/ai-check-02', 24, 0, 0, NULL, 'AI_CHECK', DATE_SUB(NOW(), INTERVAL 3 DAY), NOW()),
    (8305, 8101, '[MOCK-SEED] reject-01', '검수 반려 확인용', 'https://example.com/v/reject-01', 'https://example.com/t/reject-01', 27, 1, 33, NULL, 'REJECT', DATE_SUB(NOW(), INTERVAL 7 DAY), NOW());

-- -----------------------------------------------------------------------------
-- 5) 댓글 신고 목데이터
-- -----------------------------------------------------------------------------
INSERT INTO comment_reports (
    id,
    comment_id,
    reporter_id,
    reason,
    status,
    created_at,
    updated_at
) VALUES
    (8501, 10001, 8301, '[MOCK-SEED] 욕설/비방', 'PENDING', DATE_SUB(NOW(), INTERVAL 2 DAY), NOW()),
    (8502, 10002, 8302, '[MOCK-SEED] 광고/스팸', 'PENDING', DATE_SUB(NOW(), INTERVAL 1 DAY), NOW()),
    (8503, 10003, 8303, '[MOCK-SEED] 음란/선정성', 'PROCESSED', DATE_SUB(NOW(), INTERVAL 5 DAY), DATE_SUB(NOW(), INTERVAL 3 DAY)),
    (8504, 10004, 8304, '[MOCK-SEED] 허위정보', 'REJECTED', DATE_SUB(NOW(), INTERVAL 8 DAY), DATE_SUB(NOW(), INTERVAL 7 DAY));

-- -----------------------------------------------------------------------------
-- 6) 빠른 확인 쿼리
-- -----------------------------------------------------------------------------
-- SELECT id, name FROM categories WHERE name LIKE '[MOCK-SEED]%' ORDER BY id;
-- SELECT id, display_name, normalized_name FROM keyword WHERE display_name LIKE '[MOCK-SEED]%' ORDER BY id;
-- SELECT id, email, role, status FROM users WHERE email LIKE 'mock.seed.%@shortudy.local' ORDER BY id;
-- SELECT id, title, status, published_at FROM shorts WHERE title LIKE '[MOCK-SEED]%' ORDER BY id;
-- SELECT id, reason, status FROM comment_reports WHERE reason LIKE '[MOCK-SEED]%' ORDER BY id;
