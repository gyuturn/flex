CREATE TABLE IF NOT EXISTS products (
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT        NOT NULL,
    name         VARCHAR(255)  NOT NULL,
    url          TEXT          NOT NULL UNIQUE,
    target_price NUMERIC(15,2) NOT NULL,
    current_price NUMERIC(15,2),
    active       BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS price_histories (
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT        NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    price       NUMERIC(15,2) NOT NULL,
    crawled_at  TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS price_alerts (
    id               BIGSERIAL PRIMARY KEY,
    product_id       BIGINT        NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    user_id          BIGINT        NOT NULL,
    triggered_price  NUMERIC(15,2) NOT NULL,
    target_price     NUMERIC(15,2) NOT NULL,
    channel          VARCHAR(20)   NOT NULL,
    status           VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
    created_at       TIMESTAMPTZ   NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_products_user_id ON products(user_id);
CREATE INDEX idx_price_histories_product_id ON price_histories(product_id);
CREATE INDEX idx_price_alerts_user_id ON price_alerts(user_id);
