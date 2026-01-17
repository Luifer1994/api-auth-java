-- liquibase formatted sql

-- changeset admin:enable-uuid-extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- changeset admin:create-credentials-table
CREATE TABLE credentials (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    
    email VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    
    -- Referencia al ID del usuario en api-user (UUID)
    user_id UUID NOT NULL, 
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);