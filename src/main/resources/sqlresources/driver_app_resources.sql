CREATE OR REPLACE FUNCTION GetScheduleForInterval (p_interval VARCHAR, p_username VARCHAR)
   RETURNS TABLE (
	   id integer,
	   passenger_id integer,
	   first_name character varying,
	   last_name character varying,
	   email character varying,
	   place_from character varying,
	   place_to character varying,
	   date_from timestamp without time zone,
	   date_to timestamp without time zone
   )
AS $$
BEGIN
   RETURN QUERY
   select sch.id,psg.id as "passenger_id", psg.first_name, psg.last_name, psg.email, sch.place_from, sch.place_to, sch.date_from, sch.date_to
from schedule sch
join passengers psg on sch.passenger_id = psg.id
join users usr on usr.driver_id = sch.driver_id
where usr.username = p_username
and localtimestamp <= sch.date_from and date_trunc('days', localtimestamp + p_interval::text::interval) >= sch.date_from;
END; $$

LANGUAGE 'plpgsql';
/
create or replace view v_driver_schedule as select sch.id, psg.id as "passenger_id", psg.first_name, psg.last_name, psg.email, sch.place_from, sch.place_to, sch.date_from, sch.date_to
from schedule sch
join passengers psg on sch.passenger_id = psg.id
join users usr on usr.driver_id = sch.driver_id