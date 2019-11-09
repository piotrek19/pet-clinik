#Create database service accounts
CREATE USER 'dzn_preprod_user'@'localhost' IDENTIFIED BY 'passwd';
CREATE USER 'dzn_preprod_user'@'%' IDENTIFIED BY 'passwd';

#Database grants
GRANT SELECT ON dzn_preprod_schema.* to 'dzn_preprod_user'@'localhost';
GRANT INSERT ON dzn_preprod_schema.* to 'dzn_preprod_user'@'localhost';
GRANT DELETE ON dzn_preprod_schema.* to 'dzn_preprod_user'@'localhost';
GRANT UPDATE ON dzn_preprod_schema.* to 'dzn_preprod_user'@'localhost';
GRANT SELECT ON dzn_preprod_schema.* to 'dzn_preprod_user'@'%';
GRANT INSERT ON dzn_preprod_schema.* to 'dzn_preprod_user'@'%';
GRANT DELETE ON dzn_preprod_schema.* to 'dzn_preprod_user'@'%';
GRANT UPDATE ON dzn_preprod_schema.* to 'dzn_preprod_user'@'%';