#Create database service accounts
CREATE USER 'dzn_prod_user'@'localhost' IDENTIFIED BY 'passwd';
CREATE USER 'dzn_prod_user'@'%' IDENTIFIED BY 'passwd';

#Database grants
GRANT SELECT ON dzn_prod_schema.* to 'dzn_prod_user'@'localhost';
GRANT INSERT ON dzn_prod_schema.* to 'dzn_prod_user'@'localhost';
GRANT DELETE ON dzn_prod_schema.* to 'dzn_prod_user'@'localhost';
GRANT UPDATE ON dzn_prod_schema.* to 'dzn_prod_user'@'localhost';
GRANT SELECT ON dzn_prod_schema.* to 'dzn_prod_user'@'%';
GRANT INSERT ON dzn_prod_schema.* to 'dzn_prod_user'@'%';
GRANT DELETE ON dzn_prod_schema.* to 'dzn_prod_user'@'%';
GRANT UPDATE ON dzn_prod_schema.* to 'dzn_prod_user'@'%';