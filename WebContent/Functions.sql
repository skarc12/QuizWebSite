delimiter @
drop function if exists nameInUse@
create function nameInUse (username varchar(100))
returns int
begin
	
	return (select ID from users where users.username=username) is not null;
end


# set id = (select ID from users where username=usernamek);
# declare result boolean default false;
#	declare id int default -1;
#	set id = (select ID from users2 where username='bidza');
#	if id > 0 then
#		set result = true;
#	end if;