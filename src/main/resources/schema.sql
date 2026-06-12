CREATE TABLE IF NOT EXISTS employee (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    reference_id VARCHAR(100) NOT NULL ,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(100) NOT NULL,
    salary DECIMAL(10,2)
);
CREATE TABLE audit_table (
    audit_id UUID PRIMARY KEY,
    employee_id UUID NOT NULL,
    payload CLOB NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE error_table (
    error_id UUID PRIMARY KEY,
    audit_id UUID NOT NULL,
    payload CLOB NOT NULL,
    error_message VARCHAR(1000),

    CONSTRAINT fk_audit_error
    FOREIGN KEY (audit_id)
    REFERENCES audit_table(audit_id)
);