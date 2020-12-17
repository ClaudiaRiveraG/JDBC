LOAD DATA
INFILE 'c:\CPRG307\payroll.txt' 
REPLACE
INTO TABLE payroll_load
FIELDS TERMINATED BY ';' OPTIONALLY ENCLOSED BY '"'
TRAILING NULLCOLS
(payroll_date DATE "Month dd, yyyy",
employee_id,
amount,
status)