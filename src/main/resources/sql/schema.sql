CREATE EXTENSION IF NOT EXISTS pgcrypto@@ -- UUID生成用

-- users
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(32) NOT NULL,
    email VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)@@

-- boards
CREATE TABLE IF NOT EXISTS boards (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    title VARCHAR(32) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)@@

-- lanes
CREATE TABLE IF NOT EXISTS lanes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    board_id UUID REFERENCES boards(id) ON DELETE CASCADE,
    title VARCHAR(32) NOT NULL,
    position INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)@@

-- cards
CREATE TABLE IF NOT EXISTS cards (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    lane_id UUID REFERENCES lanes(id) ON DELETE CASCADE,
    title VARCHAR(32) NOT NULL,
    position INT NOT NULL,
    due_date DATE NOT NULL,
    status VARCHAR(16) NOT NULL,
    description VARCHAR(128),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CHECK (status IN ('TODO', 'DOING', 'DONE', 'PENDING', 'CANCELLED'))
)@@

-- Lane削除時にpositionを詰める
CREATE OR REPLACE FUNCTION reorder_lanes_after_delete()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE lanes
    SET position = position - 1
    WHERE board_id = OLD.board_id AND position > OLD.position;

    RETURN OLD;
END;
$$ LANGUAGE plpgsql@@

CREATE OR REPLACE TRIGGER trg_reorder_lanes_after_delete
AFTER DELETE ON lanes
FOR EACH ROW
EXECUTE FUNCTION reorder_lanes_after_delete()@@