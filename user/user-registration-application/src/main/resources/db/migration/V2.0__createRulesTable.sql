create table if not exists rules (
    id        text not null primary key,
    rule_type text not null,
    text      text not null,
    actual    bool default false
);
