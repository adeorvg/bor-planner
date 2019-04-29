CREATE TABLE public.drivers
(
    pesel character varying COLLATE pg_catalog."default" NOT NULL,
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    driving_license_number character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT drivers_pkey1 PRIMARY KEY (pesel)
);

CREATE TABLE public.passengers
(
    id integer NOT NULL,
    first_name character varying COLLATE pg_catalog."default" NOT NULL,
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default",
    CONSTRAINT passengers_pkey PRIMARY KEY (id)
);

CREATE TABLE public.users
(
    driver_id character varying COLLATE pg_catalog."default" NOT NULL,
    username character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    enabled boolean NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (username),
    CONSTRAINT users_drivers_pesel_fk FOREIGN KEY (driver_id)
        REFERENCES public.drivers (pesel) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE public.cars
(
    registration_number character varying COLLATE pg_catalog."default" NOT NULL,
    mark character varying COLLATE pg_catalog."default",
    model character varying COLLATE pg_catalog."default",
    production_date date,
    CONSTRAINT cars_pkey PRIMARY KEY (registration_number)
);

CREATE TABLE public.schedule
(
    id integer NOT NULL,
    driver_id character varying COLLATE pg_catalog."default" NOT NULL,
    passenger_id integer NOT NULL,
    car_id character varying COLLATE pg_catalog."default" NOT NULL,
    place_from character varying COLLATE pg_catalog."default" NOT NULL,
    place_to character varying COLLATE pg_catalog."default" NOT NULL,
    date_from timestamp without time zone NOT NULL,
    date_to timestamp without time zone NOT NULL,
    CONSTRAINT schedule_pkey PRIMARY KEY (id),
    CONSTRAINT schedule_cars_fk FOREIGN KEY (car_id)
        REFERENCES public.cars (registration_number) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT schedule_drivers_fk FOREIGN KEY (driver_id)
        REFERENCES public.drivers (pesel) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT schedule_passengers_fk FOREIGN KEY (passenger_id)
        REFERENCES public.passengers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE SEQUENCE public.schedule_seq;
