create table Student{
    sname varchar(30),
    sno varchar(30),
    sage smallint,
    sdept varchar(30),
    scholarship varchar(30),
    ssex varchar(30),
    };

create table Course{
    cno varchar(30),
    cname varchar(30),
    ccredit int,
    cpno varchar(30)
    };

create table SC{
    cno varchar(30),
    sno varchar(30),
    grade int
    };